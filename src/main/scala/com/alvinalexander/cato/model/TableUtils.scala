package com.alvinalexander.cato.model

import java.sql.DatabaseMetaData
import com.devdaily.dbgrinder.model.ColumnData
import scala.collection.mutable.ArrayBuffer
import java.sql.ResultSet
import scala.util.{Try,Success,Failure}
import com.devdaily.dbgrinder.utility.StringUtils

object TableUtils {

    /**
     * converts names like "stocks" to "Stock",
     * "users" to "User",
     * "research_links" to "ResearchLink", etc.
     */
    def convertTableNameToClassName(tableName: String): String = {
        CatoUtils.singularize(StringUtils.convertUnderscoreNameToUpperCase(tableName).capitalize)
    }

    /**
     * converts names like "stocks" to "stock",
     * "users" to "user",
     * "research_links" to "researchLink", etc.
     */
    def convertTableNameToObjectName(tableName: String): String = {
        CatoUtils.singularize(StringUtils.convertUnderscoreNameToUpperCase(tableName))
    }


    /**
     * Creates a Java PreparedStatement string from the given table columns.
     * The result looks like: "id=?, uid=?, symbol=?, quantity=?, price=?, date_time=?, notes=?"
     */
    def createPreparedStatementUpdateString(tableColumns: Seq[ColumnData]): String = {
        def lambda(fieldName: String) = s"$fieldName=?"
        loopOverTableFieldsAndReturnCsvString(tableColumns, lambda)
    }
  
    def createPreparedStatementUpdateString(tableColumns: Seq[ColumnData], desiredFields: Seq[String]): String = {
        def lambda(fieldName: String) = s"$fieldName=?"
        val ltdTableColumns = getSubsetOfColumnData(tableColumns, desiredFields)
        loopOverTableFieldsAndReturnCsvString(ltdTableColumns, lambda)
    }
  

    /**
     * Converts a list of field names to a series of `?` symbols.
     */
    def createPreparedStatementInsertString(tableColumns: Seq[ColumnData]) = {
        def lambda(fieldName: String) = "?"
        loopOverTableFieldsAndReturnCsvString(tableColumns, lambda)
    }

    def createPreparedStatementInsertString(tableColumns: Seq[ColumnData], desiredFields: Seq[String]) = {
        def lambda(fieldName: String) = "?"
        val ltdTableColumns = getSubsetOfColumnData(tableColumns, desiredFields)
        loopOverTableFieldsAndReturnCsvString(ltdTableColumns, lambda)
    }


    /**
     * get the field names from the Seq[ColumnData]
     */
    def getFieldNames(tableColumns: Seq[ColumnData]): Seq[String] = {
        tableColumns.map((cd: ColumnData) => cd.getColumnName)
    }
  
    def getFieldNames(tableColumns: Seq[ColumnData], desiredFields: Seq[String]): Seq[String] = {
        val ltdTableColumns = getSubsetOfColumnData(tableColumns, desiredFields)
        ltdTableColumns.map((cd: ColumnData) => cd.getColumnName)
    }
  

    /**
     * converts field names from `email_address` to `emailAddress`
     */
    def getCamelCaseFieldNames(tableColumns: Seq[ColumnData]): Seq[String] = {
        def lambda(cd: ColumnData) = CatoUtils.convertUnderscoreNameToCamelCase(cd.getColumnName)
        tableColumns.map(lambda)
    }

    def getCamelCaseFieldNames(tableColumns: Seq[ColumnData], desiredFields: Seq[String]): Seq[String] = {
        def lambda(cd: ColumnData) = CatoUtils.convertUnderscoreNameToCamelCase(cd.getColumnName)
        val ltdTableColumns = getSubsetOfColumnData(tableColumns, desiredFields)
        ltdTableColumns.map(lambda)
    }


    /**
     * returns a list of the database fields types, like (int, int, varchar, text, datetime ...)
     */
    def getDatabaseFieldTypes(tableColumns: Seq[ColumnData]): Seq[String] = {
        tableColumns.map((cd: ColumnData) => cd.getDatabaseColumnType)
    }
  
    def getDatabaseFieldTypes(tableColumns: Seq[ColumnData], desiredFields: Seq[String]): Seq[String] = {
        val ltdTableColumns = getSubsetOfColumnData(tableColumns, desiredFields)
        ltdTableColumns.map((cd: ColumnData) => cd.getDatabaseColumnType)
    }
  

    /**
     * returns the field types as java field types, like (int, boolean, String, Timestamp ...)
     */
    def getJavaFieldTypes(tableColumns: Seq[ColumnData]): Seq[String] = {
        tableColumns.map((cd: ColumnData) => cd.getJavaType)
    }
  
    def getJavaFieldTypes(tableColumns: Seq[ColumnData], desiredFields: Seq[String]): Seq[String] = {
        val ltdTableColumns = getSubsetOfColumnData(tableColumns, desiredFields)
        ltdTableColumns.map((cd: ColumnData) => cd.getJavaType)
    }

    /**
     * returns a list of whether each field is required (or not)
     */
    def getFieldsRequiredStatus(tableColumns: Seq[ColumnData]): Seq[Boolean] = {
        tableColumns.map((cd: ColumnData) => cd.isRequired)
    }
  
    def getFieldsRequiredStatus(tableColumns: Seq[ColumnData], desiredFields: Seq[String]): Seq[Boolean] = {
        val ltdTableColumns = getSubsetOfColumnData(tableColumns, desiredFields)
        ltdTableColumns.map((cd: ColumnData) => cd.isRequired)
    }
  
    /**
     * returns the table field names as a csv string.
     */
    def getFieldNamesAsCsvString(tableColumns: Seq[ColumnData]): String = {
        def noOp(fieldName: String) = fieldName
        loopOverTableFieldsAndReturnCsvString(tableColumns, noOp)
    }
  
    /**
     * returns only the fields you supply, in the order given by the database.
     */
    def getFieldNamesAsCsvString(tableColumns: Seq[ColumnData], desiredFields: Seq[String]): String = {
        val ltdColumnData = getSubsetOfColumnData(tableColumns, desiredFields)
        def noOp(fieldName: String) = fieldName
        loopOverTableFieldsAndReturnCsvString(ltdColumnData, noOp)
    }
    
    def getFieldNamesCamelCasedAsCsvString(tableColumns: Seq[ColumnData]): String = {
        def camelCaseField(fieldName: String) = StringUtils.convertUnderscoreNameToUpperCase(fieldName)
        loopOverTableFieldsAndReturnCsvString(tableColumns, camelCaseField)
    }

    def getFieldNamesCamelCasedAsCsvString(tableColumns: Seq[ColumnData], desiredFields: Seq[String]): String = {
        val ltdColumnData = getSubsetOfColumnData(tableColumns, desiredFields)
        def camelCaseField(fieldName: String) = StringUtils.convertUnderscoreNameToUpperCase(fieldName)
        loopOverTableFieldsAndReturnCsvString(ltdColumnData, camelCaseField)
    }

    /**
     * returns a `Seq[ColumnData]` that contain only the `desiredFields`, that is,
     * where `tableColumn.getColumnName` is in `desiredFields`.
     */
    private def getSubsetOfColumnData(tableColumns: Seq[ColumnData], desiredFields: Seq[String]): Seq[ColumnData] = {
        tableColumns.filter(col => desiredFields.contains(col.getColumnName))
    }
  
    private def loopOverTableFieldsAndReturnCsvString(tableColumns: Seq[ColumnData], f:(String) => String): String = {
        val sb = new StringBuilder
        var count = 0
        for (column <- tableColumns) {
            sb.append(f(column.getColumnName))
            if (count < tableColumns.length-1) sb.append(", ")
            count += 1
        }
        sb.toString
    }

    
    /**
     * 
     * See `getColumns` on the DatabaseMetaData page:
     * https://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData.html
     * 
     */
    
    
    // Vector v = Table.getColumnData(currentlySelectedDatabaseTableName,Project.getDatabaseMetaData(),null,null,true);
    // TODO wrap in Try
    /**
     * This method returns a `Seq[ColumnData]`. You can use this to do whatever you want/need to
     * work with the fields in a database table.
     */
    def getColumnData (
        tableName: String,
        metaData: DatabaseMetaData,
        catalog: String,
        schema: String,
        typesAreStrings: Boolean): Try[Seq[ColumnData]] =
    {
        var colData = new ArrayBuffer[ColumnData]()
        var cd: ColumnData = null
        var colStringType = ""
        var colType = 0
        var numCols = 0

        Try {
            val rs = metaData.getColumns(catalog, schema, tableName, "%")
            while (rs.next) {
                val colName = rs.getString(4)
                if (typesAreStrings) {
                    colStringType = rs.getString(5)
                    val isNullable = rs.getBoolean(11)
                    numCols = getNumCols(rs)
                    cd = null
                    try {
                        cd = new ColumnData(colName, Integer.parseInt(colStringType), numCols, isRequired(isNullable))
                    } catch {
                        case e: NumberFormatException => // TODO
                    }
                }
                else
                {
                    colType = rs.getInt(5)  // column type (XOPEN values)
                    val isNullable = rs.getBoolean(11)
                    numCols = getNumCols(rs)
                    cd = new ColumnData(colName, colType, numCols, isRequired(isNullable))
                }
                colData += cd
            }
            DatabaseUtils.closeResultSetIgnoringExceptions(rs)
            colData
        }
    }
    
    // isRequired is the opposite of isNullable
    def isRequired(isNullable: Boolean) = if (isNullable) false else true
  
    /**
     * size e.g. varchar(20)
     */
    private def getNumCols(rs: ResultSet) = {
        try {
            rs.getInt(7)  
        } catch {
            case t: Throwable => 1
        }
    }

}





