package com.alvinalexander.cato.controllers

import com.alvinalexander.cato.MainGuiController
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

class TablesFieldsTemplatesController (mainController: MainGuiController) {
  
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
    
    def handleDatabaseTableSelectedEvent {
        val dbTableName = databaseTablesJList.getSelectedValue
        val fields = mainController.getFieldsForTableName(dbTableName)
        setListOfTableFields(fields)
    }

    def setListOfTableFields(newFields: Seq[String]) {
        fieldsTablesModel.clear
        for (field <- newFields) fieldsTablesModel.addElement(field)
    }
    
    // TODO *** NEED TO DO A LOT OF VALIDATION HERE ***
    def handleGenerateCodeButtonClicked {
        val dbTable = databaseTablesJList.getSelectedValue
        val fields = tableFieldsJList.getSelectedValues
        val localTemplateFilename = templatesJList.getSelectedValue
        if (!tftDataIsValid(dbTable, fields, localTemplateFilename)) {
            GuiUtils.showErrorDialog("Invalid Data", "Choose a table, one or more fields, and one template.")
            return
        }
        val canonTemplateFilename = createCanonicalTemplateFilename(localTemplateFilename)
        val templateText = JavaFileUtils.readFileAsString(canonTemplateFilename)

        // build up the `data` object from the selected Table and Fields
        val data = buildDataObjectForTemplate

        // apply the data to the template to get the desired code
        val generatedCode = CodeGenerator.generateCode(templateText, data)
        // show output
        showSourceCodeDialog("Generated Source Code", generatedCode)
    }
    
    // TODO get this data correctly
    private def buildDataObjectForTemplate: Map[String, Object] = {
        val data = scala.collection.mutable.Map[String, Object]()
        data += ("tablename" -> "user")
        data += ("classname" -> "User")
        data += ("objectame" -> "user")
        
        // TODO i want to create "fields" as a collection of Field java beans (i think)
        val fields = new ArrayBuffer[Field]
        fields += new Field("id", "int", true)
        fields += new Field("username", "String", true)
        fields += new Field("password", "String", true)
        fields += new Field("email", "String", false)
        val fieldsAsJavaList : java.util.List[Field] = fields
        data.put("fields", fieldsAsJavaList)
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

    
    private def createTextareaWidgetInsideScrollPane(text: String): JScrollPane = {
        // TODO come up with a smarter way of setting the editor/dialog size
        val textArea = new JTextArea(40, 130)
        textArea.setFont(new Font("Monaco", Font.PLAIN, 12))
        textArea.setText(text)
        textArea.setCaretPosition(0)
        textArea.setEditable(false)
        new JScrollPane(textArea)
    }
    
    private def showSourceCodeDialog(title: String, textToDisplay: String) {
        JOptionPane.showMessageDialog(
            null,
            createTextareaWidgetInsideScrollPane(textToDisplay),
            title,
            JOptionPane.INFORMATION_MESSAGE)
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

