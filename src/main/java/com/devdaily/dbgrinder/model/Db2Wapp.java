package com.devdaily.dbgrinder.model;

import java.io.*;
import java.sql.*;
import java.util.*;
import com.devdaily.dbgrinder.utility.StringUtils;
import com.devdaily.utils.Debug;


// TODO delete this class


/* The only files that need to be compiled for this project are:

   FILES TO COMPILE:
   -----------------
       Db2Wapp.java
       ColumnData.java
       DomainObject.java

   RESOURCE FILES:
   ---------------
       tablegen.properties -- defines what Db2Wapp should do when it is run
       dbgeneric/DataObject.java (read-from by Db2Wapp)

   TO RUN:
   -------
   Db2Wapp.java should be run like this:
       java Db2Wapp tablegen.properties

*/

/**
  * Db2Wapp
  * <p>A generator of container classes for a database.
  * This application uses the database metadata to create
  * one object per table designed to hold the type of data
  * stored within that table.</p>
  * It also generate methods to update,retrieve and insert
  * the object data within the database. The update and
  * retrieve methods come in two flavours, ByKey and ByIndex.</p>
  * <p>If keys are found on a table then updateByKey and
  * retrieveByKey methods are created</p>
  * <p>If foreign_keys are found on the table retrieveByXXX methods are created for each key
  * as well
  * <p>If _doIndexes are found on a table then updateByIndex and
  * retrieveByIndex methods are created</p>
  * insert and getFromResultSet methods are always created.
  *
  * @author  J.A.Carter (original author)
  * @version 2.2
  * <c) Joe Carter 1998
  * Released under GPL. See LICENSE for full details.
  *
  * changes in version 2.2:
  * <br>added DomainObject classes (DomainObject, Field, Method, MethodArgument)
  * <br>added jsp-generation capability
  *
  * changes in version 2.3:
  * <br>corrected problem where the object class -- it should not extend DataObject
  * <br>made the DO class final
  * <br>made the methods in the DO class synchronized
  * <br>added additional methods to generate the "add" jsp
  * <br>starting adding methods to generate the "add controller" jsp
  *
  * recent changes:
  * <br>corrected problems in _currentClassName and _currentObjectName not being camel case(10/13/2001)
  */
public class Db2Wapp
{
  // the sub-dir where DataObject.java is located
  String _genericFileDirectory = "Db2WappResources";

  // this program will build a list of "domain objects" from the db tables it reads
  Vector _domainObjects = null;
  DomainObject _currentDomainObject = null;

  BufferedWriter _outputWriter;

  String _url;
  String _username;
  String _password;
  String _databaseType;
  String _catalog;
  String _driver;
  String _packageName;
  List   _desiredColumns;   // a list of the columns from the current db table that the user wants to use
  String _schema;
  String _prefix;

  // db2wapp fields
  //String _homeDir = "c:/wapp";
  //String _classOutputDir = "c:/wapp/classes-out";
  //String _jspOutputDir = "c:/wapp/jsp-out";
  //String _jspTemplateDir = "c:/wapp/jsp-template";

  // specifies the do method that should be written for this run
  String _dataObjectMethodToWrite;

  // File objects we need to write the model, container, and controllers to
  File _modelFolder;
  File _containerFolder;
  File _controllerFolder;

  private String _currentClassName = null;
  private String _currentObjectName = null;

  private static final String LEADING_SPACE = "  ";

  private static final String POSTGRES = "POSTGRES";
  private static final String MYSQL    = "MYSQL";
  private static final String OTHER    = "OTHER";

  boolean _keys;
  boolean _doForeignKeys;
  boolean _doIndexes;

  // fudge for the mm.mysql _driver which returns column
  // types as strings not int's
  boolean types_are_strings;

  // set this flag to be compatible with versions < 1.5
  // We're changing to fit the JavaBeans spec better
  boolean oldversion = false;

  DatabaseMetaData _metaData;
  Connection _connection;
  BufferedWriter _currentOutput;

  // added by aja for new effort
  StringWriter _modelClass = new StringWriter();
  StringWriter _dataObjectClass = new StringWriter();

  Vector _tableNames;
  Vector _colData;
  Vector _keyData;
  Vector _foreignKeyData;
  Vector _indexData;
  // names of tables to generate for. If null we do all.
  Hashtable _tablesList;
  // class names that are associated with the given table names.
  // let's us rename our DO objects to have more meaningful names.
  Hashtable _tableNameToClassNameMap;

  static String[] _genericFiles = { "DataObject" };

  // first attempt to handle different database naming conventions
  /** @todo -- this should be a configurable param in a properties file or elsewhere */
  static final String DATABASE_FIELD_NAME_UNDERSCORE_CONVENTION = "UNDERSCORE";
  static final String DATABASE_FIELD_NAME_CAMELCASE_CONVENTION = "CAMEL_CASE";
  static final String DATABASE_FIELD_NAME_CONVENTION = DATABASE_FIELD_NAME_CAMELCASE_CONVENTION;


  /**
    * Constructor from a properties file.
    * Default file is "tablegen.properties"
    *
    * <TABLE>
    * <TR><TH>Parameter<TH>Description</TR>
    * <TR><TD>_driver</TD><TD>JDBC driver name</TD></TR>
    * <TR><TD>_url</TD><TD>JDBC URL (path)</TD></TR>
    * <TR><TD>_username</TD><TD>DB username</TD></TR>
    * <TR><TD>_password</TD><TD>DB password</TD></TR>
    * <TR><TD>class_output_dir</TD><TD>directory where generated classes are placed.</TD></TR>
    * <TR><TD>package_name</TD><TD>package name to use for gen classes.</TD></TR>
    * <TR><TD>_keys</TD><TD>(optional) Generate key methods if keys available</TD></TR>
    * <TR><TD>foreign_keys</TD><TD>(optional) Generate retrieve methods for
    *         each foreign_key available</TD></TR>
    * <TR><TD>_doIndexes</TD><TD>(optional) Generate index methods if indexes available</TD></TR>
    * <TR><TD>_schema</TD><TD>(optional) DB _schema to use</TD></TR>
    * <TR><TD>oldversion</TD><TD>(optional) Be compatible with TG v1.4 or less</TD></TR>
    * <TR><TD>table_list</TD><TD>(optional) Comma separated list of tables to build</TD></TR>
    * <TR><TD>types_are_strings</TD><TD>Some drivers (e.g. MM MySql) return the types
    *     as String. If so set this to yes. Leave out otherwise</TD>
    * <TR><TD>_prefix</TD><TD>A string to _prefix to classes names.
    *     To prevent namespace problems</TD></TR>
    * </TABLE>
    */
  public Db2Wapp (Properties props, String dataObjectMethodToWrite)
  throws IOException,SQLException,ClassNotFoundException
  {
    _dataObjectMethodToWrite = dataObjectMethodToWrite;
    assignGivenPropertiesToOurValues(props);
    putTableNamesIntoTableList(props.getProperty("table_list"),props.getProperty("class_list"));
    // really bad kludge, in particular to support the new 3-args constructor
    // where _domainObjects is passed in
    if ( _domainObjects == null )
    {
      _domainObjects = new Vector();
      run();
    }
  }

  public Db2Wapp (Properties props,
                  String dataObjectMethodToWrite,
                  Vector domainObjectList)
  throws IOException,SQLException,ClassNotFoundException
  {
    this(props,dataObjectMethodToWrite);
    _domainObjects = domainObjectList;
    run();
  }


  /**
   * runs this program by calling the appropriate methods
   */
  private void run ()
  throws SQLException, ClassNotFoundException
  {
    JavaBeanFactory.setIndentation(JavaBeanFactory.INDENT_TWO_CHARACTERS);
    try
    {
      Database database = new Database(_driver,_url,_username,_password);
      database.makeConnection();
      _connection = database.getConnection();
      _metaData = database.getTableMetaData();
      _tableNames = database.getTableNames(_databaseType,_catalog,_schema,_tablesList);
      generateBeansAndContainers();
    }
    catch(SQLException sqle)
    {
      throw sqle;
    }
    catch(ClassNotFoundException cnfe)
    {
      throw cnfe;
    }
  }

  public String getDataObjectClass()
  {
    return _dataObjectClass.toString();
  }

  public String getModelClass()
  {
    return _modelClass.toString();
  }

  public String getCurrentClassName()
  {
    return _currentClassName;
  }

  public String getCurrentObjectName()
  {
    return _currentObjectName;
  }

  /**
   * Assigns properties from the Properties file to class fields.
   */
  private void assignGivenPropertiesToOurValues (Properties props)
  {
    _driver         = props.getProperty("driver");
    _url            = props.getProperty ("url");
    _username       = props.getProperty("username");
    _password       = props.getProperty("password");
    _packageName    = props.getProperty("package_name");
    _schema         = props.getProperty("_schema");
    _databaseType   = props.getProperty("database_type");
    String commaSeperatedListOfDesiredColumns = props.getProperty("desiredColumns");
    _desiredColumns = copyCommaSeperatedFieldsToList(commaSeperatedListOfDesiredColumns);

    //_classOutputDir = props.getProperty("class_output_dir");
    //_homeDir        = props.getProperty("home_dir");

    // decide whether to enable the use of keys and indexes...
    // (some drivers do not support this
    String keysString = props.getProperty("keys");
    _keys = true;  // default to _keys on
    if (keysString != null)
    {
      if (keysString.toLowerCase().compareTo("no") == 0)
      {
        _keys = false;
      }
    }

    String foreignKeysString = props.getProperty("foreign_keys");
    set_doForeignKeys(foreignKeysString);

    String indexesString = props.getProperty("indexes");
    //System.err.println( "  indexesString = " + indexesString );
    _doIndexes = true;  // default to _doIndexes on
    if (indexesString != null)
    {
      if (indexesString.toLowerCase().compareTo("no") == 0)
        _doIndexes = false;
    }

    String typesString = props.getProperty("types_are_strings");
    types_are_strings = false;  // default col type returned as int
    if (typesString != null)
    {
      if (typesString.toLowerCase().compareTo("yes") == 0)
      {
        types_are_strings = true;
      }
    }

    String oldStr = props.getProperty("oldversion");
    oldversion = false;
    if (oldStr != null)
    {
      if (oldStr.toLowerCase().compareTo("yes") == 0)
      {
        oldversion = true;
      }
    }

    _prefix = props.getProperty("prefix");
    if (_prefix == null)
    {
      _prefix = "";
    }
  }

  private void addToDomainObjectList (DomainObject domainObject)
  {
    _domainObjects.add(domainObject);
  }

  /**
    * Parses a comma separated list of table names into the _tablesList Vector.
    */
  private List copyCommaSeperatedFieldsToList (String commaSeparatedFields)
  {
    Debug.println( "copyCommaSeperatedFieldsToList() called" );
    List aList = new LinkedList();
    if ( commaSeparatedFields == null )
    {
      return aList;
    }

    StringTokenizer st = new StringTokenizer(commaSeparatedFields,",");
    String currentField = null;
    while ( st.hasMoreElements() )
    {
      currentField = st.nextToken();
      aList.add(currentField);
    }

    return aList;
  }


  /**
    * Parses a comma separated list of table names into the _tablesList Vector.
    */
  void putTableNamesIntoTableList(String tablesNames, String classNames)
  {
    Debug.println( "putTableNamesIntoTableList called" );
    if ( tablesNames != null )
    {
      String currentTableName = null;
      String currentClassName = null;

      // _tablesList holds the names of the tables that the user wants to create DO
      // objects for.
      _tablesList = new Hashtable();
      StringTokenizer st = new StringTokenizer(tablesNames,",");
      currentTableName = null;
      while ( st.hasMoreElements() )
      {
        currentTableName = st.nextToken();
        _tablesList.put(currentTableName,currentTableName);
      }

      // create a mapping of tableName->desiredClassName
      StringTokenizer tst = new StringTokenizer(tablesNames,",");
      StringTokenizer cst = new StringTokenizer(classNames,",");

      // there better be a one-to-one relationship between _tableNames
      // and classNames, or this is going to die hard.
      _tableNameToClassNameMap = new Hashtable();
      while ( tst.hasMoreElements() )
      {
        currentTableName = tst.nextToken();
        currentClassName = cst.nextToken();
        _tableNameToClassNameMap.put(currentTableName,currentClassName);
      }
    }
  }

  /**
    * Generates the JavaBean and Container (database access functions) for
    * list of tables.
    */
  public void generateBeansAndContainers()
  throws SQLException
  {
    String tableName;
    boolean isDomainClass = false;

    try
    {
      for ( Enumeration e=_tableNames.elements(); e.hasMoreElements(); )
      {
        tableName = (String)e.nextElement();
        Debug.println( "tableName = "  + tableName );
        _currentClassName = (String)_tableNameToClassNameMap.get(tableName);
        _currentClassName = StringUtils.convertUnderscoreNameToUpperCase(_currentClassName);
        _currentClassName = StringUtils.makeFirstCharUpperCase(_currentClassName);
        _currentObjectName = StringUtils.makeFirstCharLowerCase(_currentClassName);

        Debug.println( "_currentClassName = "  + _currentClassName );
        Debug.println( "_currentObjectName = " + _currentObjectName );

        // 1. create the class that represents the table object
        writeJavaBeanToCurrentOutput(tableName);

        // 2. write the data object (DO) class
        writeDataObjectToCurrentOutput(tableName);
      }

    }
    catch (IOException e)
    {
      e.printStackTrace(System.out);
    }
  }


  /**
   * Create the model, container, and controller folders, using the "_classOutputDir"
   * and "_packageName" as the "root" for these other folders.
   */
   /*
  private void createClassOutputFolders ()
  {
    // create the model, container, and controller folders
    File rootPackageFile = new File(_classOutputDir + "/" + _packageName.replace('.','/') );
    rootPackageFile.mkdirs();
    _modelFolder = new File(rootPackageFile, "model");
    _containerFolder = new File(rootPackageFile, "container");
    _controllerFolder = new File(rootPackageFile, "controller");
    _modelFolder.mkdir();
    _containerFolder.mkdir();
    _controllerFolder.mkdir();
  }
  */


  /**
   *
   */
  public void writeJavaBeanRepresentingTable(String tableName, String className, boolean isDomainClass)
  throws java.io.IOException
  {
    // write the class header
    writeHeader(className,isDomainClass);

    // write the class members (fields)
    for ( Enumeration e2=_colData.elements(); e2.hasMoreElements(); )
    {
      writeVariable((ColumnData)e2.nextElement());
    }
    outPrintln("");

    // write the class members (get/set methods)
    for ( Enumeration e2=_colData.elements(); e2.hasMoreElements(); )
    {
      writeSetGetMethods((ColumnData)e2.nextElement());
    }
    outPrintln("}");
  }


  public void writeDataObjectRepresentingTable(String tableName,
                                               String className,
                                               boolean isDomainClass)
  throws java.io.IOException,SQLException
  {

    /** @todo This method is all screwed up and needs a boatload of work!!! */

    if ( _dataObjectMethodToWrite.equals(Project.CREATION_MODE_SELECT) )
    {
      createDataObjectSelectMethod(tableName);
    }
    else if ( _dataObjectMethodToWrite.equals(Project.CREATION_MODE_INSERT) )
    {
      writeInsert(tableName);
    }
    else if ( _dataObjectMethodToWrite.equals(Project.CREATION_MODE_DELETE) )
    {
      createDataObjectDeleteMethod(tableName);
    }
    else if ( _dataObjectMethodToWrite.equals(Project.CREATION_MODE_UPDATE) )
    {
      createDataObjectUpdateMethod(tableName);
    }

/*
      if (_keys)
      {
        getTableKeys(tableName); // updates the _keyData variable
        if ( _keyData.size() > 0 )
        {
          if ( _dataObjectMethodToWrite.equals(Project.CREATION_MODE_SELECT) )
          {
            writeSelectMethods(tableName);
          }
          else if ( _dataObjectMethodToWrite.equals(Project.CREATION_MODE_DELETE) )
          {
            writeDelete(tableName,_keyData,"deleteByKey");
          }
          // updateByKey does not seem to be generating the proper code;
          // it should also be renamed to 'update' in the future.
          // writeUpdate(tableName,_keyData,"updateByKey");
          // countByKey is broken (prints "customer.id" instead of "id")
          // writeCount(tableName,_keyData,false,"countByKey");
          // countLikeKey is broken (prints "customer.id" instead of "id")
          // writeCount(tableName,_keyData,true,"countLikeKey");
        }
      }
*/

    /** @todo Not sure how this works. Comment-out and bring back in the future. */
    /*
    if (! getDatabaseType(_driver).equals(POSTGRES) )
    {
      if (_doForeignKeys)
      {
        Debug.println( "  _doForeignKeys was true" );
        _foreignKeyData = Table.getTableImportedKeys(tableName, _metaData, _catalog, _schema);
        for (Enumeration fkEnum = _foreignKeyData.elements() ; fkEnum.hasMoreElements() ;)
        {
          writeImportedMethods(tableName,(FKDefinition)fkEnum.nextElement());
        }

        _foreignKeyData = Table.getTableExportedKeys(tableName, _metaData, _catalog, _schema);
        for (Enumeration fkEnum = _foreignKeyData.elements() ; fkEnum.hasMoreElements() ;)
        {
          writeExportedMethods(tableName,(FKDefinition)fkEnum.nextElement());
        }
      }
    }
    */

    //writeRetrieveAll(tableName,null,true,"selectAllWhere",true);
    //writeRetrieveAll(tableName,null,false,"selectAll",true);
  }


  void writeSelectMethods(String tableName)
  throws IOException
  {
    writeGetFromResultSet();
    writeRetrieve(tableName,_keyData,"selectByKey");
    writeRetrieveAll(tableName,_keyData,false,"selectAllLikeKey",true);
  }


  /**
   * Determine the database type from the name of the driver.
   */
  private String getDatabaseType(String driver)
  {
    if ( driver.toUpperCase().indexOf(POSTGRES) >= 0 )
    {
      return POSTGRES;
    }
    else if ( driver.toUpperCase().indexOf(MYSQL) >= 0 )
    {
      return MYSQL;
    }
    else
    {
      return OTHER;
    }

  }


  /**
   *  Most methods call this method.
   *  Newer methods call the tableName/isDomainClass derivation of this method.
   */
  void writeHeader(String className) throws IOException
  {
    writeHeader(className, true);
  }

  /**
    * Generates the container/database access functions for each table.
    */
  void writeHeader(String className, boolean isDomainClass) throws IOException
  {
    Debug.println( "writeHeader() called" );
    if ( (_packageName != null)  &&  (!_packageName.trim().equals("")) )
    {
      if (isDomainClass)
      {
        outNoLeadingSpace("package " + _packageName + ".model;\n");
      }
      else
      {
        outNoLeadingSpace("package " + _packageName + ".container;\n");
      }
    }
    outNoLeadingSpace("/**");
    if (isDomainClass)
      outNoLeadingSpace(" * " + StringUtils.firstCharacterUpperCase(className));
    else
      outNoLeadingSpace(" * " + className);
    outNoLeadingSpace(" */");
    outNoLeadingSpace("");
    if (isDomainClass)
    {
      // DEAD CODE -- no longer needed/desired
      //outNoLeadingSpace("import java.io.*;");
      //outNoLeadingSpace("import java.util.*;");
    }
    else
    {
      outNoLeadingSpace("import com.devdaily.util.DDContainer;");
      outNoLeadingSpace("import java.io.*;");
      outNoLeadingSpace("import java.sql.*;");
      outNoLeadingSpace("import java.util.*;");
    }
    //for (int i=0; i<_genericFiles.length; i++)
    //{
    //  if ( _packageName!=null && (!_packageName.trim().equals("")) )
    //    outNoLeadingSpace("import "+_packageName+"."+_genericFiles[i]+";");
    //  else
    //    outNoLeadingSpace("import "+_genericFiles[i]+";");
    //}
    outNoLeadingSpace("");
    if (isDomainClass)
      outNoLeadingSpace("public class "
                        + _prefix
                        + StringUtils.firstCharacterUpperCase(className) );
          //+ " extends DataObject");
    else
      outNoLeadingSpace("public final class "
                        + _prefix
                        + className
                        + " extends DDContainer");
    outNoLeadingSpace("{\n");
  }

  /**
    * Writes out the variables with javadoc info.
    */
  void writeVariable(ColumnData cd) throws IOException
  {
    String varScope = "private";
    // AJA: commented this out to get this to work better with MS SQL Server database field naming conventions
    // (i.e., "camel case")

    String varName = createVarNameFromCdName(cd);

    //String varName = StringUtils.convertUnderscoreNameToUpperCase(cd.getName().toLowerCase());
    //String varName = StringUtils.convertUnderscoreNameToUpperCase(cd.getName());
    String varType = cd.getJavaType();

    outNoLeadingSpace("  " + varScope + " "
      + varType
      + " "
      + varName
      + ";");

    // use the current variable info to create a Field; then add the Field to the
    // current domain object that we're building
//    Field f = new Field(varScope, varType, varName);
//    _currentDomainObject.addField(f);
  }

  /**
    * Writes out the set and get functions for each variable.
    */
  void writeSetGetMethods(ColumnData cd) throws IOException
  {
    Debug.println( "writeSetGetMethods() called" );
    String name = createNameFromCdName(cd);

    String newName = name;
    String varName = name;

    // capitalize the first letter of newName (used for method naming)
    newName = newName.substring(0,1).toUpperCase()+
              newName.substring(1,newName.length());
    newName = StringUtils.convertUnderscoreNameToUpperCase(newName);
    varName = StringUtils.convertUnderscoreNameToUpperCase(varName);

    // create the current Method
    String methodScope = "public";
    String methodReturnType = "void";
    String methodName = "set" + newName;
    Method setMethod = new Method(methodScope,methodReturnType,methodName);
    Method getMethod = new Method(methodScope,methodReturnType,"get"+newName);

    // build the current method argument
    MethodArgument methodArg = new MethodArgument();
    methodArg.argName = varName;
    methodArg.argType = cd.getJavaType();

    setMethod.addMethodArgument(methodArg);
    _currentDomainObject.addSetMethod(setMethod);
    _currentDomainObject.addGetMethod(getMethod);

    // "SET" methods
    writeJavaBeanSetMethod(cd, methodReturnType, newName, varName);

    // "GET" methods
    writeJavaBeanGetMethod(cd, newName, varName);
  }

  private String createNameFromCdName(ColumnData cd)
  {
    String name = null;
    if ( DATABASE_FIELD_NAME_CONVENTION.equals(DATABASE_FIELD_NAME_UNDERSCORE_CONVENTION) )
    {
      name = cd.getName().toLowerCase();
    }
    else if ( DATABASE_FIELD_NAME_CONVENTION.equals(DATABASE_FIELD_NAME_CAMELCASE_CONVENTION) )
    {
      name = cd.getName();
    }
    else
    {
      // default
      name = cd.getName().toLowerCase();
    }
    return name;
  }

  private String createVarNameFromCdName(ColumnData cd)
  {
    String varName = null;
    if ( DATABASE_FIELD_NAME_CONVENTION.equals(DATABASE_FIELD_NAME_UNDERSCORE_CONVENTION) )
    {
      varName = StringUtils.convertUnderscoreNameToUpperCase(cd.getName().toLowerCase());
    }
    else if ( DATABASE_FIELD_NAME_CONVENTION.equals(DATABASE_FIELD_NAME_CAMELCASE_CONVENTION) )
    {
      varName = StringUtils.convertUnderscoreNameToUpperCase(cd.getName());
    }
    else
    {
      // default
      varName = StringUtils.convertUnderscoreNameToUpperCase(cd.getName().toLowerCase());
    }
    return varName;
  }

  /**
    * Writes out the retrieveAll function.
    * This retrieves all the rows that match the search pattern.
    */
  void writeRetrieveAll (String tableName,
                         Vector params,
                         boolean customWhere,
                         String methodName,
                         boolean bLike) throws IOException
  {
    //String query = "SELECT * FROM " + tableName + "\"";
    String query = createSelectQuery(tableName);
    String where = createWhereClause(params,bLike);

    outPrintln("/**");
    outPrintln("  * SQL SELECT from the database for table \""+StringUtils.firstCharacterUpperCase(tableName)+"\"");
    outPrintln("  */");

    if (customWhere)
      outPrintln("public List "+methodName+"(Connection conn, String where) \n  throws SQLException");
    else
      outPrintln(createMethodLine(methodName,params,"List"));

    outPrintln("{");
    outPrintln("  List returnRows = new LinkedList();");
    outPrintln("  " + _prefix+_currentClassName+" currentRow = null;");
    //outPrintln("  " + _prefix+StringUtils.firstCharacterUpperCase(tableName)+" currentRow;");

    String statementName = "statement";
    String resultSetName = "resultSet";
    outPrintln("  Statement " + statementName + " = null;");
    outPrintln("  ResultSet " + resultSetName + " = null;");
    outPrintln("  try" );
    outPrintln("  {" );

    if (customWhere)  outPrintln("    String query = \"" + query + " + \" where \" + where;");
    else              outPrintln("    String query = \"" + query + where + ";");

    outPrintln("    " + statementName + " = conn.createStatement();");
    outPrintln("    " + resultSetName + " = " + statementName + ".executeQuery(query);");
    outPrintln("    while ( " + resultSetName + ".next() )");
    outPrintln("    {");
    //outPrintln("    currentRow = new "+_prefix+_currentClassName+"();");
    outPrintln("      currentRow = getFromResultSet(" + resultSetName + ");");
    outPrintln("      returnRows.add(currentRow);");
    outPrintln("    }");
    outPrintln("    return returnRows;");
    outPrintln("  }" );
    outPrintln("  catch (SQLException se)" );
    outPrintln("  {" );
    outPrintln("    // log exception if desired" );
    outPrintln("    throw se;" );
    outPrintln("  }" );
    outPrintln("  finally" );
    outPrintln("  {" );
    outPrintln("    if ( " + statementName + " != null )" );
    outPrintln("    {" );
    outPrintln("      " + statementName + ".close();" );
    outPrintln("    }" );
    outPrintln("  }" );
    outPrintln("}");
    outPrintln("");
  }

  /**
    * Writes out the retrieve (SELECT) function.
    */
  void writeRetrieve (String tableName,
                      Vector params,
                      String methodName) throws IOException
  {
    String query = createSelectQuery(tableName);
    String where = createWhereClause(params,false);

    outPrintln("/**");
    outPrintln(" * SQL SELECT from the database for table \""+tableName+"\"");
    outPrintln(" */");
    outPrintln(createMethodLine(methodName,params,"boolean"));
    outPrintln("{");

    outPrintln("  " + _prefix+_currentClassName+" currentRow = null;");

    String statementName = "statement";
    String resultSetName = "resultSet";
    outPrintln("  Statement " + statementName + " = null;");
    outPrintln("  ResultSet " + resultSetName + " = null;");
    outPrintln("  try" );
    outPrintln("  {" );
    outPrintln("    String query = \"" + query + where + ";");
    outPrintln("    " + statementName + " = conn.createStatement();");
    outPrintln("    " + resultSetName + " = " + statementName + ".executeQuery(query);");
    outPrintln("    if ( " + resultSetName + ".next() )");
    outPrintln("    {");
    outPrintln("      currentRow = getFromResultSet(" + resultSetName + ");");
    outPrintln("    }");
    outPrintln("  }" );
    outPrintln("  catch (SQLException se)" );
    outPrintln("  {" );
    outPrintln("    // log exception if desired" );
    outPrintln("    throw se;" );
    outPrintln("  }" );
    outPrintln("  finally" );
    outPrintln("  {" );
    outPrintln("    if ( " + statementName + " != null )" );
    outPrintln("    {" );
    outPrintln("      " + statementName + ".close();" );
    outPrintln("    }" );
    outPrintln("  }" );
    outPrintln("}" );
    outPrintln("");
  }


  /**
   * Create the 'SELECT' statement for the current _colData object.
   */
  private String createSelectQuery(String tableName)
  {
    ColumnData cd = null;
    StringBuffer columnss = new StringBuffer("");
    String query = " SELECT ";
    for (Enumeration e=_colData.elements(); e.hasMoreElements(); )
    {
      cd = (ColumnData)e.nextElement();
      columnss.append(cd.getName());
      if ( e.hasMoreElements() )
      {
        columnss.append(",");
      }
      else
      {
        columnss.append(" ");
      }
    }
    query += columnss;
    query += "\"\n                   + \" FROM " + tableName + " \"";
    return query;
  }

  /**
    * Writes out the retrieve function.
    */
  void writeDelete (String tableName,
                    Vector params,
                    String methodName) throws IOException
  {
    String key = null;
    String keyType = null;
    String query = "DELETE FROM "+ tableName + " WHERE ";
    String tmp = "public void " + methodName + "(Connection conn";

    // work out the query string and the method parameters.
    //
    for (Enumeration e=params.elements(); e.hasMoreElements(); )
    {
      tmp += ", ";
      key = (String)e.nextElement();
      keyType = getColType(key);
      tmp = tmp + keyType + " " + key.toLowerCase();

      // NB others types to add here - like date,time etc etc
      //
      query += key + " = \" + " + StringUtils.sqlQuote(key.toLowerCase(),keyType);

      if (e.hasMoreElements())
      {
        //tmp = tmp + ",";
        query += " + \" and ";
      }
      else
      {
        tmp = tmp + ") \n  throws SQLException";
      }
    }

    String statementName = "statement";
    String resultSetName = "resultSet";

    outPrintln("/**");
    outPrintln("  * Deletes from the database for table \"" + tableName + "\"." );
    outPrintln("  */");
    outPrintln(tmp);
    outPrintln("{");
    outPrintln("  Statement " + statementName + " = null;");
    outPrintln("  ResultSet " + resultSetName + " = null;");
    outPrintln("  try" );
    outPrintln("  {" );
    outPrintln("    boolean deleteStatus = false;");
    outPrintln("    String query = \"" + query + ";");
    outPrintln("    " + statementName + " = conn.createStatement();");
    outPrintln("    " + resultSetName + " = " + statementName + ".executeUpdate(query);");
    //outPrintln("  deleteStatus = doUpdateQuery(conn,query);");
    //outPrintln("  return deleteStatus;");

    outPrintln("  }" );
    outPrintln("  catch (SQLException se)" );
    outPrintln("  {" );
    outPrintln("    // log exception if desired" );
    outPrintln("    throw se;" );
    outPrintln("  }" );
    outPrintln("  finally" );
    outPrintln("  {" );
    outPrintln("    if ( " + statementName + " != null )" );
    outPrintln("    {" );
    outPrintln("      " + statementName + ".close();" );
    outPrintln("    }" );
    outPrintln("  }" );
    outPrintln("}");
    outPrintln("");
  }


  /**
    * Writes out the getFromResultSet method.
    * The getFromResultSet method interprets the returned result set
    * from a retrieve and update the object with those values.
    */
  void writeGetFromResultSet() throws IOException
  {
    String col = null;
    String lcol = null;
    ColumnData cd = null;
    String colType = null;
    Vector gets = new Vector();  // used to store the r1=r.getString("r1"); thingies
    String get = null;

    String currentObjectName = StringUtils.makeFirstCharLowerCase(_currentClassName).trim();

    for (Enumeration e=_colData.elements(); e.hasMoreElements(); )
    {
      cd = (ColumnData)e.nextElement();
      col = cd.getName();
      lcol = cd.getName().toLowerCase();
      colType = cd.getJavaType();

      // work out which data type we are getting for each variable

      // convert variable names from underscores to camel-naming
      lcol = StringUtils.convertUnderscoreNameToUpperCase(lcol);

      if (colType.compareTo("String") == 0)
        get = currentObjectName+".set"+StringUtils.firstCharacterUpperCase(lcol)+"( r.getString(\""+col+"\") );";
      else if (colType.compareTo("byte") == 0)
        get = currentObjectName+".set"+StringUtils.firstCharacterUpperCase(lcol)+"( r.getByte(\""+col+"\") );";
      else if (colType.compareTo("long") == 0)
        get = currentObjectName+".set"+StringUtils.firstCharacterUpperCase(lcol)+"( r.getLong(\""+col+"\") );";
      else if (colType.compareTo("int") == 0)
        get = currentObjectName+".set"+StringUtils.firstCharacterUpperCase(lcol)+"( r.getInt(\""+col+"\") );";
      else if (colType.compareTo("short") == 0)
        get = currentObjectName+".set"+StringUtils.firstCharacterUpperCase(lcol)+"( r.getShort(\""+col+"\") );";
      else if (colType.compareTo("float") == 0)
        get = currentObjectName+".set"+StringUtils.firstCharacterUpperCase(lcol)+"( r.getFloat(\""+col+"\") );";
      else if (colType.compareTo("double") == 0)
        get = currentObjectName+".set"+StringUtils.firstCharacterUpperCase(lcol)+"( r.getDouble(\""+col+"\") );";
      else if (colType.compareTo("Date") == 0)
        get = currentObjectName+".set"+StringUtils.firstCharacterUpperCase(lcol)+"( r.getDate(\""+col+"\") );";
      else if (colType.compareTo("Timestamp") == 0)
        get = currentObjectName+".set"+StringUtils.firstCharacterUpperCase(lcol)+"( r.getTimestamp(\""+col+"\") );";
      else if (colType.compareTo("Time") == 0)
        get = currentObjectName+".set"+StringUtils.firstCharacterUpperCase(lcol)+"( r.getTime(\""+col+"\") );";
      else if (colType.compareTo("char") == 0)
        get = currentObjectName+".set"+StringUtils.firstCharacterUpperCase(lcol)+"( r.getString(\""+col+"\") ).charAt(0);";
      else if (colType.compareTo("byte[]") == 0)
        get = currentObjectName+".set"+StringUtils.firstCharacterUpperCase(lcol)+"( r.getBytes(\""+col+"\") );";
      else
      {
        System.out.println("Warning! Unknown type : "+colType+
                           " in writeGetFromResultSet");
      }

      gets.addElement(get);
    }

    outPrintln("/**");
    outPrintln(" * Updates the object from a retrieved ResultSet.");
    outPrintln(" */");
    outPrintln("private " + _currentClassName + " getFromResultSet (ResultSet r) \n  throws SQLException");
    outPrintln("{");

    outPrintln("  " + _currentClassName + " " + currentObjectName + " = new " + _currentClassName + "();");

    for (Enumeration e=gets.elements(); e.hasMoreElements();)
      outPrintln("  "+(String)e.nextElement());

    outPrintln("  return " + currentObjectName + ";");
    outPrintln("}");
    outPrintln("");
  }

  /**
    * Writes out the insert function.
    */
  void writeInsert(String tableName) throws IOException
  {
    outPrintln( DataObjectFactory.writeInsert(tableName,_currentClassName,_currentObjectName,_colData,_currentDomainObject,_desiredColumns) );
  }

  /**
    * Writes out the update function.
    */
  void writeUpdate(String tableName,
                   Vector params,
                   String methodName) throws IOException
  {
    System.err.println( "  Entered writeUpdate" );
    ColumnData cd = null;
    StringBuffer columnss = new StringBuffer("( ");
    StringBuffer values = new StringBuffer("");
    StringBuffer questionMarks = new StringBuffer("");
    StringBuffer setStatements = new StringBuffer("");
    String line = "";
    String preparedStatementName = "doUpdate";

    outPrintln("/**");
    outPrintln(" * Updates the current object values into the database.");
    outPrintln(" */");
    outPrintln("public void update (Connection connection, " + _currentClassName + " " + _currentObjectName + ")" );
    outPrintln("throws SQLException");
    outPrintln("{");
    outPrintln( "  PreparedStatement " + preparedStatementName + " = null;" );
    outPrintln( "  try" );
    outPrintln( "  {" );
    outPrintln( "    // get connection here if needed" );
    outPrintln( "    " + preparedStatementName + " = connection.prepareStatement(" );
    outPrintln( "    \"UPDATE " + tableName + " SET \"" );

    // work out the update query string
    int count = 0;
    for (Enumeration e=_colData.elements(); e.hasMoreElements(); )
    {
      cd = (ColumnData)e.nextElement();
      if ( _desiredColumns.contains( cd.getName() ) )
      {
        count++;

        // convert names with underscores to camel-naming as part of this process
        values.append(StringUtils.sqlQuote(_currentObjectName + "." + StringUtils.convertUnderscoreNameToUpperCase(cd.getName().toLowerCase()),cd.getJavaType()));

        columnss.append(cd.getName());
        String currentSetStatement = "  "
                                   + preparedStatementName + ".set"
                                   + StringUtils.firstCharacterUpperCase(cd.getJavaType())
                                   + "(" + count + ","
                                   + _currentObjectName + "."
                                   + ((Method)_currentDomainObject.getMethods.get(count-1)).methodName + "()"
                                   + ");";
        outPrint( "        + \"" + cd.getName() + "=?" );

        if ( e.hasMoreElements() )
        {
          outPrint( ",\"\n" );
          values.append("+\",\"+");
          columnss.append(",");
          questionMarks.append("?,");
          setStatements.append( currentSetStatement +  "\n" );
        }
        else
        {
          outPrintln( "\"" );
          String whereClause = "WHERE " + cd.getName() + "=?\");";
          outPrintln( "    + \"" + whereClause );
          values.append("+\" )\";");
          columnss.append(" )");
          questionMarks.append("?");
          setStatements.append( "  " + currentSetStatement );
        }
      }
    }

    // now need to set the value of each ?
    outPrintln( setStatements.toString() );

    // print the last "update.set_()" method for the 'where' clause
    String getMethodForWhereClause = _currentObjectName+".get"+ StringUtils.convertUnderscoreNameToUpperCase(StringUtils.makeFirstCharUpperCase(cd.getName().toLowerCase())+"()");

    /** @todo Set the method type properly for the WHERE clause below. */
    outPrintln( "");
    outPrintln( "  INTENTIONAL ERROR HERE -> NOTE THAT THIS NEXT METHOD TYPE IS PROBABLY WRONG -- JUST SETTING IT TO int FOR NOW!!!  ");
    outPrintln( "  " + preparedStatementName + ".setInt(" + ++count + "," + getMethodForWhereClause + ");" );

    outPrintln( "  " + preparedStatementName + ".executeUpdate();" );

    outPrintln( "  }" );
    outPrintln( "  catch (SQLException se)" );
    outPrintln( "  {" );
    outPrintln( "    // log exception if desired" );
    outPrintln( "    throw se;" );
    outPrintln( "  }" );
    outPrintln( "  finally" );
    outPrintln( "  {" );
    outPrintln( "    if ( " + preparedStatementName + " != null )" );
    outPrintln( "    {" );
    outPrintln( "      " + preparedStatementName + ".close();" );
    outPrintln( "    }" );
    outPrintln( "  }" );


    outPrintln("}");
    outPrintln("");
  }


  /**
    * Writes out the update function.
    */
  void writeUpdate2(String tableName,
                   Vector params,
                   String methodName) throws IOException
  {
    ColumnData cd = null;
    String line = "";

    // work out the "where" part of the update first..
    //
    String where = createWhereClause(params,false);

    outPrintln("/**");
    outPrintln(" * Updates the current object values into the database.");
    outPrintln(" */");
    outPrintln("public boolean "+methodName+"(Connection conn, " + _currentClassName + " " + _currentObjectName + ") \n  throws SQLException");
    outPrintln("{");
    outPrintln("  boolean TGstatus = false;");
    //outPrintln("  if (connect())");
    //outPrintln("  {");

    // work out the query string
    outPrintln("  String query=\"UPDATE "+tableName+" SET \"+");

    for (Enumeration e=_colData.elements(); e.hasMoreElements(); )
    {
      cd = (ColumnData)e.nextElement();
      line = "               \""+cd.getName()+"=\"+"+StringUtils.sqlQuote(_currentObjectName+".get"+ StringUtils.makeFirstCharUpperCase(cd.getName().toLowerCase())+"()",cd.getJavaType());
      //line = "               \""+cd.getName()+"=\"+" + StringUtils.sqlQuote(_currentObjectName + "." + cd.getName().toLowerCase(),cd.getJavaType());

      if (e.hasMoreElements())
        line += "+\",\"+";
      else
        line += where+";";

      outPrintln(line);
    }

    outPrintln("  TGstatus = doUpdateQuery(conn,query);");
    outPrintln("  return TGstatus;");
    outPrintln("}");
    outPrintln("");
  }

  /**
    * Writes out the count function.
    * If "key" is not null then a search based on the key is added.
    * If "withLike" then the search is based on a "like" not an exact match.
    */
  void writeCount (String tableName,
                   Vector params,
                   boolean withLike,
                   String methodName) throws IOException
  {
    ColumnData cd = null;
    String line = "";
    String where = "";

    where = createWhereClause(params,withLike);

    outPrintln("/**");
    outPrintln(" * Counts the number of entries for this table in the database.");
    outPrintln(" */");
//    outPrintln("public int "+methodName+"() throws SQLException");
    outPrintln(createMethodLine(methodName,params,"int"));
    outPrintln("{");
    outPrintln("  int count = -1;");
    outPrintln("  String query = \"SELECT COUNT(*) FROM " + tableName + "\" " + where + ";");
    outPrintln("  ResultSet r = executeQuery(conn,query);");
    outPrintln("  if ( r.next() )");
    outPrintln("  {");
    outPrintln("    count = r.getInt(1);");
    outPrintln("  }");
    outPrintln("  return count;");
    outPrintln("}");
    outPrintln("");
  }

  /**
    * Creates a where clause.
    * If params are null then an empty string is returned.
    * If "withLike" is true then a "like" clause is used.
    * e.g. "SELECT COUNT(*) from languages".
    *      "SELECT COUNT(*) from languages where lang='english'"
    *      "SELECT COUNT(*) from languages where lang='english'"
    */
  private String createWhereClause (Vector params, boolean withLike)
  {
    String where = "";

    // if we have keys passed in then we add a "where" to the count
    // e.g. se
    //
    if (params != null)
    {
      // work out the "where" part of the update first..
      //
      where="\n                   + \" WHERE ";
      String key="";
      String keyType="";
      for (Enumeration e=params.elements(); e.hasMoreElements(); )
      {
        key = (String)e.nextElement();
        keyType = getColType(key);

        // only allow 'like' clause on String types
        if ((withLike) && (keyType.equals("String")))
        {
          //where += key+" like \" + " + StringUtils.sqlQuote(_currentObjectName + ".get" + makeFirstCharUpperCase(key.toLowerCase())+"()",keyType);
          where += key+" like \" + " + StringUtils.sqlQuote(key.toLowerCase(),keyType);
        }
        else
        {
          //where += key+"=\" + " + StringUtils.sqlQuote(_currentObjectName + ".get" + makeFirstCharUpperCase(key.toLowerCase())+"()",keyType);
          where += key+" = \" + " + StringUtils.sqlQuote(key.toLowerCase(),keyType);
        }

        if (e.hasMoreElements())
        {
          where = where + " + \" and ";
        }
      }
    }

    return where;
  }

  /**
    * Creates a the parameter block for a method.
    */
  public String createMethodLine (String methodName, Vector params, String returnType)
  {
    String paramString = "public "
                       + returnType
                       + " "
                       + methodName
                       + " (Connection conn";
    String key = "";
    String keyType = "";

    if (params != null)
    {
      // work out the query string and the method parameters.
      //
      for (Enumeration e=params.elements(); e.hasMoreElements(); )
      {
        key = (String)e.nextElement();
        keyType = getColType(key);
        paramString += ", ";
        paramString = paramString + keyType+" "+key.toLowerCase();

        if (e.hasMoreElements())
        {
          //paramString = ", " + paramString;
          //paramString = paramString + ",";
        }
        else
        {
          paramString = paramString + ") \n  throws SQLException";
        }
      }
   }
   else
   {
     paramString = paramString + ") \n  throws SQLException";
   }

    return paramString;
  }

  /**
    * Retrieves the primary keys for a particular table.
    */
  public boolean getTableKeys(String tableName)
  {
    Debug.println( "getTableKeys() called" );
    boolean status = false;
    _keyData = new Vector();

    try
    {
      String key = null;

      ResultSet rs = _metaData.getPrimaryKeys(_catalog,_schema,tableName);
      while ( rs.next() )
      {
        key = rs.getString(4);
        _keyData.addElement(key);
        status = true;  // yes we have at least one key
      }
      rs.close();
    }
    catch (SQLException e)
    {
      System.err.println("Error getting keys for " + tableName);
      System.err.println(e);
    }

    return status;
  }


/**
  *
  * write the methods associated to this Exported Foreign key entry.
  */
  protected void writeExportedMethods( String tableName, FKDefinition def ) throws  IOException
  {
    outPrintln("/**");
    outPrintln("  * Get all related  "+ def.getFKTableName()+" which have same "+def.getFKColList());
    outPrintln("  */");

    outPrintln(createMethodLine("getRelated"+def.getFKTableName()+"__"+ def.getFKName(),null,"List"));
    outPrintln("{" );
    outPrintln("\t"+ StringUtils.firstCharacterUpperCase(def.getFKTableName()) + " x;");
    outPrintln("\t"+ StringUtils.firstCharacterUpperCase(def.getFKTableName()) +" x = new "+StringUtils.firstCharacterUpperCase(def.getFKTableName())+"();");
    outPrintln("\treturn x.retrieveBy"+def.getFKTableName() +"__" +
          def.getFKName()+"("+def.getPKColList()+");");
    outPrintln( "}");
  }

/**
  *
  * write the methods associated to this Foreign key entry.
  */
  protected void writeImportedMethods( String tableName, FKDefinition def ) throws IOException
  {
    outPrintln("/**");
    outPrintln("  * Imported " + tableName + " PK:"+ def.getPKTableName() + " FK:" + def.getFKTableName());
    outPrintln("  */");
    outPrintln(createMethodLine("get"+StringUtils.firstCharacterUpperCase(def.getPKTableName())+
        "By"+def.getFKName(),null, StringUtils.firstCharacterUpperCase(def.getPKTableName()) ));
    outPrintln("{" );
    outPrintln("\t"+ StringUtils.firstCharacterUpperCase(def.getPKTableName()) +" x = new "+StringUtils.firstCharacterUpperCase(def.getPKTableName())+"();");
    outPrintln("\tx.retrieveByKey("+def.getFKColList()+");");
    outPrintln("\treturn x;");
    outPrintln( "}");
    writeRetrieveAll( tableName, def.getFKFields(), false, "retrieveBy"+
                def.getFKTableName() +"__" +def.getFKName(),false);
  }

  /**
    * Retrieves the type of a particular column name.
    * Cannot use Hashtables to store columns as it screws
    * up the ordering, so we have to do a crap search.
    * (and yes I know it could be better - it's good enuf).
    */
  public String getColType(String key)
  {
    String type = "unknown";
    ColumnData tmp;

    for (Enumeration e=_colData.elements(); e.hasMoreElements(); )
    {
      tmp = (ColumnData)e.nextElement();
      if (tmp.getName().compareTo(key) == 0)
        type = tmp.getJavaType();
    }

    return type;
  }

  /**
    * Wrapper for text output.
    */
  public void outPrintln(String text) throws IOException
  {
    _currentOutput.write(LEADING_SPACE+text+"\n");
  }

  /**
    * Wrapper for text output. Don't print a newline character.
    */
  public void outPrint(String text) throws IOException
  {
    _currentOutput.write(text);
  }

  /**
    * Wrapper for text output.
    * So we can change where the output goes easily!
    */
  public void outNoLeadingSpace(String text) throws IOException
  {
    _currentOutput.write(text+"\n");
  }

  private void log (String s)
  {
    System.out.println(s);
  }


  void set_doForeignKeys(String foreignKeysString)
  {
    _doForeignKeys = false;
    if (foreignKeysString != null)
    {
      if (foreignKeysString.toLowerCase().compareTo("no") == 0)
      {
        Debug.println( "foreign_keys was defined, assigned to [false] (do not use foreign keys)" );
        _doForeignKeys = false;
      }
      else
      {
        Debug.println( "foreign_keys was defined, assigned to [true] (use foreign keys)" );
        _doForeignKeys = true;
      }
    }
    else
    {
      Debug.println( "foreign_keys not defined, defaulting to [false]" );
    }
  }

  private void createDataObjectDeleteMethod(final String tableName)
  throws IOException
  {
    if (_keys)
    {
      getTableKeys(tableName); // updates the _keyData variable
      if ( _keyData.size() > 0 )
      {
        writeDelete(tableName,_keyData,"deleteByKey");
      }
    }
  }

  private void createDataObjectSelectMethod(final String tableName)
  throws IOException
  {
    if (_keys)
    {
      getTableKeys(tableName); // updates the _keyData variable
      if ( _keyData.size() > 0 )
      {
        writeSelectMethods(tableName);
      }
    }
  }

  private void createDataObjectUpdateMethod(final String tableName)
  throws SQLException,IOException
  {
    // saw where writeUpdate() was not even using the second param, so I took
    // it out of the call for now (2002-04-29)
    writeUpdate(tableName,null,"updateByIndex");

/*    // bugs in postgres driver - skip this
    if ( getDatabaseType(_driver).equals(POSTGRES) )
    {
      System.err.println( "___ type == POSTGRES" );
      if (_doIndexes)
      {
        _indexData = new Vector();
        Table.getTableIndexes(tableName, _metaData, _catalog, _schema, _indexData);
        if (_indexData.size() > 0)
        {
          System.err.println("___ calling writeUpdate");
          writeUpdate(tableName,_indexData,"updateByIndex");
        }
        else
        {
          Debug.println( "  _indexData.size <= 0" );
        }
      }
    }
*/
  }

  private void writeDataObjectToCurrentOutput(String tableName)
  throws SQLException,IOException
  {
    boolean isDomainClass = false;
    _currentOutput = new BufferedWriter(_dataObjectClass);
    /** @todo -- catalog and schema are always null here */
    //_colData = Table.getColumnData(tableName,_metaData,_catalog,_schema,types_are_strings);
    _colData = Table.getColumnData(tableName,_metaData,null,null,types_are_strings);
    writeDataObjectRepresentingTable(tableName,_currentClassName,isDomainClass);
    _currentOutput.close();
  }

  private void writeJavaBeanToCurrentOutput(String tableName)
  throws SQLException,IOException
  {
    boolean isDomainClass = true;
    _currentOutput = new BufferedWriter(_modelClass);
    // create a new domainObject to represent the current table
    _currentDomainObject = new DomainObject(_packageName, _currentClassName);
    addToDomainObjectList (_currentDomainObject);
    _colData = Table.getColumnData(tableName,_metaData,_catalog,_schema,types_are_strings);
    // (note: this method secretly writes to _currentOutput stream)
    writeJavaBeanRepresentingTable(tableName,_currentClassName,isDomainClass);
    _currentOutput.close();
  }

  private void writeJavaBeanSetMethod(ColumnData cd, String methodReturnType, String newName, String varName)
  throws IOException
  {
    outPrintln( JavaBeanFactory.writeJavaBeanSetMethod(cd,methodReturnType,newName,varName) );
  }

  void writeJavaBeanGetMethod(ColumnData cd, String newName, String varName)
  throws IOException
  {
    outPrintln( JavaBeanFactory.writeJavaBeanGetMethod(cd,newName,varName) );
  }
}
