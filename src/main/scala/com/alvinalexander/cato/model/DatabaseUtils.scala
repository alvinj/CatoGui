package com.alvinalexander.cato.model

import java.sql._
import java.util._
import scala.util.{Try,Success,Failure}

object DatabaseUtils {

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
            hashOfTableNames: Hashtable)
    {
        val tableNames = new Vector
        var tables: ResultSet = null

        val tableTypes = Array("TABLE", "VIEW", "ALIAS", "SYNONYM", "GLOBAL TEMPORARY",
                              "LOCAL TEMPORARY", "SYSTEM TABLE")
  
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
          //System.err.println( " tableName: " + tableName );
          //System.err.println( " tableType: " + tableType + " \n" );
    
          // If we are using a table list
          // then check against that first.
          // If no list then we use everything.
          //
          if ((hashOfTableNames == null) || (hashOfTableNames.get(tableName) != null)) {
            //System.err.println( "  adding " + tableName + " to hashOfTableNames ..." );
            tableNames.addElement(tableName)
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
            tables = metaData.getTables(catalog, schema, "%", tableTypes)
        }
        else if (databaseType.equals("SOLID")) {
            // works for the "solid" db
            val query = "SELECT TABLE_SCHEMA,TABLE_TYPE,TABLE_NAME FROM TABLES"
            val stmt = connection.createStatement
            tables = stmt.executeQuery(query)
        }
        tables
    }
  
  
    // returns a Java Vector
    // TODO wrap in a Try
    def getTableNames = {
        
        val tableTypes = Array("TABLE", "VIEW", "ALIAS", "SYNONYM",
                                "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "SYSTEM TABLE")
    
        var tableNames = new Vector
        var tables: ResultSet = null
    
        /** @todo -- catalog and schema always set to null! */
        //String catalog = new String();
        //String schema  = new String();
        var catalog: String = null
        var schema: String  = null
    
        val databaseType = "NORMAL"
        tables = getTablesAsAResultSet(catalog, databaseType, schema, tableTypes, tables)
    
        var tableName = ""
        System.err.println( "about to start looping thru table names ..." );
        while (tables.next) {
            tableName = tables.getString(3)
            //System.err.println( "  tableName: " + tableName );
            val tableType = tables.getString(2)
            //System.err.println( "  tableType: " + tableType );
            tableNames.addElement(tableName)
        }
        tables.close
        tableNames
    }
  
  
    def printDatabaseMetaDataInformation(metaData: DatabaseMetaData) {
        System.out.println("");
        System.out.println("Database & Driver Information:");
        System.out.println("------------------------------");
        System.out.println("Database version : " + metaData.getDatabaseProductVersion);
        System.out.println("Driver Name : " + metaData.getDriverName);
        System.out.println("Driver Version : " + metaData.getDriverMajorVersion + "."
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
            tableTypes: Seq[String],
            tables: ResultSet): ResultSet = {

        /** @todo should be able to determine this artificial "database type" from the metadata
         * without needing this parameter
         */
        if (databaseType.equals("NORMAL")) {
            tables = metaData.getTables(catalog, schema, "%", tableTypes)
            tables
        }
        else if (databaseType.equals("SOLID")) {
            // works for the "solid" db
            val query = "SELECT TABLE_SCHEMA, TABLE_TYPE, TABLE_NAME FROM TABLES"
            val stmt = connection.createStatement()
            val tables = stmt.executeQuery(query)
            tables
        }
    }
  
}






