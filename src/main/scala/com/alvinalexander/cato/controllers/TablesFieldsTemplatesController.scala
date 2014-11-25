package com.alvinalexander.cato.controllers

import com.alvinalexander.cato.CatoGui
import com.alvinalexander.cato.view.TablesFieldsTemplatesPanel
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import javax.swing.ListSelectionModel
import scala.collection.JavaConversions._
import javax.swing.DefaultListModel
import javax.swing.event.ListSelectionListener
import javax.swing.event.ListSelectionEvent
import com.alvinalexander.cato.model.DatabaseUtils
import com.alvinalexander.cato.utils.FileUtils
import com.alvinalexander.cato.utils.JavaFileUtils
import com.alvinalexander.cato.utils.SwingUtils
import javax.swing.JOptionPane
import javax.swing.JScrollPane
import javax.swing.JTextArea
import java.awt.Font
import com.alvinalexander.cato.utils.StringUtils
import com.alvinalexander.cato.utils.GuiUtils
import scala.collection.mutable.ArrayBuffer
import com.alvinalexander.cato.Field
import com.alvinalexander.cato.model.TableUtils
import com.devdaily.dbgrinder.view.TextDisplayDialog
import java.awt.Toolkit
import java.awt.Dimension
import com.alvinalexander.annotations.impure

class TablesFieldsTemplatesController (mainController: CatoGui) {
  
    val tablesFieldsTemplatesPanel = new TablesFieldsTemplatesPanel
    val databaseTablesJList = tablesFieldsTemplatesPanel.getTablesJList
    val tableFieldsJList = tablesFieldsTemplatesPanel.getFieldsJList
    val templatesJList = tablesFieldsTemplatesPanel.getTemplatesJList
    val generateCodeButton = tablesFieldsTemplatesPanel.getButton2
    
    // customize as needed
    databaseTablesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)         // one db table
    templatesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)              // one template
    tableFieldsJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION) // multiple fields ok

    // jlist models
    val templateFilesTablesModel = new DefaultListModel[String]
    val databaseTablesModel = new DefaultListModel[String]
    val fieldsTablesModel = new DefaultListModel[String]
    templatesJList.setModel(templateFilesTablesModel)
    databaseTablesJList.setModel(databaseTablesModel)
    tableFieldsJList.setModel(fieldsTablesModel)
    
    // listeners
    databaseTablesJList.addListSelectionListener(new TablesListSelectionListener(this))
    generateCodeButton.addActionListener(new GenerateCodeButtonListener(this))
    
    def setTableNames(dbTableNames: Seq[String]) {
        databaseTablesModel.clear
        for (table <- dbTableNames) databaseTablesModel.addElement(table)
    }
    
    def setListOfTemplateFiles(listOfTemplateFiles: Seq[String]) {
        templateFilesTablesModel.clear
        for (file <- listOfTemplateFiles) templateFilesTablesModel.addElement(file)
    }
    
    def clearListOfTemplateFiles { templateFilesTablesModel.clear }
    
    @impure def handleDatabaseTableSelectedEvent {
        val dbTableName = databaseTablesJList.getSelectedValue
        val fields = mainController.getFieldsForTableName(dbTableName)
        setListOfTableFields(fields)
    }

    def setListOfTableFields(newFields: Seq[String]) {
        fieldsTablesModel.clear
        for (field <- newFields) fieldsTablesModel.addElement(field)
    }
    
    // TODO *** NEED TO ADD A LOT OF VALIDATION HERE ***
    def handleGenerateCodeButtonClicked {
        val dbTable = databaseTablesJList.getSelectedValue
        val fields = tableFieldsJList.getSelectedValuesList
        val localTemplateFilename = templatesJList.getSelectedValue
        if (!tftDataIsValid(dbTable, fields, localTemplateFilename)) {
            GuiUtils.showErrorDialog("Invalid Data", "Choose a table, one or more fields, and one template.")
            return
        }
        val canonTemplateFilename = createCanonicalTemplateFilename(localTemplateFilename)
        val templateText = JavaFileUtils.readFileAsString(canonTemplateFilename)

        // build up the `data` object from the selected Table and Fields
        val data = buildDataObjectForTemplate(dbTable: String, fields: Seq[String])

        // apply the data to the template to get the desired code
        val generatedCode = CodeGenerator.generateCode(templateText, data)
        // show output
        showSourceCodeDialog("Generated Source Code", generatedCode)
    }
    
    def handleDatabaseConnectEvent {
        templateFilesTablesModel.clear
        databaseTablesModel.clear
        fieldsTablesModel.clear
    }

    def handleDatabaseDisconnectEvent {
        templateFilesTablesModel.clear
        databaseTablesModel.clear
        fieldsTablesModel.clear
    }
    
    // TODO get this data correctly
    private def buildDataObjectForTemplate(dbTablename: String, userSelectedFields: Seq[String]): Map[String, Object] = {
        val data = scala.collection.mutable.Map[String, Object]()
        
        // create the single values that the templates need
        data += ("tablename" -> dbTablename)
        data += ("classname" -> TableUtils.convertTableNameToClassName(dbTablename))
        data += ("objectname" -> TableUtils.convertTableNameToObjectName(dbTablename))
        
        // TODO - NEED TO VERIFY THESE
        data += ("fieldsAsInsertCsvString" -> mainController.getFieldNamesAsCsvString(dbTablename, userSelectedFields))
        data += ("prepStmtAsInsertCsvString" -> mainController.getPreparedStatementInsertString(dbTablename, userSelectedFields))
        data += ("prepStmtAsUpdateCsvString" -> mainController.getPreparedStatementUpdateString(dbTablename, userSelectedFields))
        
        // TODO need to add some more conversions here ...
    
        // create the array for "fields" for the template
        val fields = mainController.getFieldDataForTableName(dbTablename, userSelectedFields)
        val fieldsAsJavaList : java.util.List[Field] = fields
        data.put("fields", fieldsAsJavaList)
        
        // return the Map the code generator will use
        data.toMap
    }

    // TODO probably need some validation here
    private def createCanonicalTemplateFilename(localTemplateFilename: String) = {
        val templateDir = mainController.getTemplateDir
        templateDir + FileUtils.SLASH + localTemplateFilename
    }
    
    private def tftDataIsValid(dbTable: String, dbFields: Seq[Object], templateFilename: String): Boolean = {
        if (StringUtils.isNullOrEmpty(dbTable)) false
        else if (StringUtils.isNullOrEmpty(templateFilename)) false
        else if (dbFields == null || dbFields.size==0) false
        else true
    }

    private def showSourceCodeDialog(title: String, textToDisplay: String) {
        val d = new TextDisplayDialog(null, title, true)
        d.setText(textToDisplay)
        val prefSize = d.getPreferredSize
        d.setPreferredSize(getBestDialogSize(prefSize))
        d.pack
        d.setLocationRelativeTo(null)
        d.setVisible(true)
    }
    
    private def getBestDialogSize(prefSize: Dimension): Dimension = {
        val prefHeight = prefSize.height
        val prefWidth = prefSize.width
        val screenSize = Toolkit.getDefaultToolkit.getScreenSize
        val maxHeight = screenSize.height * 3/4
        val maxWidth = screenSize.width * 3/5
        if (prefHeight < maxHeight && prefWidth < maxWidth) {
            prefSize
        } else {
            new Dimension(maxWidth, maxHeight)
        }
    }
    
}

class GenerateCodeButtonListener(handler: TablesFieldsTemplatesController) extends ActionListener {
  
    def actionPerformed(e: ActionEvent) {
        handler.handleGenerateCodeButtonClicked
    }

}

class TablesListSelectionListener(controller: TablesFieldsTemplatesController) extends ListSelectionListener {
    def valueChanged(e: ListSelectionEvent) {
        if (e.getValueIsAdjusting() == false) controller.handleDatabaseTableSelectedEvent
    }
}

