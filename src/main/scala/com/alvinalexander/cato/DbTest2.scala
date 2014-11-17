package com.alvinalexander.cato

import java.sql.Connection
import com.devdaily.dbgrinder.model.Project
import java.util.Properties
import com.devdaily.dbgrinder.model.Table
import java.sql.SQLException
import com.devdaily.dbgrinder.model.ColumnData
import scala.collection.mutable.ArrayBuffer
import com.devdaily.dbgrinder.model.Database
import java.sql.DatabaseMetaData

object DbTest2 extends App {

    // /Applications/MAMP/tmp/mysql/mysql.sock
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:8889/finance"
    val username = "root"
    val password = "root"

    import scala.collection.JavaConversions._
    
    val db = new Database(driver, url, username, password)
    db.makeConnection
    val dbMetaData = db.getTableMetaData
    val tableNames = db.getTableNames
    
    println("\nDatabase Meta Data")
    println("------------------")
    printDatabaseMetaDataInformation(dbMetaData)

    println("\nDatabase Table Names")
    println("--------------------")
    tableNames.foreach(println)
    
    def printDatabaseMetaDataInformation(metaData: DatabaseMetaData) {
        println("Database version : " + metaData.getDatabaseProductVersion)
        println("Driver Name : " + metaData.getDriverName)
        println("Driver Version : " + metaData.getDriverMajorVersion + "." + metaData.getDriverMinorVersion)
    }

}




