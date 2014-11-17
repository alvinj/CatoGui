package com.devdaily.dbgrinder.model;

import java.sql.*;
import java.util.*;

public class Database
{

  private Connection connection;
  private String driver;
  private String url;
  private String username;
  private String password;
  private DatabaseMetaData _metaData;

  /**
   * Don't let users use this constructor.
   */
  private Database()
  {
  }

  public Database(String driver, String url, String username, String password)
  {
    this.driver = driver;
    this.url = url;
    this.username = username;
    this.password = password;
  }

  /**
    * Connects to the database a get the MetaData.
    */
  public void makeConnection()
  throws SQLException,ClassNotFoundException
  {
    Class.forName(this.driver);
    connection = DriverManager.getConnection(url, username, password);
  }

  /**
   * Returns a reference to our database connection.
   */
  public Connection getConnection()
  {
    return this.connection;
  }

  /**
   * Return the DatabaseMetaData for the given connection.
   */
  public DatabaseMetaData getTableMetaData()
  throws SQLException
  {
    _metaData = connection.getMetaData();
    //printDatabaseMetaDataInformation();
    return _metaData;
  }

  /**
    * Retrieves all the table data required.
    */
  public Vector getTableNames(String databaseType,
                              String catalog,
                              String schema,
                              Hashtable hashOfTableNames)
  throws SQLException
  {
    Vector tableNames = new Vector();
    ResultSet tables = null;

    String[] tableTypes = { "TABLE",
                            "VIEW",
                            "ALIAS",
                            "SYNONYM",
                            "GLOBAL TEMPORARY",
                            "LOCAL TEMPORARY",
                            "SYSTEM TABLE" };

    tables = getTablesForDbType(databaseType, catalog, schema, tableTypes);

    // works for most db's
    if ( databaseType.equals("NORMAL") )
    {
      tables = _metaData.getTables(catalog,schema,"%",tableTypes);
    }
    // works for the "solid" db
    else if ( databaseType.equals("SOLID") )
    {
      String query = "SELECT TABLE_SCHEMA,TABLE_TYPE,TABLE_NAME FROM TABLES";
      Statement stmt = connection.createStatement();
      tables = stmt.executeQuery(query);
    }

    int i;
    String tableName="";

    //System.err.println( "about to go thru table names ..." );
    while ( tables.next() )
    {
      tableName = tables.getString(3);
      String tableType = tables.getString(2);
      //System.err.println( " tableName: " + tableName );
      //System.err.println( " tableType: " + tableType + " \n" );

      // If we are using a table list
      // then check against that first.
      // If no list then we use everything.
      //
      if ( (hashOfTableNames == null) || (hashOfTableNames.get(tableName) != null) )
      {
        //System.err.println( "  adding " + tableName + " to hashOfTableNames ..." );
        tableNames.addElement(tableName);
      }
    }
    tables.close();
    return tableNames;
  }


  private ResultSet getTablesForDbType(String databaseType,
                                       String catalog,
                                       String schema,
                                       String[] tableTypes)
  throws SQLException
  {
    ResultSet tables = null;

    // works for most db's
    if ( databaseType.equals("NORMAL") )
    {
      tables = _metaData.getTables(catalog,schema,"%",tableTypes);
    }
    // works for the "solid" db
    else if ( databaseType.equals("SOLID") )
    {
      String query = "SELECT TABLE_SCHEMA,TABLE_TYPE,TABLE_NAME FROM TABLES";
      Statement stmt = connection.createStatement();
      tables = stmt.executeQuery(query);
    }
    return tables;
  }


  /**
    * Retrieves all the table data required.
    */
  public Vector getTableNames()
  throws SQLException
  {
    String[] tableTypes = { "TABLE",
                            "VIEW",
                            "ALIAS",
                            "SYNONYM",
                            "GLOBAL TEMPORARY",
                            "LOCAL TEMPORARY",
                            "SYSTEM TABLE" };

    Vector tableNames = new Vector();
    ResultSet tables = null;

    /** @todo -- catalog and schema always set to null! */
    //String catalog = new String();
    //String schema  = new String();
    String catalog = null;
    String schema  = null;

    String databaseType = "NORMAL";
    tables = getTablesAsAResultSet(catalog, databaseType, schema, tableTypes, tables);

    String tableName="";
    System.err.println( "about to start looping thru table names ..." );
    while ( tables.next() )
    {
      tableName = tables.getString(3);
      //System.err.println( "  tableName: " + tableName );
      String tableType = tables.getString(2);
      //System.err.println( "  tableType: " + tableType );
      tableNames.addElement(tableName);
    }
    tables.close();
    return tableNames;
  }


  private void printDatabaseMetaDataInformation()
  throws SQLException
  {
    System.out.println("");
    System.out.println("Database & Driver Information:");
    System.out.println("------------------------------");
    System.out.println("Database version : " + _metaData.getDatabaseProductVersion());
    System.out.println("Driver Name : " + _metaData.getDriverName());
    System.out.println("Driver Version : " + _metaData.getDriverMajorVersion() + "."
                       + _metaData.getDriverMinorVersion());

    // AJA: temporary: print database table types
    ResultSet rs = _metaData.getTableTypes();
    while ( rs.next() )
    {
      String type = rs.getString(1);
      System.err.println( "Valid table type: " + type );
    }
  }


  private ResultSet getTablesAsAResultSet(String catalog,
                                          String databaseType,
                                          String schema,
                                          String[] tableTypes,
                                          ResultSet tables)
  throws SQLException
  {
    /** @todo should be able to determine this artificial "database type" from the metadata
     * without needing this parameter
     */
    if ( databaseType.equals("NORMAL") )
    {
      //tables = _metaData.getTables(null,null,"%",tableTypes);
      tables = _metaData.getTables(catalog,schema,"%",tableTypes);
    }
    // works for the "solid" db
    else if ( databaseType.equals("SOLID") )
    {
      String query = "SELECT TABLE_SCHEMA,TABLE_TYPE,TABLE_NAME FROM TABLES";
      Statement stmt = connection.createStatement();
      tables = stmt.executeQuery(query);
    }
    return tables;
  }
}



