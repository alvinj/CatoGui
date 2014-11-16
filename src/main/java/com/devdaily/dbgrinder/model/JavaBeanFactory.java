package com.devdaily.dbgrinder.model;

import java.io.IOException;

/**
 * A simple Factory that should produce JavaBeans and
 * JavaBean components.
 */
public class JavaBeanFactory
{
  private static String indentation = "  ";
  public static String INDENT_TWO_CHARACTERS = "  ";
  public static String INDENT_ONE_TAB = "\t";

  static String writeJavaBeanGetMethod(ColumnData cd, String newName, String varName)
  throws IOException
  {
    String s = "\n";
    s += getIndentation() + "public " + cd.getJavaType() + " get" + newName + "()\n";
    s += getIndentation() + "{\n";
    s += getIndentation() + getIndentation() + "return " + varName + ";\n";
    s += getIndentation() + "}";
    return s;
  }

  static String writeJavaBeanSetMethod(ColumnData cd, String methodReturnType, String newName, String varName)
  throws IOException
  {
    String s = "\n";
    s += getIndentation() +  "public " + methodReturnType + " set" + newName + "(" + cd.getJavaType() + " " + varName + ")\n";
    s += getIndentation() +  "{\n";
    s += getIndentation() +  getIndentation() + "this." + varName + " = " + varName + ";\n";
    s += getIndentation() +  "}";
    return s;
  }

  public static void setIndentation(String newIndentation)
  {
    indentation = newIndentation;
  }

  public static String getIndentation()
  {
    return indentation;
  }

  public static String getIndentation(int numTimes)
  {
    String s = "";
    for (int i=0; i<numTimes; i++)
    {
      s += getIndentation();
    }
    return s;
  }

}