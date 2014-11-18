package com.alvinalexander.cato.controllers

import com.alvinalexander.cato.MainGuiController
import com.alvinalexander.cato.view.PropertiesPanel
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import com.alvinalexander.cato.model.Database
import com.alvinalexander.cato.utils.GuiUtils
import scala.util.{Try, Success, Failure}
import com.alvinalexander.cato.model.CatoUtils

class PropertiesController (mainController: MainGuiController) {

    val propertiesPanel = new PropertiesPanel
    
    // database
    val urlField = propertiesPanel.getUrlTextField
    val driverField = propertiesPanel.getDriverTextField
    val usernameField = propertiesPanel.getUsernameTextField
    val passwordField = propertiesPanel.getPasswordTextField
    val connectButton = propertiesPanel.getConnectDisconnectButton
    val successfullyConnectedImageLabel = propertiesPanel.getConnectSuccessLabel  // turn green when connected
    
    // temporary data
    urlField.setText("jdbc:mysql://localhost:8889/finance")
    driverField.setText("com.mysql.jdbc.Driver")
    usernameField.setText("root")
    passwordField.setText("root")
    
    // templates
    val templatesDirectoryField = propertiesPanel.getTemplatesDirectoryTextField
    val templatesDirectoryButton = propertiesPanel.getSelectTemplatesDirectoryButton
    
    // state
    var connectTextIsShowing = true
    
    connectButton.addActionListener(new ConnectButtonListener(this))
    
    def handleConnectButtonClicked {
        if (connectTextIsShowing) {
            handleConnectToDatabaseProcess
        } else {
            handleDisconnectFromDatabaseProcess
        }
    }
    
    private def handleConnectToDatabaseProcess {
        val (url, driver, username, password) = getTextFromTextFields
        if (stringsAreNullOrBlank(Seq(url, driver, username, password))) {
            GuiUtils.showErrorDialog("Validation Error", "Please fill in all of the database-related fields.")
            return
        }
        // fields validated; try to connect to the database
        val db = new Database(url, driver, username, password)
        val result = mainController.tryConnectingToDatabase(db)
        result match {
            case Success(noop) => 
                connectButton.setText("Disconnect")
                connectTextIsShowing = false
            case Failure(throwable) => 
                GuiUtils.showErrorDialogWithLongText("Connection Failed", CatoUtils.getStackTraceString(throwable))
        }
    }
    
    // TODO implement this
    private def handleDisconnectFromDatabaseProcess {
        // do this stuff at the end
        connectButton.setText("Connect")
        connectTextIsShowing = true
    }
    
    private def getTextFromTextFields = (driverField.getText, urlField.getText, usernameField.getText, passwordField.getText)
    
    private def stringsAreNullOrBlank(strings: Seq[String]): Boolean = {
        for (s <- strings) {
            if (s == null || s.trim == "") true
        }
        false
    }
    
}


class ConnectButtonListener(handler: PropertiesController) extends ActionListener {
  
    def actionPerformed(e: ActionEvent) {
        handler.handleConnectButtonClicked
    }

}


