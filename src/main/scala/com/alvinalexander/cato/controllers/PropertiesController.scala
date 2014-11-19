package com.alvinalexander.cato.controllers

import com.alvinalexander.cato.MainGuiController
import com.alvinalexander.cato.view.PropertiesPanel
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import com.alvinalexander.cato.model.Database
import com.alvinalexander.cato.utils.GuiUtils
import scala.util.{Try, Success, Failure}
import com.alvinalexander.cato.model.CatoUtils
import javax.swing.JFileChooser
import com.alvinalexander.cato.utils.SwingUtils

class PropertiesController (mainController: MainGuiController) {

    val propertiesPanel = new PropertiesPanel
    
    // database
    val urlField = propertiesPanel.getUrlTextField
    val driverField = propertiesPanel.getDriverTextField
    val usernameField = propertiesPanel.getUsernameTextField
    val passwordField = propertiesPanel.getPasswordTextField
    val connectButton = propertiesPanel.getConnectDisconnectButton
    val successfullyConnectedImageLabel = propertiesPanel.getConnectSuccessLabel  // turn green when connected
    successfullyConnectedImageLabel.setText("")
    
    // templates
    val templatesDirectoryField = propertiesPanel.getTemplatesDirectoryTextField
    val templatesDirectoryButton = propertiesPanel.getSelectTemplatesDirectoryButton
    
    // temporary data
    // TODO replace this stuff with preferences
    urlField.setText("jdbc:mysql://localhost:8889/finance")
    driverField.setText("com.mysql.jdbc.Driver")
    usernameField.setText("root")
    passwordField.setText("root")
    templatesDirectoryField.setText("/Users/Al/Projects/Scala/CatoGui/resources/templates")
    
    // state
    var connectTextIsShowing = true

    // listeners
    connectButton.addActionListener(new ConnectButtonListener(this))
    templatesDirectoryButton.addActionListener(new TemplatesDirectoryButtonListener(this))
    
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
    
    def handleTemplatesDirectoryButtonClicked {
        val tmpDirname = getUserSelectedDirectory
        if (tmpDirname==null || tmpDirname.trim.equals("")) return
        templatesDirectoryField.setText(tmpDirname)
    }
    
    // TODO using FileDialog is correct on Mac OS X and Java 7.
    // may want to do something different for Linux and Windows.
    private def getUserSelectedDirectory: String = {
        val fileDialog = SwingUtils.letUserChooseFile(null, null)  // (mainFrame, defaultDir)
        SwingUtils.getCanonicalFilenameFromFileDialog(fileDialog)
    }
  
}


class ConnectButtonListener(handler: PropertiesController) extends ActionListener {
  
    def actionPerformed(e: ActionEvent) {
        handler.handleConnectButtonClicked
    }

}

class TemplatesDirectoryButtonListener(handler: PropertiesController) extends ActionListener {
  
    def actionPerformed(e: ActionEvent) {
        handler.handleTemplatesDirectoryButtonClicked
    }

}



