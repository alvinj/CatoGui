package com.devdaily.dbgrinder.model;

import java.sql.*;
import java.util.*;
import com.devdaily.utils.Debug;

public class Table
{

/*
  public Table()
  {
  }
*/

  /**
    * Gets the column data for a specified table.
    */
  public static Vector getColumnData(String tableName,
                                     DatabaseMetaData metaData,
                                     String catalog,
                                     String schema,
                                     boolean typesAreStrings)
  throws SQLException
  {
    Vector colData = new Vector();
    ColumnData cd;
    String colStringType;
    int colType;
    int colCols;

    ResultSet rs = metaData.getColumns(catalog,schema,tableName,"%");
    while ( rs.next() )
    {
      String colName = rs.getString(4);
      if ( typesAreStrings )
      {
        colStringType = rs.getString(5);
        //colCols = rs.getInt(7);  // size e.g. varchar(20)
        // mm.mysql problemette
        // fudge for now. Not that we use columns yet...
        colCols = 1;
        cd = null;
        try
        {
          cd = new ColumnData(colName,Integer.parseInt(colStringType),colCols);
        }
        catch (NumberFormatException nfe)
        {
          nfe.printStackTrace(System.err);
        }
      }
      else
      {
        colType = rs.getInt(5);  // column type (XOPEN values)
        colCols = rs.getInt(7);  // size e.g. varchar(20)
        cd = new ColumnData(colName,colType,colCols);
      }
      colData.addElement(cd);
    }
    rs.close();

    return colData;
  }


  /**
    * Retrieves the Exported Keys defined for a particular table.
    * Currently puts the info in the class variable _foreignKeyData.
    */
  static Vector getTableExportedKeys( final String tableName,
                                      final DatabaseMetaData metaData,
                                      final String catalog,
                                      final String schema )
  throws SQLException
  {
    String sFKName;
    String sFKColumn;
    String sPKColumn;
    String sFKTable;
    String sPKTable;
    short sequence;
    short oldSequence;
    boolean bMore;
    FKDefinition foreignKeyDefinition;

    Vector foreignKeyData = new Vector();

    oldSequence =0;
    sequence=1;
    ResultSet resultSet = null;

    try
    {
      resultSet = metaData.getExportedKeys( catalog, schema, tableName ) ;
      bMore= resultSet.next();

      while ( bMore )
      {
        sFKName =  resultSet.getString( 12);
        sFKTable= resultSet.getString(7);
        sPKTable= resultSet.getString(3);
        sequence = resultSet.getShort(9);
        oldSequence =0;
        foreignKeyDefinition = new FKDefinition( sPKTable, sFKTable, sFKName );
        while ( bMore && ( sequence > oldSequence ))
        {
          sFKColumn = resultSet.getString(8);
          sPKColumn = resultSet.getString(4);
          foreignKeyDefinition.addField( sFKColumn, sPKColumn );

          oldSequence = sequence;
          bMore= resultSet.next();
          if (bMore) sequence = resultSet.getShort(9);
        }
        foreignKeyData.addElement( foreignKeyDefinition );
      }

      resultSet.close();
      return foreignKeyData;
    }
    catch (SQLException se)
    {
      Debug.println("In getTableExportedKeys, threw se: " + se.getMessage());
      throw se;
    }
    finally
    {
      if ( resultSet != null )
      {
        try
        {
          resultSet.close();
        }
        catch (Exception e)
        {
        }
      }
    }
  }

  /**
   * Retrieves the Imported Keys defined for a particular table.
   */
  static Vector getTableImportedKeys( final String tableName,
                                      final DatabaseMetaData metaData,
                                      final String catalog,
                                      final String schema )
  throws SQLException
  {
    String sFKName;
    String sFKColumn;
    String sPKColumn;
    String sFKTable;
    String sPKTable;
    short sequence;
    short oldSequence;
    boolean bMore;
    FKDefinition foreignKeyDefinition;

    Vector foreignKeyData = new Vector();

    oldSequence =0;
    sequence=1;
    ResultSet resultSet = metaData.getImportedKeys( catalog, schema, tableName ) ;
    bMore= resultSet.next();
    while ( bMore )
    {
      sFKName =  resultSet.getString( 12);
      sFKTable= resultSet.getString(7);
      sPKTable= resultSet.getString(3);
      sequence = resultSet.getShort(9);
      oldSequence =0;
      foreignKeyDefinition = new FKDefinition( sPKTable, sFKTable, sFKName );
      while ( bMore && ( sequence > oldSequence ))
      {
        sFKColumn = resultSet.getString(8);
        sPKColumn = resultSet.getString(4);
        foreignKeyDefinition.addField( sFKColumn, sPKColumn );

        oldSequence = sequence;
        bMore= resultSet.next();
        if (bMore) sequence = resultSet.getShort(9);
      }
      foreignKeyData.addElement( foreignKeyDefinition );
    }
    resultSet.close();
    return foreignKeyData;
  }


  /**
   * The contents of indexList are cleared and modified in this method.
   */
  public static boolean getTableIndexes(final String tableName,
                                        final DatabaseMetaData metaData,
                                        final String catalog,
                                        final String schema,
                                        Vector indexList)
  throws SQLException
  {
    boolean status = false;
    // use a hashtable to temporarily store the indexes as well
    // so we can avoid duplicate values.
    Hashtable checkIndexes = new Hashtable();
    String index = null;
    short indexType = 0;

    // get rid of anything else in the list
    indexList.clear();

    ResultSet r = null;
    try
    {
      /** @todo -- POSTGRES IS BLOWING UP HERE ON UPDATE's */
      // NOTE -- latest postgres dev driver from 2002-02-09 seems to work okay here!!!
      r = metaData.getIndexInfo(catalog,schema,tableName,false,false);
      while ( r.next() )
      {
        indexType = r.getShort(7);
        index = r.getString(9);
        if (indexType != DatabaseMetaData.tableIndexStatistic)
        {
          // ensure that it is not a duplicate value.
          if ( checkIndexes.get(index) == null )
          {
            indexList.addElement(index);
            checkIndexes.put(index,index);
            status = true;  // yes we have at least one index
          }
        }
      }
      return status;
    }
    catch (SQLException se)
    {
      Debug.println( "blew up in Table.getTableIndexes. Message follows:" );
      Debug.println( se.getMessage() );
      throw se;
    }
    finally
    {
      r.close();
    }
  }

}


