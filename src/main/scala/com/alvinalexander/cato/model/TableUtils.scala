package com.alvinalexander.cato.model

import java.sql.DatabaseMetaData
import com.devdaily.dbgrinder.model.ColumnData
import scala.collection.mutable.ArrayBuffer
import java.sql.ResultSet
import scala.util.{Try,Success,Failure}
import com.devdaily.dbgrinder.utility.StringUtils

object TableUtils {

    /**
     * Creates a Java PreparedStatement string from the given table columns.
     * The result looks like: "id=?, uid=?, symbol=?, quantity=?, price=?, date_time=?, notes=?"
     */
    def createPreparedStatementUpdateString(tableColumns: Seq[ColumnData]): String = {
        def lambda(fieldName: String) = s"$fieldName=?"
        loopOverTableFieldsAndReturnCsvString(tableColumns, lambda)
    }
  
    /**
     * Converts a list of field names to a series of `?` symbols.
     */
    def createPreparedStatementInsertString(tableColumns: Seq[ColumnData]) = {
        def lambda(fieldName: String) = "?"
        loopOverTableFieldsAndReturnCsvString(tableColumns, lambda)
    }

    /**
     * converts names like "stocks" to "Stock",
     * "users" to "User",
     * "research_links" to "ResearchLink", etc.
     */
    def convertTableNameToClassName(tableName: String) = {
        CatoUtils.singularize(StringUtils.convertUnderscoreNameToUpperCase(tableName).capitalize)
    }

    /**
     * converts names like "stocks" to "stock",
     * "users" to "user",
     * "research_links" to "researchLink", etc.
     */
    def convertTableNameToObjectName(tableName: String) = {
        CatoUtils.singularize(StringUtils.convertUnderscoreNameToUpperCase(tableName))
    }

    // TODO refactor
    def getFieldNames(tableColumns: Seq[ColumnData]): Seq[String] = {
        val fields = new ArrayBuffer[String]()
        for (column <- tableColumns) {
            fields += column.getColumnName
        }
        fields
    }
  
    // TODO refactor
    def getFieldNamesCapitalized(tableColumns: Seq[ColumnData]): Seq[String] = {
        val fields = new ArrayBuffer[String]()
        for (column <- tableColumns) {
            fields += column.getColumnName.capitalize
        }
        fields
    }
  
    def getFieldNamesAsCsvString(tableColumns: Seq[ColumnData]): String = {
        def noOp(fieldName: String) = fieldName
        loopOverTableFieldsAndReturnCsvString(tableColumns, noOp)
    }
  
    def getFieldNamesCapitalizedAsCsvString(tableColumns: Seq[ColumnData]): String = {
        def capField(fieldName: String) = fieldName.capitalize
        loopOverTableFieldsAndReturnCsvString(tableColumns, capField)
    }

    def getFieldNamesCamelCasedAsCsvString(tableColumns: Seq[ColumnData]): String = {
        def camelCaseField(fieldName: String) = StringUtils.convertUnderscoreNameToUpperCase(fieldName)
        loopOverTableFieldsAndReturnCsvString(tableColumns, camelCaseField)
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
                    numCols = getNumCols(rs)
                    cd = null
                    try {
                        cd = new ColumnData(colName, Integer.parseInt(colStringType), numCols)
                    } catch {
                        case e: NumberFormatException => // TODO
                    }
                }
                else
                {
                    colType = rs.getInt(5)  // column type (XOPEN values)
                    numCols = getNumCols(rs)
                    cd = new ColumnData(colName, colType, numCols)
                }
                colData += cd
            }
            DatabaseUtils.closeResultSetIgnoringExceptions(rs)
            colData
        }
    }
  
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





