package com.devdaily.dbgrinder.model;

import java.sql.*;
import junit.framework.*;
import java.util.*;

//
//  need to add to
//  Project  Project Properties  Run ... VM Parameters for JBuilder
//

public class _Database extends TestCase
{

  String _driver = "org.gjt.mm.mysql.Driver";
  String _url = "jdbc:mysql://localhost/junit_test";

/*
  protected void setUp()
  {
    try
    {
      //Class.forName("org.gjt.mm.mysql.Driver");
      //String url = "jdbc:mysql://localhost/junit_test";
      //connection = DriverManager.getConnection(url,"","");
      //Class.forName("org.postgresql.Driver");
      //String url = "jdbc:postgresql://localhost/junit_test";
      //connection = DriverManager.getConnection(url,"username","password");
    }
    catch (SQLException se)
    {
      System.err.println( "SQLException in setUp(): " + se.getMessage() );
    }
    catch (ClassNotFoundException cnfe)
    {
      System.err.println( "ClassNotFoundException in setUp(): " + cnfe.getMessage() );
    }
  }
*/

  //--------------------------------------------------------------------------//

  public void testSELECTAgainstDatabase()
  {
    Connection connection = null;
    try
    {
      Class.forName(_driver);
      connection = DriverManager.getConnection(_url,"","");
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT uid, username, password, email FROM user WHERE uid=1");
      if ( rs.next() )
      {
        int uid = rs.getInt("uid");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String email = rs.getString("email");
        assertEquals("uid == 1",uid,1);
        assertEquals("username == ala",username,"ala");
        assertEquals("password == ala",password,"ala");
        assertEquals("email = java@devdaily.com", email, "java@devdaily.com");
      }
      stmt.close();
      connection.close();
    }
    catch (ClassNotFoundException cnfe)
    {
      System.err.println( "ClassNotFoundException -- " + cnfe.getMessage() );
    }
    catch (SQLException se)
    {
      System.err.println( "SQLException - " + se.getMessage() );
    }
    finally
    {
    }
  }


  //--------------------------------------------------------------------------//
/*
  public void test_getTableNames()
  {
    try
    {
      String username = "";
      String password = "";
      Database database = new Database(_driver,_url,username,password);
      database.makeConnection();
      assertNotNull(database);
      String _databaseType = "NORMAL";
      String _catalog = "";
      String _schema = "";
      //Hashtable _tablesList = new Hashtable();
      Vector _tableNames = database.getTableNames(_databaseType,_catalog,_schema,null);
      Iterator it = _tableNames.iterator();
      while ( it.hasNext() )
      {
        String elem = (String)it.next();
        System.err.println( "elem: " + elem );
      }
    }
    catch(SQLException sqle)
    {
    }
    catch(ClassNotFoundException cnfe)
    {
    }
  }
*/


  //--------------------------------------------------------------------------//

  public static void main(String args[])
  {
    junit.textui.TestRunner.run(_Database.class);
  }

  public _Database(String name)
  {
    super(name);
  }

  /**
   * add one line here for each test in the suite
   */
  public static Test suite()
  {
    TestSuite suite = new TestSuite();

    // run tests manually
    //suite.addTest( new _Database("test1") );
    //return suite;

    // or, run tests dynamically
    return new TestSuite(_Database.class);
  }

}



