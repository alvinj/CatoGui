import java.io.*;
import java.sql.*;
import java.util.*; // Properties

/* NOTE: this file is used as a resource by TableGen, but should not
         be compiled with the TableGen project. It is just read-from
         during the TableGen process.
*/

/**
  * <BR>
  * Base class for database accessor classes.
  * <BR>
  * Provides a set of common database functions.<BR>
  * These include making connections and running queries.
  * Modified by AJA to fit our database access methodology.
  * @author J.A.Carter
  * @version 1.2.1
  *
  * (c) Joe Carter 1998
  * Released under GPL. See LICENSE.
  */

public class DataObject
{

  final static int queryTimeout = 0;
  static boolean isOracle = false;
  public final static boolean debug = true;

/**
  * Constructor which loads properties in from the default file
  * called "db.properties".
  * This is only done on the first call of this constructor.
  * Later calls will use the existing connection pool.
  */
  public DataObject()
  {
    // do nothing
  }

/**
  * General last ditch exception catcher.
  *<BR>
  * Simply logs the error.
  */
  public void catchEx(Exception e)
  {
    // ? what to do with it now?
      //e.printStackTrace(System.out);
  }

/**
  * Executes an update/insert query in the database.<BR>
  * Note we <B>must</B> be connect to the database before
  * making this call.
  */
  public static boolean doUpdateQuery (Connection conn, String dbQuery) throws SQLException
  {
    boolean status = false;  // default to failure

    Statement dbStatement = conn.createStatement();
    dbStatement.setQueryTimeout(queryTimeout);
    int updateCount = dbStatement.executeUpdate(dbQuery);
    if (updateCount == 0)
    {
      status = false;
    }
    else if (updateCount == 1)
    {
      // all ok
      status = true;
    }
    else
    {
      status = false;
    }
    dbStatement.close();

    return status;
  }

/**
  * Executes a query in the database which returns a Resultset.<BR>
  * Note we <B>must</B> be connect to the database before
  * making this call.
  */
  public static ResultSet executeQuery(Connection conn, String query) throws SQLException
  {
    Statement queryStatement = conn.createStatement();
    queryStatement.setQueryTimeout(queryTimeout);
    ResultSet r = queryStatement.executeQuery(query);

    return r;
  }

/**
  * Contains some Oracle specific fixes to the timestamp.
  * Hopefully I've fixed it so it isn't incompatible with others now!
  * Also turns nulls into "null"s.
  */
  public static String formatTimeStamp(Timestamp ts)
  {
    String tsString = "null";
    if (ts!=null)
    {
      if (isOracle)
	tsString = "to_date('"+ts.toString().substring(0,19)+
                    "','YYYY-MM-DD HH24:MI:SS')" ;
      else
        tsString = "\'"+ts.toString()+"\'";
    }

    return tsString;
  }

/**
  * Convenience method for string enclosure.
  * Mainly for turning nulls into "null"s.
  */
  public static String formatString(String st)
  {
    String stString = "null";
    if (st!=null)
      stString = "'"+st+"'";

    return stString;
  }

}
