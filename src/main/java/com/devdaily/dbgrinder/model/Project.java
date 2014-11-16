package com.devdaily.dbgrinder.model;

/**
 * Copyright DevDaily Interactive, 1999 and beyond. All Rights Reserved.
 */

import java.sql.*;
import java.util.*;

/**
 * This class is really just a container for a bunch of project-related properties.
 * It currently serves as the "glue" between the old Db2Wapp application and
 * the container method generator that I currently desire.
 */
public class Project
{
  private static String currentDatabaseTable;
  private static DatabaseMetaData databaseMetaData;
  private static Vector fieldsSelectedVector = new Vector();
  private static String methodCreationMode;
  private static String driver;
  private static String url;
  private static String username;
  private static String password;
  private static Collection listOfTableNames;
  private static Properties currentProperties;

  public static final String CREATION_MODE_INSERT  = "INSERT";
  public static final String CREATION_MODE_SELECT  = "SELECT";
  public static final String CREATION_MODE_UPDATE  = "UPDATE";
  public static final String CREATION_MODE_DELETE  = "DELETE";
  public static final String CREATION_MODE_DEFAULT = CREATION_MODE_INSERT;

  public static void connectToDatabase()
  {
    Database database = new Database(driver,url,username,password);
    DatabaseMetaData dbmd = null;
    try
    {
      database.makeConnection();
      dbmd = database.getTableMetaData();
      setDatabaseMetaData(dbmd);
      setListOfTableNames(database.getTableNames());
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

  public static String getCurrentDatabaseTable()
  {
    return currentDatabaseTable;
  }
  public static void setCurrentDatabaseTable(String newCurrentDatabaseTable)
  {
    currentDatabaseTable = newCurrentDatabaseTable;
  }

  public static void setDatabaseMetaData(DatabaseMetaData newDatabaseMetaData)
  {
    databaseMetaData = newDatabaseMetaData;
  }
  public static DatabaseMetaData getDatabaseMetaData()
  {
    return databaseMetaData;
  }
  public static void setFieldsSelectedVector(Vector newFieldsSelectedVector)
  {
    fieldsSelectedVector = newFieldsSelectedVector;
  }

  public static Vector getFieldsSelectedVector()
  {
    return fieldsSelectedVector;
  }

  public static void setMethodCreationMode(String newMethodCreationMode)
  {
    methodCreationMode = newMethodCreationMode;
  }

  public static String getMethodCreationMode()
  {
    return methodCreationMode;
  }

  public static void removeItemFromFieldsSelectedVector(String selectedField)
  {
    Iterator it = fieldsSelectedVector.iterator();
    int i = 0;
    while ( it.hasNext() )
    {
      String currentFieldName = (String)it.next();
      if ( currentFieldName.equals(selectedField) )
      {
        fieldsSelectedVector.removeElementAt(i);
        return;
      }
      i++;
    }
  }

  public static void addItemToFieldsSelectedVector(String selectedField)
  {
    Iterator it = fieldsSelectedVector.iterator();
    while ( it.hasNext() )
    {
      String currentFieldName = (String)it.next();
      if ( currentFieldName.equals(selectedField) )
      {
        return;
      }
    }
    fieldsSelectedVector.addElement(selectedField);
  }
  public static void setDriver(String newDriver)
  {
    driver = newDriver;
  }
  public static String getDriver()
  {
    return driver;
  }
  public static void setUrl(String newUrl)
  {
    url = newUrl;
  }
  public static String getUrl()
  {
    return url;
  }
  public static void setUsername(String newUsername)
  {
    username = newUsername;
  }
  public static String getUsername()
  {
    return username;
  }
  public static void setPassword(String newPassword)
  {
    password = newPassword;
  }
  public static String getPassword()
  {
    return password;
  }
  public static void setListOfTableNames(Collection newListOfTableNames)
  {
    listOfTableNames = newListOfTableNames;
  }
  public static Collection getListOfTableNames()
  {
    return listOfTableNames;
  }

  public static Properties getCurrentProperties()
  {
    return currentProperties;
  }

  public static void setCurrentProperties(Properties currentProperties)
  {
    Project.currentProperties = currentProperties;
  }


}