package com.alvinalexander.cato

import java.sql.Connection
import java.util.Properties
import java.sql.SQLException
import scala.collection.mutable.ArrayBuffer
import java.sql.DatabaseMetaData
import com.alvinalexander.cato.model.Database
import com.alvinalexander.cato.model.DatabaseUtils
import com.alvinalexander.cato.model.TableUtils

/**

+-------------------+
| Tables_in_finance |
+-------------------+
| play_evolutions   |
| research_links    |
| stocks            |
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
    val columns = TableUtils.getColumnData("stocks", metaData, null, null, true).get
    for (cd <- columns) {
        println(s"ColumnName:  ${cd.getColumnName}")
        println(s"ColumnType:  ${cd.getJavaType}")
        println(s"ColumnWidth: ${cd.getNumColumns}")
        println("")
    }
    
    // field names capitalized
    println("\nField Names Capitalized")
    println("-----------------------")
    val fieldNamesCapitalized = TableUtils.getFieldNamesCapitalized(columns)
    fieldNamesCapitalized.foreach(println)
    
    println("\nDatabase Table Names")
    println("--------------------")
    tableNames.foreach(println)
    
}








