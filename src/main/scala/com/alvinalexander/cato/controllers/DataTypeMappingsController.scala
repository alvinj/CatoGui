package com.alvinalexander.cato.controllers

import com.alvinalexander.cato.CatoGui
import com.alvinalexander.cato.view.DataTypeMappingsPanel
import javax.swing.DefaultComboBoxModel
import java.awt.event.ItemListener
import java.awt.event.ItemEvent
import com.alvinalexander.cato.model.DataTypeMappings
import com.alvinalexander.cato.model.DataTypeMappings._

class DataTypeMappingsController(mainController: CatoGui) {
  
    val dataTypeMappingsPanel = new DataTypeMappingsPanel
    val blobTextField = dataTypeMappingsPanel.getBlobTextField
    val booleanTextField = dataTypeMappingsPanel.getBooleanTextField
    val dateTextField = dataTypeMappingsPanel.getDateTextField
    val integerTextField = dataTypeMappingsPanel.getIntegerTextField
    val longTextField = dataTypeMappingsPanel.getLongTextField
    val floatTextField = dataTypeMappingsPanel.getFloatTextField
    val textVarcharTextField = dataTypeMappingsPanel.getTextVarcharTextField
    val timestampTextField = dataTypeMappingsPanel.getTimestampTextField
    
    val builtInMappingsComboBox = dataTypeMappingsPanel.getBuiltInMappingsComboBox    
    val dataTypesModel = new DefaultComboBoxModel(Array(JAVA, JSON, PHP, PLAY, SCALA))
    builtInMappingsComboBox.setModel(dataTypesModel)
    
    initializeTextFields
    
    // update the fields when the combobox is changed
    val itemListener = new ItemListener {
        def itemStateChanged(itemEvent: ItemEvent) {
            val state = itemEvent.getStateChange
            if (state == ItemEvent.SELECTED) {
                val item = itemEvent.getItem.toString
                // get the right map, then update the textfields
                val dataTypesMap = DataTypeMappings.dataTypesMap(item)
                updateTextFieldsWithMap(dataTypesMap)
            }
        }
    }
    
    def initializeTextFields {
        val initialDataTypesMap = DataTypeMappings.dataTypesMap("Java")
        updateTextFieldsWithMap(initialDataTypesMap)
    }
    
    private def updateTextFieldsWithMap(dataTypesMap: Map[String, String]) {
        blobTextField.setText(dataTypesMap(BLOB))
        booleanTextField.setText(dataTypesMap(BOOLEAN))
        dateTextField.setText(dataTypesMap(DATE))
        integerTextField.setText(dataTypesMap(INTEGER))
        longTextField.setText(dataTypesMap(LONG))
        floatTextField.setText(dataTypesMap(FLOAT))
        textVarcharTextField.setText(dataTypesMap(TEXT))
        timestampTextField.setText(dataTypesMap(TIMESTAMP))
    }
    
    builtInMappingsComboBox.addItemListener(itemListener)
    
}





