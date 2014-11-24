package com.alvinalexander.cato
import com.alvinalexander.cato.model.Database
import com.alvinalexander.cato.model.DatabaseUtils
import com.alvinalexander.cato.model.TableUtils
import com.alvinalexander.cato.examples.Common

/**

+-------------------+
| Tables_in_finance |
+-------------------+
| play_evolutions   |
| research_links    |
| stocks   import com.alvinalexander.cato.examples.Common
         |
| transactions      |
| users             |
+-------------------+
5 rows in set (0.00 sec)

mysql> desc transactions;
+-----------+---------------+------+-----+-------------------+----------------+
| Field     | Type          | Null | Key | Default           | Extra          |
+-----------+---------------+------+-----+-------------------+----------------+
| id        | int(11)       | NO   | PRI | NULL              | auto_increment |
| uid       | int(11)       | NO   | MUL | NULL              |                |
| symbol    | varchar(10)   | NO   |     | NULL              |                |
| ttype     | char(1)       | NO   |     | NULL              |                |
| quantity  | int(11)       | NO   |     | NULL              |                |
| price     | decimal(10,2) | NO   |     | NULL              |                |
| date_time | timestamp     | NO   |     | CURRENT_TIMESTAMP |                |
| notes     | text          | YES  |     | NULL              |                |
+-----------+---------------+------+-----+-------------------+----------------+
8 rows in set (0.01 sec)

 */
object DbTest3 extends App {

    import Common._
    val database = new Database(driver, url, username, password)
    val connection = DatabaseUtils.makeConnection(database).get
    val metaData = DatabaseUtils.getTableMetaData(connection).get
    val tableNames = DatabaseUtils.getTableNames(connection, metaData)
    
    // Vector v = Table.getColumnData(currentlySelectedDatabaseTableName,Project.getDatabaseMetaData(),null,null,true);
    val columns = TableUtils.getColumnData("transactions", metaData, null, null, true).get
    for (cd <- columns) {
        println(s"ColumnName:  ${cd.getColumnName}")
        println(s"ColumnType:  ${cd.getJavaType}")
        println(s"ColumnWidth: ${cd.getNumColumns}")
        println("")
    }
    

    printHeader("Class Names")
    for (t <- tableNames) {
        println(TableUtils.convertTableNameToClassName(t))
    }
    

    printHeader("Object Names")
    for (t <- tableNames) {
        println(TableUtils.convertTableNameToObjectName(t))
    }
    

    printHeader("Prepared Statement Insert String")
    println(TableUtils.createPreparedStatementInsertString(columns))
    

    printHeader("Prepared Statement Update String")
    val fieldNamesPSUpdate = TableUtils.createPreparedStatementUpdateString(columns)
    println(fieldNamesPSUpdate)
    

    printHeader("Field Names")
    val fieldNames = TableUtils.getFieldNames(columns)
    fieldNames.foreach(println)
    

    printHeader("Field Names as Variable Names")
    val fieldNamesAsVariableNames = TableUtils.getCamelCaseFieldNames(columns)
    fieldNamesAsVariableNames.foreach(println)
    

    printHeader("Field Names as CSV")
    val fieldNamesAsCsv = TableUtils.getFieldNamesAsCsvString(columns)
    println(fieldNamesAsCsv)
    

    printHeader("Fields Required Status")
    val fieldsRequired = TableUtils.getFieldsRequiredStatus(columns)
    println(fieldsRequired)
    

    printHeader("Field Types (as Database types)")
    val dbFieldTypes = TableUtils.getDatabaseFieldTypes(columns)
    println(dbFieldTypes)
    
    // this doesn't work after i got rid of the `TableUtils.getJavaFieldTypes` method 
//    printHeader("Field Types (as Java types)")
//    val fieldTypes = TableUtils.getJavaFieldTypes(columns)
//    println(fieldTypes)
    

//    printHeader("Field Names as CSV Capitalized")
//    val fieldNamesCapsAsCsv = TableUtils.getFieldNamesCapitalizedAsCsvString(columns)
//    println(fieldNamesCapsAsCsv)
    

    printHeader("Field Names as CSV Camel-Cased")
    val fieldNamesCamelCasedAsCsv = TableUtils.getFieldNamesCamelCasedAsCsvString(columns)
    println(fieldNamesCamelCasedAsCsv)
    

    printHeader("Database Table Names")
    tableNames.foreach(println)


    def printHeader(s: String) {
        println(s"\n$s")
        println(genUnderlines(s))
    }

    def genUnderlines(s: String) = s.map(_ => "-").mkString
    
}








