package com.alvinalexander.cato.model

import java.io.IOException
import java.util.Enumeration
import java.util.List
import java.util.Vector
import com.devdaily.dbgrinder.utility.StringUtils
import com.devdaily.dbgrinder.model.ColumnData

object DataObjectFactory {

    // TODO wrap in a Try
    // TODO this is currently named "write", but should be renamed to show that it returns a String
    def writeInsert(
            tableName: String,
            currentClassName: String,
            currentObjectName: String,
            columnData: Seq[ColumnData],
            currentDomainObject: DomainObject,
            desiredColumns: Seq[String]): String =
    {
        var cd: ColumnData = null
        var columnss: StringBuffer = new StringBuffer("( ")
        var values: StringBuffer = new StringBuffer("")
        var questionMarks: StringBuffer = new StringBuffer("")
        var setStatements: StringBuffer = new StringBuffer("")
        var line = ""
        var preparedStatementName = "doInsert"
        var s = ""
    
        s += ("/**")
        s += JavaBeanFactory.getIndentation + " * Inserts the current object values into the database.\n"
        s += JavaBeanFactory.getIndentation + " */\n"
        s += JavaBeanFactory.getIndentation + "public void insert (Connection conn, " + currentClassName + " " + currentObjectName + ")\n"
        s += JavaBeanFactory.getIndentation + "throws SQLException\n"
        s += JavaBeanFactory.getIndentation + "{\n"
    
    
        // work out the insert query string
        var count = 0
        
        // TODO fix this
        //for (Enumeration e =columnData.elements; e.hasMoreElements; )
        for (cd <- columnData) {
            // only write out Java code for the database table columns the user has selected
            if (desiredColumns.contains(cd.getName)) {
                // convert names with underscores to camel-naming as part of this process
                values.append(StringUtils.sqlQuote(currentObjectName + "." + StringUtils.convertUnderscoreNameToUpperCase(cd.getName.toLowerCase),cd.getJavaType))
                columnss.append(cd.getName)
                val javaTypeCapped = cd.getJavaType.capitalize
                val currMethodName = currentDomainObject.getMethods(count).methodName
                val currentSetStatement = s"${preparedStatementName}.set${javaTypeCapped}(${count+1},${currentObjectName}.${currMethodName})"
                // TODO this is probably wrong; may be close
                if (count < columnData.length) {
                    values.append("+\",\"+")
                    columnss.append(",")
                    questionMarks.append("?,")
                    setStatements.append(JavaBeanFactory.getIndentation(3) + currentSetStatement + "\n")
                } else {
                    values.append("+\" )\"")
                    columnss.append(" )")
                    questionMarks.append("?")
                    setStatements.append(JavaBeanFactory.getIndentation(3) + currentSetStatement)
                }
                count += 1
            }
        }
    
        // build PreparedStatement
        s += JavaBeanFactory.getIndentation(2) +  "PreparedStatement " + preparedStatementName + " = null\n"
        s += JavaBeanFactory.getIndentation(2) +  "try\n"
        s += JavaBeanFactory.getIndentation(2) +  "{\n"
        s += JavaBeanFactory.getIndentation(3) +  "" + preparedStatementName + " = conn.prepareStatement(\n"
        s += JavaBeanFactory.getIndentation(3) +  "\"INSERT INTO " + tableName + columnss + "\" +\n"
        s += JavaBeanFactory.getIndentation(3) +  "\" VALUES (" + questionMarks + ")\" )\n"
    
        // now need to set the value of each ?
        s += JavaBeanFactory.getIndentation +  setStatements.toString + "\n"
        s += JavaBeanFactory.getIndentation(3) +  preparedStatementName + ".executeUpdate\n"
        s += JavaBeanFactory.getIndentation(2) +  "}\n"
        s += JavaBeanFactory.getIndentation(2) +  "catch (SQLException se)\n"
        s += JavaBeanFactory.getIndentation(2) +  "{\n"
        s += JavaBeanFactory.getIndentation(3) +  "// log exception if desired\n"
        s += JavaBeanFactory.getIndentation(3) +  "throw se\n"
        s += JavaBeanFactory.getIndentation(2) +  "}\n"
        s += JavaBeanFactory.getIndentation(2) +  "finally\n"
        s += JavaBeanFactory.getIndentation(2) +  "{\n"
        s += JavaBeanFactory.getIndentation(3) +  "if ( " + preparedStatementName + " != null )\n"
        s += JavaBeanFactory.getIndentation(3) +  "{\n"
        s += JavaBeanFactory.getIndentation(3) +  "" + preparedStatementName + ".close\n"
        s += JavaBeanFactory.getIndentation(3) +  "}\n"
        s += JavaBeanFactory.getIndentation(2) +  "}\n"
        s += JavaBeanFactory.getIndentation + "}\n"
        s += JavaBeanFactory.getIndentation + "\n"
        s
    }

}


