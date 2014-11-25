package com.devdaily.kickstart.controller;


// TODO delete this class

import java.util.*;
import java.io.*;
import com.devdaily.dbgrinder.utility.StringUtils;
import com.devdaily.dbgrinder.model.*;

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
 *  **FOREACH_FIELD**
 *  **FIELD_LABEL**                  (a guess at the field label for JSPs)
 *  **FIELD_TYPE_FIRST_CHAR_UPPER**  (actually returns Int instead of int, Boolean instead of boolean)
 *  **END_FOREACH_FIELD**
 */
public class KSTemplateGenerator
{

  // this program will build a list of "domain objects" from the db tables it reads
  private Vector _domainObjects;

  private KSTemplateGenerator()
  {
  }

  public KSTemplateGenerator(Vector domainObjects)
  {
    _domainObjects = domainObjects;
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
   * New method I'm working on.
   */
  public String createOutputFromTemplate (String templateFileName)
  throws IOException
  {
    BufferedReader jspTemplate = null;
    Enumeration doEnumeration = _domainObjects.elements();
    // major shortcut/kludge here
    DomainObject domainObject = (DomainObject)doEnumeration.nextElement();
    StringBuffer output = new StringBuffer();
    try
    {
      jspTemplate = new BufferedReader(new FileReader(templateFileName));
      String line = null;
      while ( (line=jspTemplate.readLine()) != null )
      {
        output.append(parseTemplateRecords(domainObject, jspTemplate, line));
        output.append("\n");
      }
    }
    catch (IOException templateFileException)
    {
      System.err.println( "IOException in WappGenerator: " + templateFileException.getMessage() );
      throw templateFileException;
    }
    finally
    {
      jspTemplate.close();
    }

    return output.toString();
  }

  private String parseTemplateRecords(DomainObject domainObject, BufferedReader in, String line)
  {
    line = parseForeachFieldBlock(in, line, domainObject);
    line = searchAndReplaceTokens(line, domainObject);
    return line;
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
//              Field f = (Field)e.nextElement();
//              // expect to find these tags
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

}