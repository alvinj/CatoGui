package com.devdaily.dbgrinder.model;

// TODO delete this class

import java.util.*;
import java.io.*;
import com.devdaily.dbgrinder.utility.StringUtils;
import com.devdaily.utils.Debug;

/**
 * Generates JSPs, Servlets, for a web app.
 * Works by reading in a bunch of JSP template files, parsing them, and creating output files
 * based on their "tags".
 *
 * Tags currently supported:
 *
 *  **CLASS_NAME**
 *  **CLASS_INSTANCE_NAME**
 *  **FIELD_NAME**
 *  **FIELD_DATA_TYPE**
 *  **DOMAIN_OBJECT**
 *  **DB_COLUMN_NAME**
 *  **FOREACH_FIELD**
 *  **FIELD_LABEL**                  (a guess at the field label for JSPs)
 *  **FIELD_TYPE_FIRST_CHAR_UPPER**  (actually returns Int instead of int, Boolean instead of boolean)
 *  **END_FOREACH_FIELD**
 */
public class WappGenerator
{

  // some resources used throughout this class
  private static final String RESOURCES_FOLDER_NAME      = "Db2WappResources";
  private static final String WEB_XML_TEMPLATE_FILE_NAME = "web.xml.template";

  // this program will build a list of "domain objects" from the db tables it reads
  private Vector _domainObjects;

  private BufferedWriter _outputWriter;
  private String _prefix;
  private String _generatedJspFilesDir;
  private String _jspTemplateDir;
  private String _workingDir;
  private String _packageName;

  // a few folders we'll be creating that we'll need to know in a couple of places
  private File _webInf;   // refers to "WEB-INF"
  private File _util;     // refers to "WEB-INF/classes/com/devdaily/util"
  private File _web;      // refers to "WEB-INF/classes/com/devdaily/web"
  private File _modelFolder;
  private File _containerFolder;
  private File _controllerFolder;


  private WappGenerator()
  {
  }

  public WappGenerator(Vector domainObjects,
                       String prefix,
                       String jspTemplateDir,
                       String jspOutputDir)
  {
    _domainObjects = domainObjects;
    _prefix = prefix;
    _generatedJspFilesDir = jspOutputDir;
    _jspTemplateDir = jspTemplateDir;
    _workingDir = _generatedJspFilesDir + File.pathSeparator + "WEB-INF";

    //_packageName = packageName;
    createInitialOutputFolders();
    copyResourceFilesToFolders();
  }


  /**
   * Generate the output folders that we'll be writing to during the generation process.
   */
  private void createInitialOutputFolders ()
  {
    // define the folders
    File generatedStuff = new File(_generatedJspFilesDir);
    _webInf   = new File( _generatedJspFilesDir,  "WEB-INF" );
    File classes  = new File( _webInf.toString(), "classes" );
    File com      = new File( classes.toString(), "com" );
    File devdaily = new File( com.toString(),     "devdaily" );
    _util     = new File( devdaily.toString(),    "util" );
    _web      = new File( devdaily.toString(),    "web" );

    // create the folders
    generatedStuff.mkdir();
    _webInf.mkdir();
    classes.mkdir();
    com.mkdir();
    devdaily.mkdir();
    _util.mkdir();
    _web.mkdir();
  }


  /**
   * Copy our resource files to the proper output folders.
   */
  private void copyResourceFilesToFolders ()
  {
    String utilFolder = _workingDir + File.separator + RESOURCES_FOLDER_NAME + "/classes/com/devdaily/util";
    String webFolder  = _workingDir + File.separator + RESOURCES_FOLDER_NAME + "/classes/com/devdaily/web";
    File requestParametersFile = new File( webFolder + "/RequestParameter.java" );
    File ddContainerFile = new File( utilFolder + "/DDContainer.java" );

    // try to copy the files to their new location
    File newRequestParametersFile = new File( _web + "/RequestParameter.java" );
    File newDDContainerFile = new File ( _util + "/DDContainer.java" );
    try
    {
      copyFile( requestParametersFile, newRequestParametersFile );
      copyFile( ddContainerFile,       newDDContainerFile );
    }
    catch (Exception e)
    {
      System.err.println( "Error occurred trying to copy " + requestParametersFile );
      System.err.println( "  Message: " + e.getMessage() );
    }
    finally
    {
      System.err.println( "leaving copyResourceFilesToFolders" );
    }
  }


  /**
   * Copy from the fromFile to the toFile.
   */
  private void copyFile (File fromFile, File toFile)
  throws IOException, FileNotFoundException
  {
    BufferedReader br = new BufferedReader( new FileReader( fromFile ) );
    BufferedWriter bw = new BufferedWriter( new FileWriter( toFile ) );
    String line = null;
    while ( (line = br.readLine()) != null )
    {
      bw.write( line + "\n" );
    }
    br.close();
    bw.close();
  }


  /**
   * for each DomainObject, print the corresponding user interface components
   */
/*
  public void printUIComponents()
  {
    parseJspTemplate( "_Add.jsp",            "Add.jsp" );
    parseJspTemplate( "_Edit.jsp",           "Edit.jsp" );
    parseJspTemplate( "_PrintView.jsp",      "PrintView.jsp" );
    parseJspTemplate( "_Delete.jsp",         "Delete.jsp" );
    parseJspTemplate( "_ConfirmDelete.jsp",  "ConfirmDelete.jsp" );
    parseJspTemplate( "_List.jsp",           "List.jsp" );
    parseJspTemplate( "_Search.jsp",         "Search.jsp" );
    parseJspTemplate( "_SearchResults.jsp",  "SearchResults.jsp" );
  }
*/

  /**
   * For each file in the "JSP" template directory that begins with an
   * underscore, write an output file of a similar name, where the
   * _ character is replaced by the name of the corresponding
   * JavaBean.
   */
  public void printUIComponents()
  {
    String[] listOfFilesToParse = getListOfJSPTemplatesToParse(_jspTemplateDir);
    for ( int i=0; i<listOfFilesToParse.length; i++ )
    {
      String templateName = listOfFilesToParse[i];
      String outputFileSuffix = removeLeadingUnderscore(templateName);
      parseJspTemplate( templateName, outputFileSuffix );
    }
  }

  String[] getListOfJSPTemplatesToParse(String aDirectory)
  {
    class OnlyUnderscoreFilter implements FilenameFilter
    {
      public boolean accept(File dir, String s)
      {
        if ( s.startsWith("_") ) return true;
        return false;
      }
    }
    return new java.io.File(aDirectory).list( new OnlyUnderscoreFilter() );
  }

  String removeLeadingUnderscore(String s)
  {
    if ( s.charAt(0) == '_' )
    {
      return s.substring(1,s.length());
    }
    return s;
  }


  /**
   * for each DomainObject, print a Controller class.
   */
  public void printControllers(File controllerFolder)
  {
    _controllerFolder = controllerFolder;

    // first parse/generate _Controller.java for each domain object
    Enumeration doEnumeration = _domainObjects.elements();
    while ( doEnumeration.hasMoreElements() )
    {
      DomainObject domainObject = (DomainObject)doEnumeration.nextElement();
      String className = domainObject.className;
      String inputFileName  = _workingDir + File.separator + RESOURCES_FOLDER_NAME + File.separator + "_Controller.java";
      String outputFileName = _controllerFolder + File.separator + className + "Controller.java";
      parseControllerTemplate(inputFileName, outputFileName, domainObject);
    }

    // this is a horrendous kludge, but I am incredibly tired.
    // note that aDomainObject is just used as a placeholder when the two parseControllerTemplate methods
    //   are called below.
    Enumeration anEnumeration = _domainObjects.elements();
    DomainObject aDomainObject = (DomainObject)anEnumeration.nextElement();

    // now copy/parse the generic Controller.java file
    String inputFileName  = _workingDir + File.separator + RESOURCES_FOLDER_NAME + File.separator + "Controller.java";
    String outputFileName = _controllerFolder + File.separator + "Controller.java";
    parseControllerTemplate(inputFileName, outputFileName, aDomainObject);

    // now copy/parse the generic ControllerUtil.java file
    inputFileName  = _workingDir + File.separator + RESOURCES_FOLDER_NAME + File.separator + "ControllerUtil.java";
    outputFileName = _controllerFolder + File.separator + "ControllerUtil.java";
    parseControllerTemplate(inputFileName, outputFileName, aDomainObject);
  }

  /**
   * Read the standard Controller template and generate a corresponding Controller
   * class for each domain object.
   */
  private void parseControllerTemplate (String inputFileName, String outputFileName, DomainObject domainObject)
  {
    BufferedReader controllerTemplate = null;
    try
    {
      controllerTemplate = new BufferedReader(new FileReader(inputFileName));
    }
    catch (IOException templateFileException)
    {
      // presumably this file doesn't exist; return quietly and go on to next
      return;
    }

    try
    {
      _outputWriter = new BufferedWriter(new FileWriter(outputFileName));
      String line = null;
      while ( (line=controllerTemplate.readLine()) != null )
      {
        parseJspRecords( domainObject, controllerTemplate, _outputWriter, line );
      }
    }
    catch (IOException outputFileException)
    {
      System.err.println( "Error: Something wicked happened trying to create/write to output file: "
                          + outputFileException.getMessage() );
    }

    try
    {
      controllerTemplate.close();
      _outputWriter.close();
    }
    catch (IOException ioe)
    {}
  }


  /**
   * Create the web.xml file for this app.
   */
  public void printWebXmlFile()
  {
    BufferedReader webXmlTemplate = null;
    String inputFileName  = _workingDir + File.separator + RESOURCES_FOLDER_NAME + File.separator + WEB_XML_TEMPLATE_FILE_NAME;
    String outputFileName = _webInf.toString() + File.separator + "web.xml";
    try
    {
      webXmlTemplate = new BufferedReader(new FileReader(inputFileName));
    }
    catch (IOException templateFileException)
    {
      System.err.println( "IOException occurred trying to read " + inputFileName );
      return;
    }

    try
    {
      _outputWriter = new BufferedWriter(new FileWriter(outputFileName));
      String line = null;
      while ( (line=webXmlTemplate.readLine()) != null )
      {
        parseWebXmlRecords( webXmlTemplate, _outputWriter, line );
      }
    }
    catch (IOException outputFileException)
    {
      System.err.println( "Error: Something wicked happened trying to create/write to output file: "
                          + outputFileException.getMessage() );
    }

    try
    {
      webXmlTemplate.close();
      _outputWriter.close();
    }
    catch (IOException ioe)
    {}
  }


  private void parseWebXmlRecords(BufferedReader in, BufferedWriter out, String line)
  {
    line = parseForeachClassBlock(in, line);
    //line = searchAndReplaceTokens(line, domainObject);
    writeLine( out, line );
  }


  /**
   * Parse a **FOREACH_CLASS** block.
   */
  private String parseForeachClassBlock (BufferedReader in, String line)
  {
    String parsedBlock = line;
    try
    {
      if ( line.indexOf("**FOREACH_CLASS**") >= 0 )
      {
        String foreachBlock = new String();
        parsedBlock = foreachBlock;
        String newLine = null;
        while ( (newLine = in.readLine()) != null  )
        {
          if ( newLine.indexOf("**END_FOREACH_CLASS**") >= 0 )
          {
            Enumeration doEnumeration = _domainObjects.elements();
            while ( doEnumeration.hasMoreElements() )
            {
              DomainObject currentDomainObject = (DomainObject)doEnumeration.nextElement();
              String tempString = new String(foreachBlock);
              // expect to find these tags
              tempString = searchAndReplaceTokens( tempString, currentDomainObject );
              //tempString = replaceTokenIfFound ( line, "**CLASS_NAME**", domainObject.className );
              parsedBlock += tempString;
            }
            return parsedBlock;
          }
          else
          {
            foreachBlock += newLine + "\n";
          }
        }
      }
    }
    catch (IOException ioe)
    {
      System.err.println( "ouch, caught an IOException: " + ioe.getMessage() );
    }
    return parsedBlock;
  }


  /**
   * Parse the JSP template file given by the jspTemplateSuffix.
   * Convert the template into a JSP.
   */
  private void parseJspTemplate (String jspTemplateSuffix, String jspOutputSuffix)
  {
    BufferedReader jspTemplate = null;
    Enumeration doEnumeration = _domainObjects.elements();
    while ( doEnumeration.hasMoreElements() )
    {
      DomainObject domainObject = (DomainObject)doEnumeration.nextElement();
      String className = domainObject.className;
      String inputFileName  = getInputFilename ( domainObject, _jspTemplateDir,  jspTemplateSuffix );
      String outputFileName = getOutputFilename ( domainObject, _jspTemplateDir, jspOutputSuffix );
      try
      {
        jspTemplate = new BufferedReader(new FileReader(inputFileName));
      }
      catch (IOException templateFileException)
      {
        System.err.println( "IOException in WappGenerator: " + templateFileException.getMessage() );
        // presumably this file doesn't exist; return quietly and go on to next
        return;
      }

      try
      {
        _outputWriter = new BufferedWriter(new FileWriter(outputFileName));
        String line = null;
        while ( (line=jspTemplate.readLine()) != null )
        {
          parseJspRecords( domainObject, jspTemplate, _outputWriter, line );
        }
      }
      catch (IOException outputFileException)
      {
        System.err.println( "Error: Something wicked happened trying to create/write to output file: "
                            + outputFileException.getMessage() );
      }

      try
      {
        jspTemplate.close();
        _outputWriter.close();
      }
      catch (IOException ioe)
      {}
    }
  }

  private void parseJspRecords(DomainObject domainObject, BufferedReader in, BufferedWriter out, String line)
  {
    line = parseForeachFieldBlock(in, line, domainObject);
    line = searchAndReplaceTokens( line, domainObject );
    writeLine( out, line );
  }


  /**
   * A special method to parse a "foreach" block within a JSP and replace the tags
   * appropriately.
   */
  private String parseForeachFieldBlock (BufferedReader in, String line, DomainObject domainObject)
  {
    String parsedBlock = line;
    try
    {
      if ( line.indexOf("**FOREACH_FIELD**") >= 0 )
      {
        System.err.println("**FOREACH_FIELD** ....................");
        String foreachBlock = new String();
        parsedBlock = foreachBlock;
        String newLine = null;
        while ( (newLine = in.readLine()) != null  )
        {
          if ( newLine.indexOf("**END_FOREACH_FIELD**") >= 0 )
          {
            Vector vectorOfFields = domainObject.fields;
            Enumeration e = vectorOfFields.elements();
            while ( e.hasMoreElements() )
            {
              String tempString = new String(foreachBlock);
              //Field f = (Field)e.nextElement();
              // expect to find these tags
              
              
              // THIS CODE IS OLD AND DEAD, DONT USE IT
//              // USE KSTemplateGenerator INSTEAD
//              
//              
//              //tempString = StringUtils.replaceAll( tempString, "**FIELD_LABEL**", StringUtils.firstCharacterUpperCase(f.fieldName) );
//              tempString = StringUtils.replaceAll( tempString, "**FIELD_LABEL**", StringUtils.firstCharacterUpperCase(f.fieldName) );
//              tempString = StringUtils.replaceAll( tempString, "**FIELD_NAME**", f.fieldName );
//              tempString = StringUtils.replaceAll( tempString, "**FIELD_DATA_TYPE**", f.type );
//              tempString = StringUtils.replaceAll( tempString, "**DB_COLUMN_NAME**", StringUtils.convertCamelCaseToUnderscore(f.fieldName) );
//              // this next line just used when creating a Controller (good to know for refactoring!)
//              tempString = StringUtils.replaceAll( tempString, "**FIELD_TYPE_FIRST_CHAR_UPPER**", StringUtils.firstCharacterUpperCase(f.type) );
//              // these may also occur in this area
//              tempString = searchAndReplaceTokens( tempString, domainObject );
              parsedBlock += tempString;
            }
            return parsedBlock;
          }
          else
          {
            foreachBlock += newLine + "\n";
          }
        }
      }
    }
    catch (IOException ioe)
    {
      System.err.println( "ouch, caught an IOException: " + ioe.getMessage() );
    }
    return parsedBlock;
  }


  /**
   * Replace our pre-defined tags with the appropriate text for the current domain object.
   */
  private String searchAndReplaceTokens(String line, DomainObject domainObject)
  {
    //String newLine = replaceTokenIfFound ( line, "**DOMAIN_OBJECT**", domainObject.className );
    String newLine = replaceTokenIfFound ( line, "**CLASS_NAME**", domainObject.className );
    newLine = replaceTokenIfFound ( newLine, "**CLASS_INSTANCE_NAME**", StringUtils.firstCharacterLowerCase(domainObject.className) );
    newLine = replaceTokenIfFound ( newLine, "**CLASS_NAME_UPPER**", domainObject.className.toUpperCase() );
    newLine = replaceTokenIfFound ( newLine, "**DATA_OBJECT_NAME**", domainObject.className+"Container" );
    newLine = replaceTokenIfFound ( newLine, "**PACKAGE_NAME**", domainObject.packageName );
    return newLine;
  }

  private String replaceTokenIfFound (String line, String token, String replacementText)
  {
    if ( line.indexOf(token) >= 0 )
    {
      line = StringUtils.replaceAll( line, token, replacementText );
    }
    return line;
  }

  private void writeLine (BufferedWriter out, String line)
  {
    try
    {
      out.write( line );
      out.write( "\n" );
    }
    catch (IOException ioe)
    {
      System.err.println( "Something wicked happened writing to an output file: " + ioe.getMessage() );
    }
  }

  private String getInputFilename(DomainObject domainObject, String jspTemplateDir, String filenameSuffix)
  {
    String className = domainObject.className;
    String fileName  = _jspTemplateDir + File.separator + filenameSuffix;
    return fileName;
  }

  private String getOutputFilename(DomainObject domainObject, String jspTemplateDir, String filenameSuffix)
  {
    String className = domainObject.className;
    String fileName = _generatedJspFilesDir + File.separator + _prefix + StringUtils.firstCharacterLowerCase(className) + filenameSuffix;
    return fileName;
  }


}