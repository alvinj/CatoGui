//package com.alvinalexander.cato.model
//
//     -------------------------------------------------------------------
//     THIS OLD CLASS WAS USED AS "GLUE" BETWEEN V2 AND V1 OF THE OLD CODE
//     (BOTH VERY OLD). I DOUBT THAT I WANT TO CONTINUE WITH THIS.
//     -------------------------------------------------------------------
//
//import java.sql._
//
///**
// * This class is really just a container for a bunch of project-related properties.
// * It currently serves as the "glue" between the old Db2Wapp application and
// * the container method generator that I currently desire.
// */
//class Project {
//
//  var currentDatabaseTable = ""
//  var databaseMetaData: DatabaseMetaData = null
//  var Vector fieldsSelectedVector = new Vector()
//  var methodCreationMode = ""
//  var driver = ""
//  var url = ""
//  var username = ""
//  var password = ""
//  var Collection listOfTableNames
//  var Properties currentProperties
//
//  val CREATION_MODE_INSERT  = "INSERT"
//  val CREATION_MODE_SELECT  = "SELECT"
//  val CREATION_MODE_UPDATE  = "UPDATE"
//  val CREATION_MODE_DELETE  = "DELETE"
//  val CREATION_MODE_DEFAULT = CREATION_MODE_INSERT
//
//  def connectToDatabase {
//    val database = new Database(driver, url, username, password)
//    var dbMetaData = null
//    try {
//      val connection = DatabaseUtils.makeConnection(database)
//      dbMetaData = DatabaseUtils.getTableMetaData(connection)
//      setDatabaseMetaData(dbMetaData)
//      setListOfTableNames(database.getTableNames())
//    } catch {
//      case cnfe: ClassNotFoundException =>
//      case se: SQLException =>
//    }
//  }
//
//  def String getCurrentDatabaseTable()
//  {
//    return currentDatabaseTable
//  }
//  def setCurrentDatabaseTable(String newCurrentDatabaseTable)
//  {
//    currentDatabaseTable = newCurrentDatabaseTable
//  }
//
//  def setDatabaseMetaData(DatabaseMetaData newDatabaseMetaData)
//  {
//    databaseMetaData = newDatabaseMetaData
//  }
//  def DatabaseMetaData getDatabaseMetaData()
//  {
//    return databaseMetaData
//  }
//  def setFieldsSelectedVector(Vector newFieldsSelectedVector)
//  {
//    fieldsSelectedVector = newFieldsSelectedVector
//  }
//
//  def Vector getFieldsSelectedVector()
//  {
//    return fieldsSelectedVector
//  }
//
//  def setMethodCreationMode(String newMethodCreationMode)
//  {
//    methodCreationMode = newMethodCreationMode
//  }
//
//  def String getMethodCreationMode()
//  {
//    return methodCreationMode
//  }
//
//  def removeItemFromFieldsSelectedVector(String selectedField)
//  {
//    Iterator it = fieldsSelectedVector.iterator()
//    int i = 0
//    while ( it.hasNext() )
//    {
//      String currentFieldName = (String)it.next()
//      if ( currentFieldName.equals(selectedField) )
//      {
//        fieldsSelectedVector.removeElementAt(i)
//        return
//      }
//      i++
//    }
//  }
//
//  def addItemToFieldsSelectedVector(String selectedField)
//  {
//    Iterator it = fieldsSelectedVector.iterator()
//    while ( it.hasNext() )
//    {
//      String currentFieldName = (String)it.next()
//      if ( currentFieldName.equals(selectedField) )
//      {
//        return
//      }
//    }
//    fieldsSelectedVector.addElement(selectedField)
//  }
//  def setDriver(String newDriver)
//  {
//    driver = newDriver
//  }
//  def String getDriver()
//  {
//    return driver
//  }
//  def setUrl(String newUrl)
//  {
//    url = newUrl
//  }
//  def String getUrl()
//  {
//    return url
//  }
//  def setUsername(String newUsername)
//  {
//    username = newUsername
//  }
//  def String getUsername()
//  {
//    return username
//  }
//  def setPassword(String newPassword)
//  {
//    password = newPassword
//  }
//  def String getPassword()
//  {
//    return password
//  }
//  def setListOfTableNames(Collection newListOfTableNames)
//  {
//    listOfTableNames = newListOfTableNames
//  }
//  def Collection getListOfTableNames()
//  {
//    return listOfTableNames
//  }
//
//  def Properties getCurrentProperties()
//  {
//    return currentProperties
//  }
//
//  def setCurrentProperties(Properties currentProperties)
//  {
//    Project.currentProperties = currentProperties
//  }
//
//
//}
//
//
//
