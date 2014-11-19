package com.alvinalexander.cato

import com.alvinalexander.cato.controllers._
import com.alvinalexander.cato.model.Database
import com.alvinalexander.cato.model.DatabaseUtils
import java.sql.Connection
import scala.util.{Try, Success, Failure}
import com.alvinalexander.cato.utils.FileUtils

trait MainGuiController {
    def tryConnectingToDatabase(db: Database): Try[String]
    def handleWindowClosingEvent
    def getListOfTemplateFiles: Option[Seq[String]]
}

/**
 * right now this class is growing into the "main controller" for the gui app.
 * might want to off-load some of this.
 */
class CatoGui extends MainGuiController {
  
    val propertiesController = new PropertiesController(this)
    val tablesFieldsTemplatesController = new TablesFieldsTemplatesController(this)
    val mainFrameController = new MainFrameController(this, propertiesController, tablesFieldsTemplatesController)
    
    var connection: Connection = null

    mainFrameController.displayTheGui
    
    def tryConnectingToDatabase(db: Database): Try[String] = {
        val possibleConnection = DatabaseUtils.makeConnection(db)
        possibleConnection match {
            case Success(conn) => 
                 connection = conn
                 // TODO get the list of db tables and update the gui
                 val metaData = DatabaseUtils.getTableMetaData(connection).get //TODO do this right
                 val dbTableNames = DatabaseUtils.getTableNames(connection, metaData)
                 tablesFieldsTemplatesController.setTableNames(dbTableNames)
                 Success("")
            case Failure(throwable) =>
                 Failure(throwable)
        }
    }
    
    def getListOfTemplateFiles: Option[Seq[String]] = {
        val templatesDir = propertiesController.getTemplatesDir
        if (templatesDir==null || templatesDir.trim=="") {
            None
        } else {
            Some(FileUtils.getListOfFilesInDirectory(templatesDir))
        }
            
    }
    
    /**
     * the user is trying to close the mainframe
     */
    def handleWindowClosingEvent {
        // TODO implement this - ask if sure; close the connection; update prefs?
        System.exit(0)
    }

}

/**
 * Just start the app.
 */
object CatoGui extends App {
    new CatoGui
}










