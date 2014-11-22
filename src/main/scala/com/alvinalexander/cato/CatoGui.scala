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

//trait MainGuiController {
//    def tryConnectingToDatabase(db: Database): Try[String]
//    def handleWindowClosingEvent
//    def getListOfTemplateFiles: Option[Seq[String]]
//    def getFieldsForTableName(dbTableName: String): Seq[String]
//    def getTemplateDir: String
//    def getFieldDataForTableName(dbTableName: String): Seq[Field]
//    //def getFieldsForTableName(dbTableName: String): Seq[String]
//    def getPreparedStatementInsertString(dbTableName: String): String
//    def getPreparedStatementUpdateString(dbTableName: String): String
//    def getFieldNamesAsCsvString(dbTableName: String): String
//    def getFieldNamesCamelCasedAsCsvString(dbTableName: String): String
//}

/**
 * right now this class is growing into the "main controller" for the gui app.
 * might want to off-load some of this.
 */
class CatoGui {
  
    val propertiesController = new PropertiesController(this)
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
    
    // getFieldNamesCamelCasedAsCsvString
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
    
    def getFieldDataForTableName(dbTableName: String, fieldsTheUserSelected: Seq[String]): Seq[Field] = {
        // TODO do this properly
        val columnData = TableUtils.getColumnData(dbTableName, metaData, catalog=null, schema=null, typesAreStrings=true).get
        val fieldNames = TableUtils.getFieldNames(columnData, fieldsTheUserSelected)
        val camelCasefieldNames = TableUtils.getCamelCaseFieldNames(columnData, fieldsTheUserSelected)
        val fieldTypes = TableUtils.getJavaFieldTypes(columnData, fieldsTheUserSelected)
        val databaseFieldTypes = TableUtils.getDatabaseFieldTypes(columnData, fieldsTheUserSelected)
        val fieldRequiredValues = TableUtils.getFieldsRequiredStatus(columnData, fieldsTheUserSelected)
        val numFields = fieldNames.length
        val fields = new ArrayBuffer[Field]
        for (i <- 0 until numFields) {
            fields += new Field(fieldNames(i), camelCasefieldNames(i), fieldTypes(i), databaseFieldTypes(i), fieldRequiredValues(i))
        }
        fields.toSeq
    }
    
    def getTemplateDir = propertiesController.getTemplatesDir
    
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
    
    try {
        new CatoGui
    } catch {
        case t: Throwable => GuiUtils.showErrorDialogWithLongText(
                                 "Boom!", 
                                 CatoUtils.getStackTraceString(t))
    }
}










