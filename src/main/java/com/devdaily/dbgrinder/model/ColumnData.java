package com.devdaily.dbgrinder.model;

import com.devdaily.utils.Debug;

/**
  * Container for details about a table column.
  *
  * @author: J.A.Carter
  * Release 1.1
  *
  * <c) Joe Carter 1998
  * Released under GPL. See LICENSE for full details
  */

public class ColumnData
{
  // NB "TEXT" is my own add on!
  static String[] sqlTypes = { "CHAR","TINYINT","BIGINT","INT",
                               "SMALLINT","FLOAT","REAL","DOUBLE",
                               "NUMERIC","DECIMAL","DATE","VARCHAR",
                               "LONGVARCHAR","TIMESTAMP","TIME","BIT",
                               "BINARY","VARBINARY","LONGVARBINARY","NULL",
                               "OTHER","TEXT" };

  int type;
  int columns;
  String name;
  String columnType;
  boolean isRequired;

/**
  * Standard constructor.
  * Requires name, XOPEN type, number of columns.
  * The last is only used for CHAR(x) and DATE fields.
  */
  public ColumnData (String name, int origType, int columns, boolean isRequired)
  {
    this.name = name;
    this.type = origType;
    this.columns = columns;
    this.isRequired = isRequired;
    
    switch (type)
    {
      case -1:
        // Sybase
        // TEXT
        type = 22;
        break;

      case -2:
        //MySQL
        //TINYBLOB - use VARBINARY for now. Cobble alert!
        type = 18;
        break;

      case -3:
        // MySQL
        // medium blob / blob to LONGVARBINARY
        type = 19;

// Arse -> Some serious type conflicts here
// This issue needs sorting out. Need a heavy duty
// dig down the JDBC specs. Sigh...

// OOOPS - conflict. MySQL gets priority for now.
        // PostGres
        // abstime / timestamp
//        type = 14;  // use timestamp

// OOOPS - conflict. Postgres gets priority for now.
        //Oracle
        //RAW which maps to VARBINARY
//        type = 18;
        break;


      case -4:
        //Oracle
        //LONG RAW which maps to LONGVARBINARY
        type = 19;
        break;

      case -5:
        // MySQL
        // bigint
        type = 3;
        break;

      case -6:
        // Sybase
        // TINYINT
        type = 2;
        break;

      case -7:
        // Sybase
        // BIT
        type = 16;
        break;

      case 1111:
        // PostGres
        // bytea / int28 / reltime / tinterval
        type = 3;  // use bigint
        break;

      case 91:
        // PostGres
        // date
        type = 11;
        break;

      case 92:
        // PostGres
        // timestamp
        type = 14;
        break;

      case 93:
        // MySQL
        // timestamp
        type = 14;
        break;

      default:
        break;
    }

    if ((type < 1) || (type > 24))
    {
      System.err.println("Warning! - column name : "+name+
         " is of a type not recognised. Value : "+type);
      System.err.println("Defaulting to string");
      type = 12;
    }
  }

/**
  * Constructor with column type as a String.
  * Requires name, type, number of columns.
  * The last is only used for CHAR(x) and DATE fields.
  */
  public ColumnData (String name, String coltype, int columns)
  {
    this.name = name;
    this.columns = columns;
    this.columnType = coltype;

    Debug.println( "\nColumnData contructor 2 called:" );
    Debug.println( "  name: " + name );
    Debug.println( "  coltype: " + coltype );
    Debug.println( "  columns: " + columns );
    Debug.println( "  " );

    int i=0;
    boolean quit = false;
    this.type = -1;  // invalid
    while (!quit)
    {
      if (coltype.toUpperCase().compareTo(sqlTypes[i]) == 0)
      {
        this.type = i+1;  // starts at 1
        quit = true;
      }

      i++;
      if (i>=sqlTypes.length)
        quit = true;
    }

    if (this.type == -1)
      System.out.println("Column name : "+name+" Type : "+coltype+" is unknown");
  }

  /**
   * The number of columns defined for the table field.
   */
  public int getNumColumns() {
      return columns;
  }

/**
  * Returns the name of the column.
  */
  public String getName() {
      return name;
  }

  public String getColumnName() {
      return name;
  }
  
  public boolean isRequired() {
      return isRequired;
  }

/**
  * Converts to entry to a readable form.
  */
  public String toString()
  {
    String res = "";
    String digits = "";

  if ((type == 1) || (type == 11))
    digits = "("+columns+")";
  else
    digits = "";

    if ((type > sqlTypes.length) || (type < 0))
      res = "Type : "+type+"  Name : "+name;
    else
      res = "Type : "+sqlTypes[type-1]+digits+" Name : "+name;

  return res;
  }

/**
  * Writes out the equivalent java type of the column sql type.
  */
  public String getJavaType()
  {
    String jType = null;

    switch (type)
    {
      case 1:
      case 12:
      case 13:
      case 22:
        jType = "String";
        break;

      case 2:
        jType = "byte";
        break;

      case 3:
        jType = "long";
        break;

      case 4:
        jType = "int";
        break;

      case 5:
        jType = "short";
        break;

      case 6:
      case 8:
        jType = "double";
        break;

      case 9:
      case 10:
        jType = "java.math.BigDecimal";
        break;

      case 7:
        jType = "float";
        break;

      case 11:
        jType = "Date";
        // always return Timestamp as it's more accurate
        // and is a superset of Date
        // NOPE - lets follow Suns recommendations, so...
        //jType = "Timestamp";
        break;

      case 14:
        jType = "Timestamp";
        break;

      case 15:
        jType = "Time";
        break;

      case 16:
        jType = "boolean";
        break;

      case 17:
      case 18:
      case 19:
        jType = "byte[]";
        break;

      case 20:
        jType = "null";
        break;

      default:
        System.out.println("Warning - col type : "+type+" is unknown");
        jType = "unknown";
        break;
    }

    return jType;
  }


  /**
   * Some of these could be different. They can probably also be all-caps or all lowercase.
   * @see http://www.w3schools.com/sql/sql_datatypes_general.asp
   */
  public String getDatabaseColumnType()
  {
    String cType = null;

    switch (type)
    {
      case 1:
      case 12:
      case 13:
      case 22:
        cType = "text";
        break;

      case 2:
        cType = "byte";
        break;

      case 3:
        cType = "long";
        break;

      case 4:
        cType = "integer";
        break;

      case 5:
        cType = "integer";  // short
        break;

      case 6:
      case 8:
        cType = "double";
        break;

      case 9:
      case 10:
        cType = "double";  // java.math.BigDecimal
        break;

      case 7:
        cType = "double"; // float
        break;

      case 11:
        cType = "date";
        // always return Timestamp as it's more accurate
        // and is a superset of Date
        // NOPE - lets follow Suns recommendations, so...
        //jType = "Timestamp";
        break;

      case 14:
        cType = "timestamp";
        break;

      case 15:
        cType = "time";
        break;

      case 16:
        cType = "boolean";
        break;

      case 17:
      case 18:
      case 19:
        cType = "binary";  // byte[]
        break;

      case 20:
        cType = "null";
        break;

      default:
        cType = "unknown";
        break;
    }

    return cType;
  }




}
