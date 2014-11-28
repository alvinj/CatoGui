package com.alvinalexander.cato

import com.alvinalexander.cato.controllers._
import com.alvinalexander.cato.model.Database
import com.alvinalexander.cato.model.DatabaseUtils
import java.sql.Connection
import scala.util.{Try, Success, Failure}
import com.alvinalexander.cato.utils.FileUtils
import com.alvinalexander.cato.model.TableUtils
import java.sql.DatabaseMetaData
import com.alvinalexander.cato.utils.GuiUtils
import com.alvinalexander.cato.model.CatoUtils
import scala.collection.mutable.ArrayBuffer
import com.devdaily.dbgrinder.model.ColumnData
import com.alvinalexander.cato.utils.ClassUtils
import java.util.prefs._
import com.alvinalexander.cato.utils.JavaFileUtils
import com.alvinalexander.annotations.impure
import org.clapper.argot._
import ArgotConverters._
import Strings._

/**
 * This class has the responsibility of starting Cato's GUI, and also holding
 * references to the key objects needed by this application. The biggest
 * object that other classes need is the `DatabaseMetaData` object, from
 * which all the source code can be generated.
 * 
 * Because it's the main class, it also handles data saved for the user
 * preferences.
 */
class CatoGui(dataTypesMappingFilename: String) {

    // technically these aren't needed until the user wants to generate some code, but i put it first so
    // the program will die if the Data Types Mapping file isn't found at startup.
    val allDataTypeMappingsAsJsonString = JavaFileUtils.readFileAsString(dataTypesMappingFilename)
    val allDataTypesAsMap = DataTypeMappingsController.getAllDataTypesAsMap(allDataTypeMappingsAsJsonString)

    // the user's last-used info is stored as preferences
    val prefs = Preferences.userNodeForPackage(this.getClass)
    val lastDbDriver     = prefs.get(DRIVER, "")
    val lastDbUrl        = prefs.get(URL, "")
    val lastDbUsername   = prefs.get(USERNAME, "")
    val lastDbPassword   = prefs.get(PASSWORD, "")
    val lastTemplatesDir = prefs.get(TEMPLATES_DIR, "")
    
    // controllers
    val propertiesController = new PropertiesController(this, lastDbDriver, lastDbUrl, lastDbUsername, lastDbPassword, lastTemplatesDir)
    val tablesFieldsTemplatesController = new TablesFieldsTemplatesController(this)
    val mainFrameController = new MainFrameController(this, propertiesController, tablesFieldsTemplatesController)
    
    var connection: Connection = null
    var metaData: DatabaseMetaData = null

    mainFrameController.displayTheGui
    
    /**
     * the properties controller calls this method when the user clicks
     * the "connect to database" button.
     */
    @impure def tryConnectingToDatabase(db: Database): Try[String] = {
        val possibleConnection = DatabaseUtils.makeConnection(db)
        possibleConnection match {
            case Success(conn) => 
                 connection = conn
                 metaData = DatabaseUtils.getTableMetaData(connection).get //TODO do this right
                 val dbTableNames = DatabaseUtils.getTableNames(connection, metaData)
                 tablesFieldsTemplatesController.handleDatabaseConnectEvent
                 tablesFieldsTemplatesController.setTableNames(dbTableNames)
                 Success("")
            case Failure(throwable) =>
                 Failure(throwable)
        }
    }
    
    /**
     * returns `true` if successful, `false` otherwise.
     */
    @impure def tryDisconnectingFromDatabase: Boolean = {
        try {
            if (connection == null) return false
            // TODO this isn't working 100% as desired after a connect/disconnect/connect series
            tablesFieldsTemplatesController.handleDatabaseDisconnectEvent
            connection.close
            connection = null
            true
        } catch {
            case t: Throwable => false
        }
    }

    // preferences
    @impure def saveDriver(driver: String)             { prefs.put(DRIVER, driver)} 
    @impure def saveUrl(url: String)                   { prefs.put(URL, url)} 
    @impure def saveUsername(username: String)         { prefs.put(USERNAME, username)} 
    @impure def savePassword(password: String)         { prefs.put(PASSWORD, password)} 
    @impure def saveTemplatesDir(templatesDir: String) { prefs.put(TEMPLATES_DIR, templatesDir)} 

    /**
     * a pass-thru method, currently used to keep the TFTController from talking to the PropertiesController.
     */
    @impure def getTemplateDir = propertiesController.getTemplatesDir
    
    /**
     * the user is trying to close the mainframe
     */
    @impure def handleWindowClosingEvent {
        tryDisconnectingFromDatabase
        System.exit(0)
    }

}

/**
 * this is where the action begins.
 */
object CatoGui extends App {

    /**
     * Use Argot to read command-line arguments.
     * At this time the need for the "Data Types Mapping" file isn't really optional,
     * so halt the program if it can't be found. 
     */
    val parser = new ArgotParser("Cato", preUsage=Some("Cato, Version 0.1. Copyright (c) 2014, Alvin J. Alexander (alvinalexander.com)."))
    
    /**
     * This line is Argot's way of saying that we support the `-m filename` and `--mappingfile filename`
     * command line options.
     */
    val mappingFileOption = parser.option[String](List("m", "mappingfile"), 
                                            "filename", 
                                            "The canonical name of the data types mapping file.")

    /**
     * Get the mappings file at the command line, or die.
     * If the flag and filename are there, attempt to start the application.
     * My mappings file is at /Users/Al/Projects/Scala/CatoGui/resources/datatypemappings.json
     */
    var mappingFilename = ""
    try {
        parser.parse(args)
        mappingFileOption.value match {
            case Some(filename) => {
                mappingFilename = filename
                startCatoGui
            }
            case None => {
                System.err.println("The 'Data Types Mapping' file was not specified; can't run without it.")
                printUsageStatementAndDie
            } 
        }
    } catch {
        case e: ArgotUsageException => 
            e.printStackTrace
            printUsageStatementAndDie
    }
    
    private def startCatoGui {
        try {
            new CatoGui(mappingFilename)
        } catch {
            case t: Throwable => {
                GuiUtils.showErrorDialogWithLongText("Boom!", CatoUtils.getStackTraceString(t))
            }
        }
    }
    
    private def printUsageStatementAndDie {
        System.err.println("")
        System.err.println("Usage: java -jar cato-0.1.jar -m dataTypesMapping.conf")
        System.err.println("")
        System.exit(1)
    }
}



















