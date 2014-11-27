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

/**
 * right now this class is growing into the "main controller" for the gui app.
 * might want to off-load some of this.
 */
class CatoGui(dataTypesMappingFilename: String) {
  
    // this isn't needed until the user wants to generate some code, but i put it first so
    // the program will die if the Data Types Mapping file isn't found at startup.
    val allDataTypeMappingsAsJsonString = JavaFileUtils.readFileAsString(dataTypesMappingFilename)
    val allDataTypesAsMap = DataTypeMappingsController.getAllDataTypesAsMap(allDataTypeMappingsAsJsonString)

    // the user's last-used info is stored as preferences
    val prefs = Preferences.userNodeForPackage(this.getClass)
    
    // TODO clean up this "properties" code
    val DRIVER = "DRIVER"
    val URL    = "URL"
    val USERNAME = "USERNAME"
    val PASSWORD = "PASSWORD"
    val TEMPLATES_DIR = "TEMPLATES_DIR"
    
    val lastDbDriver     = prefs.get(DRIVER, "")
    val lastDbUrl        = prefs.get(URL, "")
    val lastDbUsername   = prefs.get(USERNAME, "")
    val lastDbPassword   = prefs.get(PASSWORD, "")
    val lastTemplatesDir = prefs.get(TEMPLATES_DIR, "")
    
    def saveDriver(driver: String)             { prefs.put(DRIVER, driver)} 
    def saveUrl(url: String)                   { prefs.put(URL, url)} 
    def saveUsername(username: String)         { prefs.put(USERNAME, username)} 
    def savePassword(password: String)         { prefs.put(PASSWORD, password)} 
    def saveTemplatesDir(templatesDir: String) { prefs.put(TEMPLATES_DIR, templatesDir)} 

    // controllers
    val propertiesController = new PropertiesController(this, lastDbDriver, lastDbUrl, lastDbUsername, lastDbPassword, lastTemplatesDir)
    val tablesFieldsTemplatesController = new TablesFieldsTemplatesController(this)
    val mainFrameController = new MainFrameController(this, propertiesController, tablesFieldsTemplatesController)
    
    var connection: Connection = null
    var metaData: DatabaseMetaData = null

    mainFrameController.displayTheGui
    
    def tryConnectingToDatabase(db: Database): Try[String] = {
        val possibleConnection = DatabaseUtils.makeConnection(db)
        possibleConnection match {
            case Success(conn) => 
                 connection = conn
                 // TODO get the list of db tables and update the gui
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
    def tryDisconnectingFromDatabase: Boolean = {
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
    
    def getListOfTemplateFiles: Option[Seq[String]] = {
        val templatesDir = propertiesController.getTemplatesDir
        if (templatesDir==null || templatesDir.trim=="") {
            None
        } else {
            Some(FileUtils.getListOfFilesInDirectory(templatesDir))
        }
    }
    
    def getFieldsForTableName(dbTableName: String): Seq[String] = {
        TableUtils.getFieldNames(getColumnData(dbTableName))
    }
    
    /**
     * the fields returned can be limited to the field names you supply in `desiredFields`.
     */
    def getPreparedStatementInsertString(dbTableName: String, desiredFields: Seq[String] = null): String = {
        if (desiredFields == null) {
            TableUtils.createPreparedStatementInsertString(getColumnData(dbTableName))
        } else {
            TableUtils.createPreparedStatementInsertString(getColumnData(dbTableName), desiredFields)
        }
    }
    
    /**
     * the fields returned can be limited to the field names you supply in `desiredFields`.
     */
    def getPreparedStatementUpdateString(dbTableName: String, desiredFields: Seq[String] = null): String = {
        if (desiredFields == null) {
            TableUtils.createPreparedStatementUpdateString(getColumnData(dbTableName))
        } else {
            TableUtils.createPreparedStatementUpdateString(getColumnData(dbTableName), desiredFields)
        }
    }

    /**
     * the fields returned can be limited to the field names you supply in `desiredFields`.
     * (this method returns the intersection of dbTable.getFields and 
     * the Seq you supply, which should just be the fields you supply,
     * in the order returned by the database.)
     */
    def getFieldNamesAsCsvString(dbTableName: String, desiredFields: Seq[String] = null): String = {
        if (desiredFields == null) {
            TableUtils.getFieldNamesAsCsvString(getColumnData(dbTableName))
        } else {
            TableUtils.getFieldNamesAsCsvString(getColumnData(dbTableName), desiredFields)
        }
    }
    
    def getFieldNamesCamelCasedAsCsvString(dbTableName: String, desiredFields: Seq[String] = null): String = {
        if (desiredFields == null) {
            TableUtils.getFieldNamesCamelCasedAsCsvString(getColumnData(dbTableName))
        } else {
            TableUtils.getFieldNamesCamelCasedAsCsvString(getColumnData(dbTableName), desiredFields)
        }
    }
    
    private def getColumnData(dbTableName: String): Seq[ColumnData] = {
        TableUtils.getColumnData(dbTableName, metaData, catalog=null, schema=null, typesAreStrings=true).get
    }
    
    // TODO this code is needed by CommandLineCato as well; should not be in CatoGui
    @impure def getFieldDataForTableName(dbTableName: String, fieldsTheUserSelected: Seq[String]): Seq[Field] = {
        // TODO do this properly (not using `get`)
        val columnData = TableUtils.getColumnData(dbTableName, metaData, catalog=null, schema=null, typesAreStrings=true).get
        val fieldNames = TableUtils.getFieldNames(columnData, fieldsTheUserSelected)
        val camelCasefieldNames = TableUtils.getCamelCaseFieldNames(columnData, fieldsTheUserSelected)
        
//        // get the data type maps from the json config file and use them
//        val allDataTypeMappingsAsJsonString = JavaFileUtils.readFileAsString(dataTypesMappingFilename)
//        val allDataTypesAsMap = DataTypeMappingsController.getAllDataTypesAsMap(allDataTypeMappingsAsJsonString)

        val javaTypesMap         = DataTypeMappingsController.getDataTypeMap(DataTypeMappingsController.JAVA, allDataTypesAsMap)
        val jsonTypesMap         = DataTypeMappingsController.getDataTypeMap(DataTypeMappingsController.JSON, allDataTypesAsMap)
        val phpTypesMap          = DataTypeMappingsController.getDataTypeMap(DataTypeMappingsController.PHP, allDataTypesAsMap)
        val playTypesMap         = DataTypeMappingsController.getDataTypeMap(DataTypeMappingsController.PLAY, allDataTypesAsMap)
        val playOptionalTypesMap = DataTypeMappingsController.getDataTypeMap(DataTypeMappingsController.PLAY_OPTIONAL, allDataTypesAsMap)
        val scalaTypesMap        = DataTypeMappingsController.getDataTypeMap(DataTypeMappingsController.SCALA, allDataTypesAsMap)
        val senchaTypesMap        = DataTypeMappingsController.getDataTypeMap(DataTypeMappingsController.SENCHA, allDataTypesAsMap)
        
        val javaFieldTypes         = TableUtils.getFieldTypes(columnData, fieldsTheUserSelected, javaTypesMap)
        val jsonFieldTypes         = TableUtils.getFieldTypes(columnData, fieldsTheUserSelected, jsonTypesMap)
        val phpFieldTypes          = TableUtils.getFieldTypes(columnData, fieldsTheUserSelected, phpTypesMap)
        val playFieldTypes         = TableUtils.getFieldTypes(columnData, fieldsTheUserSelected, playTypesMap)
        val playOptionalFieldTypes = TableUtils.getFieldTypes(columnData, fieldsTheUserSelected, playOptionalTypesMap)
        val scalaFieldTypes        = TableUtils.getFieldTypes(columnData, fieldsTheUserSelected, scalaTypesMap)
        val senchaFieldTypes       = TableUtils.getFieldTypes(columnData, fieldsTheUserSelected, senchaTypesMap)
        
        val databaseFieldTypes = TableUtils.getDatabaseFieldTypes(columnData, fieldsTheUserSelected)
        val fieldRequiredValues = TableUtils.getFieldsRequiredStatus(columnData, fieldsTheUserSelected)

        val numFields = fieldNames.length
        val fields = new ArrayBuffer[Field]
        for (i <- 0 until numFields) {
            fields += new Field(
                              fieldNames(i), 
                              camelCasefieldNames(i), 
                              javaFieldTypes(i),
                              jsonFieldTypes(i),
                              phpFieldTypes(i),
                              playFieldTypes(i),
                              playOptionalFieldTypes(i),
                              scalaFieldTypes(i),
                              senchaFieldTypes(i),
                              databaseFieldTypes(i), 
                              fieldRequiredValues(i))
        }
        fields.toSeq
    }
    
    def getTemplateDir = propertiesController.getTemplatesDir
    
    /**
     * the user is trying to close the mainframe
     */
    def handleWindowClosingEvent {
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



















