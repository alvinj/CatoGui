package dbgeneric;

import java.io.*;
import java.sql.*;
import java.util.*; // Properties
//import org.ewin.sql.*;

/**
  * <BR>
  * Base class for database accessor classes.
  * <BR>
  * Provides a set of common database functions.<BR>
  * These include making connections and running queries.
  *
  * @author J.A.Carter
  * @version 1.2
  *
  * (c) Joe Carter 1998
  * Released under GPL. See LICENSE.
  */

public
class DatabaseAccess implements Serializable
{
 /**
   * Stores the most recent error in the class
   */
  String errorString = "";

  final static int loginTimeout = 0;
  final static int queryTimeout = 0;

  static boolean isOracle = false;

  String dbUsername;
  String dbPassword;
  String driver;
  String url;
  int limit;

/**
  * This use of a static ConnectionManager allows all the
  * extended classes to use the same pool of connections to the
  * database. This is very effecient. The only downside is that
  * it effectively prevents connections to multiple databases.
  * To achieve multiple databases, this class would have to be copied
  * to a <b>different</b> name and hence give multiple connection pools
  */
  // This is now in the DbGlobal class. See there for why.
//  public static ConnectionManagerMin cm;

  public Connection _con;
  public Statement queryStatement = null;

  public final static boolean debug = true;

/**
  * Initialisation used by all the constructor.
  * Performs the setup required for connecting to the database
  * via JDBC.<BR>
  */
  private void initialise()
  {
    try
    {
      //Class c = Class.forName("connect.sybase.SybaseDriver");

      // create the ConnectionManager if we haven't already...
      //
      if (DbGlobal.cm == null)
      {
        DbGlobal.cm = new ConnectionManagerMin(driver,url,dbUsername,dbPassword);
        DbGlobal.cm.setLimit(limit);
      }
    }
    catch (Exception e)
    {
      setError("Unable to load the Sybase JDBC driver. "
             + e.toString());
      DbGlobal.cm = null;
    }
  }

  private void extractProperties(Properties props)
  {
    dbUsername = props.getProperty("username");
    dbPassword = props.getProperty("password");
    driver = props.getProperty("driver");
    url = props.getProperty("url");
    String strLimit = props.getProperty("connections");
    limit = 1;  // default
    if (strLimit != null)
    {
      try
      {
        limit = Integer.parseInt(strLimit);
      }
      catch (NumberFormatException e)
      {
        System.out.println(
            "db.properties: Limit must be a number - defaulting to "+limit);
      }
    }

    if (debug)
    {
      System.out.println("username = "+dbUsername);
      System.out.println("password = "+dbPassword);
      System.out.println("url = "+url);
      System.out.println("driver = "+driver);
      System.out.println("connections = "+limit);
    }
  }

/**
  * Constructor which loads properties in from the default file
  * called "db.properties".
  * This is only done on the first call of this constructor.
  * Later calls will use the existing connection pool.
  */
  public DatabaseAccess()
  {
    // only load properties if we do not have an existing
    // database connection
    //
    if (DbGlobal.cm == null)
    {
      Properties props = new Properties();
      try
      {
        FileInputStream fis = new FileInputStream("db.properties");
        props.load(fis);
        fis.close();

        extractProperties(props);
        initialise();
      }
      catch (IOException e)
      {
        System.err.println("Failed to load database properties from db.properties");
        e.printStackTrace(System.err);
      }
    }
  }

/**
  * Constructor with Properties.
  *<BR>
  * Sets the values used to connect to the database to be different
  * via the user defined Properties.<BR>
  * Includes defining the proxy, registering the driver etc
  */
  public DatabaseAccess (Properties props)
  {
    extractProperties(props);
    initialise();
  }

/**
  * Sets the error string.
  */
  public void setError (String error)
  {
    errorString = error;
    System.out.println("db access error : "+errorString);
  }

/**
  * Gets the error string.
  */
  public String getError ()
  {
    return errorString;
  }

/**
  * General last ditch exception catcher.
  *<BR>
  * Simply logs the error.
  */
  public void catchEx(Exception e)
  {
      setError("Unexpected Exception: " + e.toString());
      //e.printStackTrace(System.out);
  }

/**
  * Gets a valid connection to the database from the Connection
  * Manager and stores it in _con.
  *<BR>
  */
  public boolean connect()
  {
    _con = DbGlobal.cm.getConnection();
     if (_con == null)
       return false;
     else
       return true;
  }

/**
  * Puts our connection back into the ConnectionManager pool.
  *<BR>
  */
  public boolean disconnect() throws SQLException
  {
    _con.commit();
    DbGlobal.cm.putConnection(_con);
    return true;
  }

/**
  * Executes an update/insert query in the database.<BR>
  * Note we <B>must</B> be connect to the database before
  * making this call.
  */
  public synchronized boolean doUpdateQuery (String dbQuery) throws SQLException
  {
    boolean status = false;  // default to failure
//System.out.println("query is \n"+dbQuery);

    Statement dbStatement = _con.createStatement();
    dbStatement.setQueryTimeout(queryTimeout);
    int updateCount = dbStatement.executeUpdate(dbQuery);
    if (updateCount == 0)
    {
      setError("Failed to insert record");
      status = false;
    }
    else if (updateCount == 1)
    {
      // all ok
      status = true;
    }
    else
    {
      setError("Error! Database corruption!! inserted multiple records");
      status = false;
    }
    dbStatement.close();

    return status;
  }

/**
  * Executes a query in the database which returns a Resultset.<BR>
  * Note we <B>must</B> be connect to the database before
  * making this call.
  * This call is synchronised to limit the number of executing statements
  * to one per DatabaseAccess instantiation.
  */
  public synchronized ResultSet executeQuery(String query) throws SQLException
  {
    if (queryStatement != null)
      queryStatement.close();

    queryStatement = _con.createStatement();
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
