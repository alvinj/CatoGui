package com.alvinalexander.cato.controllers

import com.alvinalexander.cato.CatoGui
import com.alvinalexander.cato.view.DataTypeMappingsPanel
import javax.swing.DefaultComboBoxModel

class DataTypeMappingsController(mainController: CatoGui) {
  
    val dataTypeMappingsPanel = new DataTypeMappingsPanel
    
    val builtInMappingsComboBox = dataTypeMappingsPanel.getBuiltInMappingsComboBox    
    val dataTypesModel = new DefaultComboBoxModel(Array("Java", "JSON", "PHP", "Play", "Scala"))
    builtInMappingsComboBox.setModel(dataTypesModel)

}