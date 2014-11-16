package com.devdaily.dbgrinder.model;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import com.devdaily.dbgrinder.utility.StringUtils;

public class DataObjectFactory
{

  /**
    * Writes out the insert function.
    */
  static String writeInsert(String tableName,
                            String _currentClassName,
                            String _currentObjectName,
                            Vector _colData,
                            DomainObject _currentDomainObject,
                            List _desiredColumns)
  throws IOException
  {
    ColumnData cd = null;
    StringBuffer columnss = new StringBuffer("( ");
    StringBuffer values = new StringBuffer("");
    StringBuffer questionMarks = new StringBuffer("");
    StringBuffer setStatements = new StringBuffer("");
    String line = "";
    String preparedStatementName = "doInsert";
    String s = "";

    s += ("/**");
    s += JavaBeanFactory.getIndentation() + " * Inserts the current object values into the database.\n";
    s += JavaBeanFactory.getIndentation() + " */\n";
    s += JavaBeanFactory.getIndentation() + "public void insert (Connection conn, " + _currentClassName + " " + _currentObjectName + ")\n";
    s += JavaBeanFactory.getIndentation() + "throws SQLException\n";
    s += JavaBeanFactory.getIndentation() + "{\n";


    // work out the insert query string
    int count = 0;
    for (Enumeration e=_colData.elements(); e.hasMoreElements(); )
    {
      cd = (ColumnData)e.nextElement();
      // only write out Java code for the database table columns the user has selected
      if ( _desiredColumns.contains( cd.getName() ) )
      {
        count++;

        // convert names with underscores to camel-naming as part of this process
        values.append(StringUtils.sqlQuote(_currentObjectName + "." + StringUtils.convertUnderscoreNameToUpperCase(cd.getName().toLowerCase()),cd.getJavaType()));

        columnss.append(cd.getName());
        String currentSetStatement = preparedStatementName + ".set"
                                   + StringUtils.firstCharacterUpperCase(cd.getJavaType())
                                   + "(" + count + ","
                                   + _currentObjectName + "."
                                   + ((Method)_currentDomainObject.getMethods.get(count-1)).methodName + "()"
                                   + ");";
        if ( e.hasMoreElements() )
        {
          values.append("+\",\"+");
          columnss.append(",");
          questionMarks.append("?,");
          setStatements.append( JavaBeanFactory.getIndentation(3) + currentSetStatement + "\n" );
        }
        else
        {
          values.append("+\" )\";");
          columnss.append(" )");
          questionMarks.append("?");
          setStatements.append( JavaBeanFactory.getIndentation(3) + currentSetStatement );
        }
      }
    }

    // build PreparedStatement
    s += JavaBeanFactory.getIndentation(2) +  "PreparedStatement " + preparedStatementName + " = null;\n";
    s += JavaBeanFactory.getIndentation(2) +  "try\n";
    s += JavaBeanFactory.getIndentation(2) +  "{\n";
    s += JavaBeanFactory.getIndentation(3) +  "" + preparedStatementName + " = conn.prepareStatement(\n";
    s += JavaBeanFactory.getIndentation(3) +  "\"INSERT INTO " + tableName + columnss + "\" +\n";
    s += JavaBeanFactory.getIndentation(3) +  "\" VALUES (" + questionMarks + ")\" );\n";

    // now need to set the value of each ?
    s += JavaBeanFactory.getIndentation() +  setStatements.toString() + "\n";
    s += JavaBeanFactory.getIndentation(3) +  preparedStatementName + ".executeUpdate();\n";
    s += JavaBeanFactory.getIndentation(2) +  "}\n";
    s += JavaBeanFactory.getIndentation(2) +  "catch (SQLException se)\n";
    s += JavaBeanFactory.getIndentation(2) +  "{\n";
    s += JavaBeanFactory.getIndentation(3) +  "// log exception if desired\n";
    s += JavaBeanFactory.getIndentation(3) +  "throw se;\n";
    s += JavaBeanFactory.getIndentation(2) +  "}\n";
    s += JavaBeanFactory.getIndentation(2) +  "finally\n";
    s += JavaBeanFactory.getIndentation(2) +  "{\n";
    s += JavaBeanFactory.getIndentation(3) +  "if ( " + preparedStatementName + " != null )\n";
    s += JavaBeanFactory.getIndentation(3) +  "{\n";
    s += JavaBeanFactory.getIndentation(3) +  "" + preparedStatementName + ".close();\n";
    s += JavaBeanFactory.getIndentation(3) +  "}\n";
    s += JavaBeanFactory.getIndentation(2) +  "}\n";
    s += JavaBeanFactory.getIndentation() + "}\n";
    s += JavaBeanFactory.getIndentation() + "\n";
    return s;
  }

}