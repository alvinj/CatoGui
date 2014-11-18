package com.alvinalexander.cato.controllers

import com.alvinalexander.cato.MainGuiController
import com.alvinalexander.cato.view.TablesFieldsTemplatesPanel

class TablesFieldsTemplatesController (mainController: MainGuiController) {
  
    val tablesFieldsTemplatesPanel = new TablesFieldsTemplatesPanel
    val databaseTablesJList = tablesFieldsTemplatesPanel.getTablesJList
    val tableFieldsJList = tablesFieldsTemplatesPanel.getFieldsJList
    val templatesJList = tablesFieldsTemplatesPanel.getTemplatesJList
    val generateCodeButton = tablesFieldsTemplatesPanel.getButton2

}