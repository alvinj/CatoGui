package com.alvinalexander.cato.controllers

import com.alvinalexander.cato.MainGuiController
import com.alvinalexander.cato.view.TablesFieldsTemplatesPanel
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import javax.swing.ListSelectionModel
import scala.collection.JavaConversions._
import javax.swing.DefaultListModel

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
    
    // models    
//    fieldsJList.setModel(new AbstractListModel {
//    String[] values = {
//        "id",
//        "username",
//        "password",
//        "email_address",
//        "primary_role"
//    };

    val databaseTablesModel = new DefaultListModel[String]
    databaseTablesJList.setModel(databaseTablesModel)
    
    generateCodeButton.addActionListener(new GenerateCodeButtonListener(this))
    
    def setTableNames(dbTableNames: Seq[String]) {
        databaseTablesModel.clear
        for (table <- dbTableNames) databaseTablesModel.addElement(table)
    }
    
    def handleGenerateCodeButtonClicked {
        // make sure one table is selected, one or more fields are selected, and a template is selected
    }

}

class GenerateCodeButtonListener(handler: TablesFieldsTemplatesController) extends ActionListener {
  
    def actionPerformed(e: ActionEvent) {
        handler.handleGenerateCodeButtonClicked
    }

}

