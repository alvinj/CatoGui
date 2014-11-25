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
import com.alvinalexander.cato.model.DataTypeMappings
import com.alvinalexander.cato.utils.JavaFileUtils
import com.alvinalexander.annotations.impure

/**
 * right now this class is growing into the "main controller" for the gui app.
 * might want to off-load some of this.
 */
class CatoGui {
  
    // the user's last-used info is stored as preferences
    val prefs = Preferences.userNodeForPackage(this.getClass)
    
    // TODO clean up this "properties" code
    val DRIVER = "DRIVER"
    val URL    = "URL"
    val USERNAME = "USERNAME"
    val PASSWORD = "PASSWORD"
    val TEMPLATES_DIR = "TEMPLATES_DIR"
    val DATA_TYPE_MAP_NAME = "DATA_TYPE_MAP_NAME"
    
    val lastDbDriver     = prefs.get(DRIVER, "")
    val lastDbUrl        = prefs.get(URL, "")
    val lastDbUsername   = prefs.get(USERNAME, "")
    val lastDbPassword   = prefs.get(PASSWORD, "")
    val lastTemplatesDir = prefs.get(TEMPLATES_DIR, "")
    val lastDataTypeMapName = prefs.get(DATA_TYPE_MAP_NAME, DataTypeMappings.JAVA)
    
    def saveDriver(driver: String)             { prefs.put(DRIVER, driver)} 
    def saveUrl(url: String)                   { prefs.put(URL, url)} 
    def saveUsername(username: String)         { prefs.put(USERNAME, username)} 
    def savePassword(password: String)         { prefs.put(PASSWORD, password)} 
    def saveTemplatesDir(templatesDir: String) { prefs.put(TEMPLATES_DIR, templatesDir)} 
    def saveDataTypeMapName(dataTypeMapName: String) { prefs.put(DATA_TYPE_MAP_NAME, dataTypeMapName) }

    // controllers
    val propertiesController = new PropertiesController(this, lastDbDriver, lastDbUrl, lastDbUsername, lastDbPassword, lastTemplatesDir)
    
    // TODO get rid of this old controller
    val dataTypeMappingsController = new DataTypeMappingsController(this)
    val tablesFieldsTemplatesController = new TablesFieldsTemplatesController(this)
    val mainFrameController = new MainFrameController(this, propertiesController, dataTypeMappingsController, tablesFieldsTemplatesController)
    
    // initialize data
    dataTypeMappingsController.setDataTypeMap(lastDataTypeMapName)
    
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
        
        // get the currently-selected field types map
        val dataTypesMap = dataTypeMappingsController.currentDataTypeMap

        // get the data type maps from the json config file and use them
        val allDataTypeMappingsAsJsonString = JavaFileUtils.readFileAsString("/Users/Al/Projects/Scala/CatoGui/resources/datatypemappings.json")
        val allDataTypesAsMap = DataTypeMappingsController2.getAllDataTypesAsMap(allDataTypeMappingsAsJsonString)

        val javaTypesMap         = DataTypeMappingsController2.getDataTypeMap(DataTypeMappingsController2.JAVA, allDataTypesAsMap)
        val jsonTypesMap         = DataTypeMappingsController2.getDataTypeMap(DataTypeMappingsController2.JSON, allDataTypesAsMap)
        val phpTypesMap          = DataTypeMappingsController2.getDataTypeMap(DataTypeMappingsController2.PHP, allDataTypesAsMap)
        val playTypesMap         = DataTypeMappingsController2.getDataTypeMap(DataTypeMappingsController2.PLAY, allDataTypesAsMap)
        val playOptionalTypesMap = DataTypeMappingsController2.getDataTypeMap(DataTypeMappingsController2.PLAY_OPTIONAL, allDataTypesAsMap)
        val scalaTypesMap        = DataTypeMappingsController2.getDataTypeMap(DataTypeMappingsController2.SCALA, allDataTypesAsMap)
        
        // TODO working here ...
        val javaFieldTypes         = TableUtils.getFieldTypes(columnData, fieldsTheUserSelected, javaTypesMap)
        val jsonFieldTypes         = TableUtils.getFieldTypes(columnData, fieldsTheUserSelected, jsonTypesMap)
        val phpFieldTypes          = TableUtils.getFieldTypes(columnData, fieldsTheUserSelected, phpTypesMap)
        val playFieldTypes         = TableUtils.getFieldTypes(columnData, fieldsTheUserSelected, playTypesMap)
        val playOptionalFieldTypes = TableUtils.getFieldTypes(columnData, fieldsTheUserSelected, playOptionalTypesMap)
        val scalaFieldTypes        = TableUtils.getFieldTypes(columnData, fieldsTheUserSelected, scalaTypesMap)
        
        // TODO this is old, delete
        val fieldTypes = TableUtils.getFieldTypes(columnData, fieldsTheUserSelected, dataTypesMap)
        
        
        val databaseFieldTypes = TableUtils.getDatabaseFieldTypes(columnData, fieldsTheUserSelected)
        val fieldRequiredValues = TableUtils.getFieldsRequiredStatus(columnData, fieldsTheUserSelected)
        val numFields = fieldNames.length
        val fields = new ArrayBuffer[Field]
        for (i <- 0 until numFields) {
            fields += new Field(
                              fieldNames(i), 
                              camelCasefieldNames(i), 
                              fieldTypes(i), 
                              databaseFieldTypes(i), 
                              fieldRequiredValues(i)
                              )
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

    // TODO i couldn't get these to work easily, and this isn't very important to me.
//    try {
//        //ClassUtils.loadAllClasses("/Users/Al/Projects/Scala/CatoGui/resources/mysql-connector-java-5.1.34-bin.jar")
//        //ClassUtils.loadOnlyMySql("/Users/Al/Projects/Scala/CatoGui/resources/mysql-connector-java-5.1.34-bin.jar")
//    } catch {
//        case t: Throwable => t.printStackTrace 
//    }
    
    try {
        new CatoGui
    } catch {
        case t: Throwable => GuiUtils.showErrorDialogWithLongText(
                                 "Boom!", 
                                 CatoUtils.getStackTraceString(t))
    }
}



















