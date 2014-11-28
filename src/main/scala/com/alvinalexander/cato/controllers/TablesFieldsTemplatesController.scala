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
    
    @impure def setTableNames(dbTableNames: Seq[String]) {
        databaseTablesModel.clear
        for (table <- dbTableNames) databaseTablesModel.addElement(table)
    }
    
    @impure def setListOfTemplateFiles(listOfTemplateFiles: Seq[String]) {
        templateFilesTablesModel.clear
        for (file <- listOfTemplateFiles) templateFilesTablesModel.addElement(file)
    }
    
    @impure def clearListOfTemplateFiles { templateFilesTablesModel.clear }
    
    @impure def handleDatabaseTableSelectedEvent {
        val dbTableName = databaseTablesJList.getSelectedValue
        val fields = CodeGenerator.getFieldsForTableName(mainController.metaData, dbTableName)
        setListOfTableFields(fields)
    }

    @impure def setListOfTableFields(newFields: Seq[String]) {
        fieldsTablesModel.clear
        for (field <- newFields) fieldsTablesModel.addElement(field)
    }
    
    /**
     * TODO need to validate the data in here
     * this method should get everything it needs from the gui, then
     * hand all of that data to the CodeGenerator to do the code-generating work,
     * and then show the result of that work. nothing else should be done here.
     */
    @impure def handleGenerateCodeButtonClicked {
        val dbTable = databaseTablesJList.getSelectedValue
        val fields = tableFieldsJList.getSelectedValuesList
        val localTemplateFilename = templatesJList.getSelectedValue
        if (!tftDataIsValid(dbTable, fields, localTemplateFilename)) {
            GuiUtils.showErrorDialog("Invalid Data", "Choose a table, one or more fields, and one template.")
            return
        }
        val canonTemplateFilename = createCanonicalTemplateFilename(localTemplateFilename)
        val templateFileAsString = JavaFileUtils.readFileAsString(canonTemplateFilename)

        // build up the `data` object from the selected Table and Fields
        val data = CodeGenerator.buildDataObjectForTemplate(mainController.metaData, mainController.allDataTypesAsMap, dbTable, fields)

        // apply the data to the template to get the desired code
        val generatedCode = CodeGenerator.generateCode(mainController.getTemplateDir, templateFileAsString, data)
        // show output
        showSourceCodeDialog("Generated Source Code", generatedCode)
    }
    
    @impure def handleDatabaseConnectEvent {
        templateFilesTablesModel.clear
        databaseTablesModel.clear
        fieldsTablesModel.clear
    }

    @impure def handleDatabaseDisconnectEvent {
        templateFilesTablesModel.clear
        databaseTablesModel.clear
        fieldsTablesModel.clear
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

