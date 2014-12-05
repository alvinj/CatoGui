package com.alvinalexander.cato.controllers

import java.io._
import freemarker.template._
import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer
import com.alvinalexander.cato.TemplateEngine
import com.alvinalexander.cato.model.TableUtils
import com.alvinalexander.cato.Field
import com.alvinalexander.annotations.impure
import com.devdaily.dbgrinder.model.ColumnData
import java.sql.DatabaseMetaData
import com.alvinalexander.cato.controllers.DataTypeMappingsController.DataTypeMap
import com.alvinalexander.inflector.Inflections
    
object CodeGenerator {

    /**
     * Given a template and some other information (database model info),
     * generate the output source code.
     */
    def generateCode(template: String): String = {
        template
    }
    
    def generateCode(rootTemplateDir: String, templateAsString: String, data: Map[String, Object]): String = {
        val jmap = new java.util.HashMap[String, Object](data)
        val result = TemplateEngine.applyDataToTemplate(rootTemplateDir, templateAsString, jmap)
        result
    }

    def buildDataObjectForTemplate(metaData: DatabaseMetaData, 
                                   allDataTypesAsMap: DataTypeMap, 
                                   dbTablename: String, 
                                   userSelectedFields: Seq[String]): scala.collection.immutable.Map[String, Object] = {
        val data = scala.collection.mutable.Map[String, Object]()
        
        // create the single values that the templates need
        data += ("tablename" -> dbTablename)
        val classname = TableUtils.convertTableNameToClassName(dbTablename)
        data += ("classname" -> classname)
        data += ("classnamePlural" -> Inflections.pluralize(classname))
        data += ("objectname" -> TableUtils.convertTableNameToObjectName(dbTablename))
        
        // TODO - NEED TO VERIFY THESE
        data += ("fieldsAsInsertCsvString"          -> getFieldNamesAsCsvString(metaData, dbTablename, userSelectedFields, false))
        data += ("fieldsAsInsertCsvStringWoIdField" -> getFieldNamesAsCsvString(metaData, dbTablename, userSelectedFields, true))
        data += ("fieldsAsCamelCaseCsvString"       -> getFieldNamesCamelCasedAsCsvString(metaData, dbTablename, userSelectedFields))
        data += ("prepStmtAsInsertCsvString"        -> getPreparedStatementInsertString(metaData, dbTablename, userSelectedFields))
        data += ("prepStmtAsUpdateCsvString"        -> getPreparedStatementUpdateString(metaData, dbTablename, userSelectedFields))

        // TODO need to add some more conversions here ...
    
        // create the array for "fields" for the template
        val fields = getFieldDataForTableName(metaData, allDataTypesAsMap, dbTablename, userSelectedFields)
        val fieldsAsJavaList : java.util.List[Field] = fields
        data.put("fields", fieldsAsJavaList)
        
        // return the Map the code generator will use
        data.toMap
    }

    // TODO this code is needed by CommandLineCato as well; should not be in CatoGui
    @impure def getFieldDataForTableName(metaData: DatabaseMetaData, allDataTypesAsMap: DataTypeMap, dbTableName: String, fieldsTheUserSelected: Seq[String]): Seq[Field] = {
        // TODO do this properly (not using `get`)
        val columnData = TableUtils.getColumnData(dbTableName, 
                                                  metaData,
                                                  catalog=null, 
                                                  schema=null, 
                                                  typesAreStrings=true).get
        val fieldNames = TableUtils.getFieldNames(columnData, fieldsTheUserSelected)
        val camelCasefieldNames = TableUtils.getCamelCaseFieldNames(columnData, fieldsTheUserSelected)
        
        val javaTypesMap           = DataTypeMappingsController.getDataTypeMap(DataTypeMappingsController.JAVA, allDataTypesAsMap)
        val jsonTypesMap           = DataTypeMappingsController.getDataTypeMap(DataTypeMappingsController.JSON, allDataTypesAsMap)
        val phpTypesMap            = DataTypeMappingsController.getDataTypeMap(DataTypeMappingsController.PHP, allDataTypesAsMap)
        val playTypesMap           = DataTypeMappingsController.getDataTypeMap(DataTypeMappingsController.PLAY, allDataTypesAsMap)
        val playOptionalTypesMap   = DataTypeMappingsController.getDataTypeMap(DataTypeMappingsController.PLAY_OPTIONAL, allDataTypesAsMap)
        val scalaTypesMap          = DataTypeMappingsController.getDataTypeMap(DataTypeMappingsController.SCALA, allDataTypesAsMap)
        val senchaTypesMap         = DataTypeMappingsController.getDataTypeMap(DataTypeMappingsController.SENCHA, allDataTypesAsMap)
        
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

    private def getColumnData(metaData: DatabaseMetaData, dbTableName: String): Seq[ColumnData] = {
        TableUtils.getColumnData(dbTableName, metaData, catalog=null, schema=null, typesAreStrings=true).get
    }
    
    def getFieldsForTableName(metaData: DatabaseMetaData, dbTableName: String): Seq[String] = {
        TableUtils.getFieldNames(getColumnData(metaData, dbTableName))
    }
    
    /**
     * the fields returned can be limited to the field names you supply in `desiredFields`.
     */
    def getPreparedStatementInsertString(metaData: DatabaseMetaData, dbTableName: String, desiredFields: Seq[String] = null): String = {
        if (desiredFields == null) {
            TableUtils.createPreparedStatementInsertString(getColumnData(metaData, dbTableName))
        } else {
            TableUtils.createPreparedStatementInsertString(getColumnData(metaData, dbTableName), desiredFields)
        }
    }
    
    /**
     * the fields returned can be limited to the field names you supply in `desiredFields`.
     */
    def getPreparedStatementUpdateString(metaData: DatabaseMetaData, dbTableName: String, desiredFields: Seq[String] = null): String = {
        if (desiredFields == null) {
            TableUtils.createPreparedStatementUpdateString(getColumnData(metaData, dbTableName))
        } else {
            TableUtils.createPreparedStatementUpdateString(getColumnData(metaData, dbTableName), desiredFields)
        }
    }

    /**
     * the fields returned can be limited to the field names you supply in `desiredFields`.
     * (this method returns the intersection of dbTable.getFields and 
     * the Seq you supply, which should just be the fields you supply,
     * in the order returned by the database.)
     */
    def getFieldNamesAsCsvString(metaData: DatabaseMetaData, 
                                 dbTableName: String,
                                 desiredFields: Seq[String] = null,
                                 excludeIdField: Boolean): String = {
        // TODO refactor this code, it's gotten out of control.
        //      related: is the getFieldNamesAsCsvStringWithoutIdField() method that important?
        //      can it be done in a template instead of here?
        if (excludeIdField) {
            if (desiredFields == null) {
                TableUtils.getFieldNamesAsCsvStringWithoutIdField(getColumnData(metaData, dbTableName), None)
            } else {
                TableUtils.getFieldNamesAsCsvStringWithoutIdField(getColumnData(metaData, dbTableName), Some(desiredFields))
            }
        } else {
            if (desiredFields == null) {
                TableUtils.getFieldNamesAsCsvString(getColumnData(metaData, dbTableName))
            } else {
                TableUtils.getFieldNamesAsCsvString(getColumnData(metaData, dbTableName), desiredFields)
            }
        }
    }
    
    def getFieldNamesCamelCasedAsCsvString(metaData: DatabaseMetaData, dbTableName: String, desiredFields: Seq[String] = null): String = {
        if (desiredFields == null) {
            TableUtils.getFieldNamesCamelCasedAsCsvString(getColumnData(metaData, dbTableName))
        } else {
            TableUtils.getFieldNamesCamelCasedAsCsvString(getColumnData(metaData, dbTableName), desiredFields)
        }
    }    
  
}


