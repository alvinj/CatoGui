package com.alvinalexander.cato.model

import java.sql.{Connection, DatabaseMetaData, DriverManager, ResultSet}
import scala.util.{Try,Success,Failure}
import scala.collection.mutable.{ArrayBuffer, Map}

object DatabaseUtils {

    val tableTypes = Array("TABLE", "VIEW", "ALIAS", "SYNONYM", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "SYSTEM TABLE")
  
    // TODO log the exception here, or use Try/Success/Failure
    def makeConnection(db: Database): Try[Connection] = {
        makeConnection(db.driver, db.url, db.username, db.password)
    }
  
    // TODO log the exception here, or use Try/Success/Failure
    def makeConnection(driver: String, url: String, username: String, password: String): Try[Connection] = {
        Try {
            Class.forName(driver)
            DriverManager.getConnection(url, username, password)
        }
    }
  
    /**
     * Return the DatabaseMetaData for the given connection.
     */
    def getTableMetaData(connection: Connection): Try[DatabaseMetaData] = {
        Try(connection.getMetaData)
    }

    // TODO wrap all of this in a "Try"
    def getTableNames(
            connection: Connection,
            metaData: DatabaseMetaData,
            databaseType: String,
            catalog: String,
            schema: String,
            hashOfTableNames: Map[String, String]): Seq[String] =
    {
        val tableNames = new ArrayBuffer[String]()
        var tables: ResultSet = null

        tables = getTablesForDbType(connection, metaData, databaseType, catalog, schema, tableTypes)
  
        // works for most db's
        if (databaseType.equals("NORMAL")) {
            tables = metaData.getTables(catalog, schema, "%", tableTypes)
        }
        else if (databaseType.equals("SOLID")) {
            // works for the "solid" db
            val query = "SELECT TABLE_SCHEMA, TABLE_TYPE, TABLE_NAME FROM TABLES"
            val stmt = connection.createStatement
            tables = stmt.executeQuery(query)
        }

        var i = 0
        var tableName = ""
    
        while (tables.next) {
            tableName = tables.getString(3)
            val tableType = tables.getString(2)
            
            // If we are using a table list then check against that first.
            // If no list then use everything.
            if ((hashOfTableNames == null) || (hashOfTableNames.get(tableName) != null)) {
                //System.err.println( "  adding " + tableName + " to hashOfTableNames ..." );
                tableNames += tableName
            }
        }
        tables.close
        tableNames
    }
  
  
    def getTablesForDbType(
            connection: Connection,
            metaData: DatabaseMetaData,
            databaseType: String,
            catalog: String,
            schema: String,
            tableTypes: Seq[String]): ResultSet = {

        var tables: ResultSet = null
    
        // works for most db's
        if (databaseType.equals("NORMAL")) {
            tables = metaData.getTables(catalog, schema, "%", tableTypes.toArray)
        }
        else if (databaseType.equals("SOLID")) {
            // works for the "solid" db
            val query = "SELECT TABLE_SCHEMA, TABLE_TYPE, TABLE_NAME FROM TABLES"
            val stmt = connection.createStatement
            tables = stmt.executeQuery(query)
        }
        tables
    }
  
  
    // returns a Java Vector
    // TODO wrap in a Try
    def getTableNames(connection: Connection, metaData: DatabaseMetaData) = {
    
        var tableNames = new ArrayBuffer[String]()
        var tables: ResultSet = null
    
        /** @todo -- catalog and schema always set to null! */
        //String catalog = new String();
        //String schema  = new String();
        var catalog: String = null
        var schema: String  = null
    
        val databaseType = "NORMAL"
        tables = getTablesAsAResultSet(connection, metaData, catalog, databaseType, schema, tableTypes)
    
        var tableName = ""
        //System.err.println( "about to start looping thru table names ..." );
        while (tables.next) {
            tableName = tables.getString(3)
            val tableType = tables.getString(2)
            tableNames += tableName
        }
        tables.close
        tableNames
    }
  
  
    def printDatabaseMetaDataInformation(metaData: DatabaseMetaData) {
        println("");
        println("Database & Driver Information:");
        println("------------------------------");
        println("Database version : " + metaData.getDatabaseProductVersion);
        println("Driver Name : " + metaData.getDriverName);
        println("Driver Version : " + metaData.getDriverMajorVersion + "."
                           + metaData.getDriverMinorVersion)
    
        // AJA: temporary: print database table types
        val rs = metaData.getTableTypes
        while (rs.next) {
            val tableType = rs.getString(1)
            System.err.println( "Valid table type: " + tableType )
        }
    }

    // TODO wrap in try
    def getTablesAsAResultSet(
            connection: Connection,
            metaData: DatabaseMetaData,
            catalog: String, 
            databaseType: String,
            schema: String,
            tableTypes: Seq[String]): ResultSet = {

        /** @todo should be able to determine this artificial "database type" from the metadata
         * without needing this parameter
         */
        if (databaseType.equals("NORMAL")) {
            metaData.getTables(catalog, schema, "%", tableTypes.toArray)
        } else {
            // works for the "solid" db
            val stmt = connection.createStatement
            stmt.executeQuery("SELECT TABLE_SCHEMA, TABLE_TYPE, TABLE_NAME FROM TABLES")
        }
    }
  
    def closeResultSetIgnoringExceptions(rs: ResultSet) {
        if (rs == null) return
        try { rs.close } catch { case t: Throwable => }
    }

}






