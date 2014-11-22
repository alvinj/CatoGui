package com.alvinalexander.cato.model

import com.devdaily.dbgrinder.model.ColumnData
import java.sql._
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Set

object Table {

  /**
   * Gets the column data for a specified table.
   */
  // TODO wrap in Try
  def getColumnData (
      tableName: String,
      metaData: DatabaseMetaData,
      catalog: String,
      schema: String,
      typesAreStrings: Boolean): Seq[ColumnData] =
  {
      var colData = new ArrayBuffer[ColumnData]()
      var cd: ColumnData = null
      var colStringType = ""
      var colType = 0
      var colCols = 0

      val rs = metaData.getColumns(catalog, schema, tableName, "%")
      while (rs.next) {
          val colName = rs.getString(4)
          if (typesAreStrings) {
              colStringType = rs.getString(5)
              val isNullable = rs.getBoolean(11)
              //colCols = rs.getInt(7)  // size e.g. varchar(20)
              // mm.mysql problemette
              // fudge for now. Not that we use columns yet...
              colCols = 1
              cd = null
              try {
                  cd = new ColumnData(colName,Integer.parseInt(colStringType),colCols, TableUtils.isRequired(isNullable))
              } catch {
                  case e: NumberFormatException => 
              }
          }
          else
          {
              colType = rs.getInt(5)  // column type (XOPEN values)
              colCols = rs.getInt(7)  // size e.g. varchar(20)
              val isNullable = rs.getBoolean(11)
              cd = new ColumnData(colName, colType, colCols, TableUtils.isRequired(isNullable))
          }
          colData += cd
        }
        DatabaseUtils.closeResultSetIgnoringExceptions(rs)
        return colData
    }
  

  /**
    * Retrieves the Exported Keys defined for a particular table.
    * Currently puts the info in the class variable _foreignKeyData.
    */
  // TODO wrap in Try
  def getTableExportedKeys(tableName: String, 
                           metaData: DatabaseMetaData,
                           catalog: String,
                           schema: String): Seq[FKDefinition] =
  {
    var sFKName = ""
    var sFKColumn = ""
    var sPKColumn = ""
    var sFKTable = ""
    var sPKTable = ""
    var bMore = false
    var foreignKeyDefinition: FKDefinition = null

    var foreignKeyData = ArrayBuffer[FKDefinition]()

    var oldSequence = 0 // short
    var sequence = 1 // short

    var resultSet: ResultSet = null

    try {
      resultSet = metaData.getExportedKeys(catalog, schema, tableName) 
      bMore = resultSet.next
      while (bMore) {
        sFKName = resultSet.getString( 12)
        sFKTable = resultSet.getString(7)
        sPKTable = resultSet.getString(3)
        sequence = resultSet.getShort(9)
        oldSequence = 0
        foreignKeyDefinition = new FKDefinition(sPKTable, sFKTable, sFKName)
        while (bMore && (sequence > oldSequence)) {
          sFKColumn = resultSet.getString(8)
          sPKColumn = resultSet.getString(4)
          foreignKeyDefinition.addField( sFKColumn, sPKColumn )
          oldSequence = sequence
          bMore= resultSet.next
          if (bMore) sequence = resultSet.getShort(9)
        }
        foreignKeyData += foreignKeyDefinition
      }

      resultSet.close
      return foreignKeyData
    }
    catch {
        case e: SQLException => 
            // TODO
            foreignKeyData
    } finally {
      DatabaseUtils.closeResultSetIgnoringExceptions(resultSet)
    }
  }

  /**
   * Retrieves the Imported Keys defined for a particular table.
   */
  // TODO wrap in Try
  // TODO refactor; this is basically the same as the previous method
  def getTableImportedKeys(tableName: String,
                           metaData: DatabaseMetaData,
                           catalog: String,
                           schema: String ): Seq[FKDefinition] =
  {
    var sFKName = ""
    var sFKColumn = ""
    var sPKColumn = ""
    var sFKTable = ""
    var sPKTable = ""
    var foreignKeyDefinition: FKDefinition = null

    val foreignKeyData = new ArrayBuffer[FKDefinition]()

    var oldSequence = 0
    var sequence = 1

    val resultSet = metaData.getImportedKeys(catalog, schema, tableName) 
    var bMore = resultSet.next
    while (bMore) {
      sFKName =  resultSet.getString( 12)
      sFKTable= resultSet.getString(7)
      sPKTable= resultSet.getString(3)
      sequence = resultSet.getShort(9)
      oldSequence = 0
      foreignKeyDefinition = new FKDefinition(sPKTable, sFKTable, sFKName)
      while (bMore && (sequence > oldSequence)) {
        sFKColumn = resultSet.getString(8)
        sPKColumn = resultSet.getString(4)
        foreignKeyDefinition.addField( sFKColumn, sPKColumn )
        oldSequence = sequence
        bMore= resultSet.next
        if (bMore) sequence = resultSet.getShort(9)
      }
      foreignKeyData += foreignKeyDefinition
    }
    DatabaseUtils.closeResultSetIgnoringExceptions(resultSet)
    return foreignKeyData
  }


  /**
   * The contents of indexList are cleared and modified in this method.
   */
  // TODO wrap in Try
  // TODO why does this return Boolean? ANSWER: because it modifies indexList!!!
  // TODO i modified this method so it returns indexList, and does not take it as a param
  def getTableIndexes(tableName: String,
                      metaData: DatabaseMetaData,
                      catalog: String,
                      schema: String): ArrayBuffer[String] =
  {
    var indexList = ArrayBuffer[String]()
    var status = false
    // use a hashtable to temporarily store the indexes as well
    // so we can avoid duplicate values.
    val checkIndexes = Set[String]()
    var index = ""
    var indexType: Short = 0

    var rs: ResultSet = null
    try {
      // note: there were postgres issues here
      rs = metaData.getIndexInfo(catalog,schema,tableName,false,false)
      while (rs.next) {
        indexType = rs.getShort(7)
        index = rs.getString(9)
        if (indexType != DatabaseMetaData.tableIndexStatistic) {
          // ensure that it is not a duplicate value.
            // TODO fix this
          if (checkIndexes(index) == null) {
            indexList += index
            checkIndexes += index
            status = true  // yes we have at least one index
          }
        }
      }
      return indexList
    }
    catch {
      case e: SQLException => 
        e.printStackTrace
        // TODO refactor this method
        indexList
    }
    finally {
      DatabaseUtils.closeResultSetIgnoringExceptions(rs)
    }
  }

}


