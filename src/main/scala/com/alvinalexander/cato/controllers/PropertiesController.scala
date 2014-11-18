package com.alvinalexander.cato.controllers

import com.alvinalexander.cato.MainGuiController
import com.alvinalexander.cato.view.PropertiesPanel

class PropertiesController (mainController: MainGuiController) {

    val propertiesPanel = new PropertiesPanel
    
    // database
    val urlField = propertiesPanel.getUrlTextField
    val driverField = propertiesPanel.getDriverTextField
    val usernameField = propertiesPanel.getUsernameTextField
    val passwordField = propertiesPanel.getPasswordTextField
    val connectButton = propertiesPanel.getConnectDisconnectButton
    val successfullyConnectedImageLabel = propertiesPanel.getConnectSuccessLabel  // turn green when connected
    
    // templates
    val templatesDirectoryField = propertiesPanel.getTemplatesDirectoryTextField
    val templatesDirectoryButton = propertiesPanel.getSelectTemplatesDirectoryButton
    
}


