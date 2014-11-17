package com.alvinalexander.cato.model

import java.sql._
import java.util._

/** 
 *  A representation of a SQL database.
 */
class Database (driver: String, url: String, username: String, password: String) {

  var connection: Connection = null
  var _metaData: DatabaseMetaData = null

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



}


