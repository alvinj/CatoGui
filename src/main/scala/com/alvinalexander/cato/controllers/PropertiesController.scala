package com.alvinalexander.cato.controllers

import com.alvinalexander.cato.view.PropertiesPanel
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import com.alvinalexander.cato.model.Database
import com.alvinalexander.cato.utils.GuiUtils
import scala.util.{Try, Success, Failure}
import com.alvinalexander.cato.model.CatoUtils
import javax.swing.JFileChooser
import com.alvinalexander.cato.utils.SwingUtils
import com.alvinalexander.cato.CatoGui
import java.awt.Dimension

class PropertiesController (
      mainController: CatoGui,
      driver: String, 
      url: String, 
      username: String, 
      password: String, 
      templatesDir: String) {

    val propertiesPanel = new PropertiesPanel
    
    // database
    val urlField = propertiesPanel.getUrlTextField
    val driverField = propertiesPanel.getDriverTextField
    val usernameField = propertiesPanel.getUsernameTextField
    val passwordField = propertiesPanel.getPasswordTextField
    val connectButton = propertiesPanel.getConnectDisconnectButton
    val successfullyConnectedImageLabel = propertiesPanel.getConnectSuccessLabel  // turn green when connected
    
    // TODO get rid of this label in the java code, it's messing up the 'Connect' button position
    successfullyConnectedImageLabel.setText("")
    successfullyConnectedImageLabel.setPreferredSize(new Dimension(300, 15))
    
    // templates
    val templatesDirectoryField = propertiesPanel.getTemplatesDirectoryTextField
    val templatesDirectoryButton = propertiesPanel.getSelectTemplatesDirectoryButton
    
    // temporary data
    urlField.setText(url)
    driverField.setText(driver)
    usernameField.setText(username)
    passwordField.setText(password)
    templatesDirectoryField.setText(templatesDir)
    
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
        val db = new Database(driver, url, username, password)
        val result = mainController.tryConnectingToDatabase(db)
        // it's a little bit less of a pain (less typing) for the user to remember these before checking to see
        // if the connection was successful.
        saveDataToPreferences(url, driver, username, password, templatesDirectoryField.getText)
        result match {
            case Success(noop) => 
                connectButton.setText("Disconnect")
                connectTextIsShowing = false
            case Failure(throwable) => 
                GuiUtils.showErrorDialogWithLongText("Connection Failed", CatoUtils.getStackTraceString(throwable))
        }
    }
    
    private def saveDataToPreferences(url: String, driver: String, username: String, password: String, templatesDir: String) {
        mainController.saveUrl(url)
        mainController.saveDriver(driver)
        mainController.saveUsername(username)
        mainController.savePassword(password)
        if (templatesDir != null) mainController.saveTemplatesDir(templatesDir)
    }
    
    private def handleDisconnectFromDatabaseProcess {
        mainController.tryDisconnectingFromDatabase
        connectButton.setText("Connect")
        connectTextIsShowing = true
    }
    
    private def getTextFromTextFields = (urlField.getText, driverField.getText, usernameField.getText, passwordField.getText)
    
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
        mainController.saveTemplatesDir(tmpDirname)
    }
    
    // TODO using FileDialog is correct on Mac OS X and Java 7.
    // may want to do something different for Linux and Windows.
    private def getUserSelectedDirectory: String = {
        val fileDialog = SwingUtils.letUserChooseFile(null, null)  // (mainFrame, defaultDir)
        SwingUtils.getCanonicalFilenameFromFileDialog(fileDialog)
    }
    
    def getTemplatesDir = templatesDirectoryField.getText
  
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



