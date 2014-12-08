package com.alvinalexander.cato.model

import java.sql.DatabaseMetaData
import com.devdaily.dbgrinder.model.ColumnData
import scala.collection.mutable.ArrayBuffer
import java.sql.ResultSet
import scala.util.{Try,Success,Failure}
import com.devdaily.dbgrinder.utility.StringUtils
import com.alvinalexander.inflector.Inflections

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
     * Converts field names like `employee_salary` to "Employee Salary", so these can be used as
     * labels for text fields and text areas on forms, column headings, etc.
     */
    def getFieldNamesAsLabels(tableColumns: Seq[ColumnData], desiredFields: Seq[String]): Seq[String] = {
        // TODO this import is a kludge until i merge the old and new StringUtils
        import com.alvinalexander.cato.utils.{StringUtils => SU}
        val ltdTableColumns = getSubsetOfColumnData(tableColumns, desiredFields)
        ltdTableColumns.map((cd: ColumnData) => SU.capitalizeAllWordsInString(Inflections.humanize(cd.getColumnName)))
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
//    def getFieldTypes(tableColumns: Seq[ColumnData]): Seq[String] = {
//        tableColumns.map((cd: ColumnData) => cd.getJavaType)
//    }
  
    /**
     * use this method to get the field types based on the user-defined data type mappings
     * we are given. 
     */
    @Deprecated
    def getFieldTypes(tableColumns: Seq[ColumnData],
                      desiredFields: Seq[String],
                      dataTypesMap: Map[String, String]): Seq[String] = 
    {
        val ltdTableColumns = getSubsetOfColumnData(tableColumns, desiredFields)
        //ltdTableColumns.map((cd: ColumnData) => dataTypesMap(cd.getDatabaseColumnType))
        ltdTableColumns.map((cd: ColumnData) => {
            System.err.println(s"COLTYPE: ${cd.getColumnName} = ${cd.getDatabaseColumnType}")
            dataTypesMap(cd.getDatabaseColumnType)
        })
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
    
    /**
     * returns the table field names as a csv string.
     */
    def getFieldNamesAsCsvStringWithoutIdField(tableColumns: Seq[ColumnData],
                                               desiredFields: Option[Seq[String]]): String = {
        def noOp(fieldName: String) = fieldName
        def exclude(cd: ColumnData) = { cd.getColumnName != "id" }
        desiredFields match {
            case None =>
                loopOverTableFieldsAndReturnCsvString(tableColumns, noOp, exclude)
            case Some(fields) =>
                val ltdColumnData = getSubsetOfColumnData(tableColumns, fields)
                loopOverTableFieldsAndReturnCsvString(ltdColumnData, noOp, exclude)
        }
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
  
    private def returnTrue(cd: ColumnData) = true
    private def loopOverTableFieldsAndReturnCsvString(tableColumns: Seq[ColumnData], 
                                                      f:(String) => String,
                                                      filterFunction:(ColumnData) => Boolean = returnTrue): String = {
        val sb = new StringBuilder
        var count = 0
        val filteredTableColumns = tableColumns.filter(filterFunction)
        for (column <- filteredTableColumns) {
            sb.append(f(column.getColumnName))
            if (count < filteredTableColumns.length-1) sb.append(", ")
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
    
    
    /**
     * This method returns a `Seq[ColumnData]`. You can use this to do whatever you want/need to
     * work with the fields in a database table.
     * 
     * from http://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData.html
     * ----------------------------------------------------------------------------
     * 
     * Each column description has the following columns:
     * 
     * (1)  TABLE_CAT String => table catalog (may be null)
     * (2)  TABLE_SCHEM String => table schema (may be null)
     * (3)  TABLE_NAME String => table name
     * (4)  COLUMN_NAME String => column name
     * (5)  DATA_TYPE int => SQL type from java.sql.Types
     * (6)  TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified
     * (7)  COLUMN_SIZE int => column size.
     * (8)  BUFFER_LENGTH  is not used.
     * (9)  DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable.
     * (10) NUM_PREC_RADIX int => Radix (typically either 10 or 2)
     * (11) NULLABLE int => is NULL allowed.
     *      columnNoNulls - might not allow NULL values
     *      columnNullable - definitely allows NULL values
     *      columnNullableUnknown - nullability unknown
     * (12) REMARKS String => comment describing column (may be null)
     * (13) COLUMN_DEF String => default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null)
     * (14) SQL_DATA_TYPE int => unused
     * (15) SQL_DATETIME_SUB int => unused
     * (16) CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column
     * (17) ORDINAL_POSITION int => index of column in table (starting at 1)
     * (18) IS_NULLABLE String => ISO rules are used to determine the nullability for a column.
     *      YES --- if the column can include NULLs
     *      NO --- if the column cannot include NULLs
     *      empty string --- if the nullability for the column is unknown
     * (19) SCOPE_CATALOG String => catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF)
     * (20) SCOPE_SCHEMA String => schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF)
     * (21) SCOPE_TABLE String => table name that this the scope of a reference attribute (null if the DATA_TYPE isn't REF)
     * (22) SOURCE_DATA_TYPE short => source type of a distinct type or user-generated Ref type, SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF)
     * (23) IS_AUTOINCREMENT String => Indicates whether this column is auto incremented
     *      YES --- if the column is auto incremented
     *      NO --- if the column is not auto incremented
     *      empty string --- if it cannot be determined whether the column is auto incremented
     * (24) IS_GENERATEDCOLUMN String => Indicates whether this is a generated column
     *      YES --- if this a generated column
     *      NO --- if this not a generated column
     *      empty string --- if it cannot be determined whether this is a generated column
     * 
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
            //                getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern)
            val rs = metaData.getColumns(catalog, schema, tableName, "%")
            while (rs.next) {
                val colName = rs.getString(4)
                if (typesAreStrings) {
                    colStringType = rs.getString(5)  // is "4" for an int
                    val dataSourceDependentTypeName = rs.getString(6)
                    if (colStringType == "4" && intReallyNeedsToBeLong(dataSourceDependentTypeName)) {
                        colStringType = "3"  // 3 is "long"
                    }

//                    System.err.println(s"\n$colName")
//                    System.err.println(s"~~~~~~~~~~~~~~~")
//                    System.err.println(s"$colName, colStringType = $colStringType")
//                    val dataSourceDependentTypeName = rs.getString(6)
//                    val columnSize = rs.getInt(7)
//                    val sqlDataType = rs.getInt(14)  //unused
//                    val isAutoIncrementField = rs.getString(23)
//                    System.err.println(s"typeName             = $dataSourceDependentTypeName")
//                    System.err.println(s"columnSize           = $columnSize")
//                    System.err.println(s"sqlDataType          = $sqlDataType")
//                    System.err.println(s"isAutoIncrementField = $isAutoIncrementField")

                    
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
    
    /**
     * TODO this is a bit of a kludge
     */
    def intReallyNeedsToBeLong(dataSourceDependentTypeName: String) = dataSourceDependentTypeName match {
        case "INT UNSIGNED" => true
        case "MEDIUMINT" => true
        case "MEDIUMINT UNSIGNED" => true
        case _ => false
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





