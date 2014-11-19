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
        // TODO make sure one table is selected, one or more fields are selected, and a template is selected
        val dbTable = databaseTablesJList.getSelectedValue
        val fields = tableFieldsJList.getSelectedValues
        val localTemplateFile = templatesJList.getSelectedValue
        // TODO validate all of those
        val templateDir = mainController.getTemplateDir
        // TODO use the system's file separator
        val canonTemplateFile = templateDir + "/" + localTemplateFile
        val templateText = JavaFileUtils.readFileAsString(canonTemplateFile) 
        val generatedCode = CodeGenerator.generateCode(templateText)
        // show output
        showSourceCodeDialog("Generated Source Code", generatedCode)
    }

    
    private def createTextareaWidgetInsideScrollPane(text: String): JScrollPane = {
        // TODO come up with a smarter way of setting the editor/dialog size
        val textArea = new JTextArea(50, 130)
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

