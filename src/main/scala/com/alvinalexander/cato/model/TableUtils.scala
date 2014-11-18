package com.alvinalexander.cato.model

import java.sql.DatabaseMetaData
import com.devdaily.dbgrinder.model.ColumnData
import scala.collection.mutable.ArrayBuffer
import java.sql.ResultSet
import scala.util.{Try,Success,Failure}

object TableUtils {

  // TODO do this better
  def getFieldNamesCapitalized(tableColumns: Seq[ColumnData]): Seq[String] = {
      val fields = new ArrayBuffer[String]()
      for (column <- tableColumns) {
          fields += column.getColumnName.capitalize
      }
      fields
  }
  
  // Vector v = Table.getColumnData(currentlySelectedDatabaseTableName,Project.getDatabaseMetaData(),null,null,true);
  // TODO wrap in Try
  /**
   * This method returns a `Seq[ColumnData]`. You can use this to do whatever you want/need to
   * work with the fields in a database table.
   */
  def getColumnData (
      tableName: String,
      metaData: DatabaseMetaData,
      catalog: String,
      schema: String,
      typesAreStrings: Boolean): Try[Seq[ColumnData]] =
  {
      var colData = new ArrayBuffer[ColumnData]()
      var cd: ColumnData = null
      var colStringType = ""
      var colType = 0
      var numCols = 0

      Try {
          val rs = metaData.getColumns(catalog, schema, tableName, "%")
          while (rs.next) {
              val colName = rs.getString(4)
              if (typesAreStrings) {
                  colStringType = rs.getString(5)
                  numCols = getNumCols(rs)
                  cd = null
                  try {
                      cd = new ColumnData(colName, Integer.parseInt(colStringType), numCols)
                  } catch {
                      case e: NumberFormatException => // TODO
                  }
              }
              else
              {
                  colType = rs.getInt(5)  // column type (XOPEN values)
                  numCols = getNumCols(rs)
                  cd = new ColumnData(colName, colType, numCols)
              }
              colData += cd
            }
            DatabaseUtils.closeResultSetIgnoringExceptions(rs)
            colData
        }
    }
  
    /**
     * size e.g. varchar(20)
     */
    private def getNumCols(rs: ResultSet) = {
        try {
            rs.getInt(7)  
        } catch {
            case t: Throwable => 1
        }
    }

}





