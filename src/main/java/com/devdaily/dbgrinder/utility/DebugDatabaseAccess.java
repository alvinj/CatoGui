package com.devdaily.dbgrinder.utility;

import com.devdaily.dbgrinder.model.Database;
import java.sql.*;
import java.util.*;

public class DebugDatabaseAccess
{
  public static void main(String[] args)
  {
    String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
    String url = "jdbc:microsoft:sqlserver://hji-tap:1433;SelectMethod=cursor/toolTAP";
    String username = "tapUser";
    String password = "t@p";

    Database database = new Database(driver,url,username,password);
    DatabaseMetaData dbmd = null;
    try
    {
      System.err.println("making db connection ...");
      database.makeConnection();
      System.err.println("getting metadata ...");
      dbmd = database.getTableMetaData();
      System.err.println("getting table names ...");
      Vector v = database.getTableNames();
      Iterator it = v.iterator();
      while ( it.hasNext() )
      {
        String s = (String)it.next();
        System.err.println( "table name: " + s );
      }
    }
    catch (ClassNotFoundException cnfe)
    {
      System.err.println( "ClassNotFoundException occurred trying to connect to database. Program halted." );
      System.err.println( cnfe.getMessage() );
      System.exit(1);
    }
    catch (SQLException se)
    {
      System.err.println( "SQLException occurred: " + se.getMessage() );
    }
  }
}