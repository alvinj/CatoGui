package com.alvinalexander.cato.model

import com.devdaily.dbgrinder.model.ColumnData

/**
 * A simple Factory that should produce JavaBeans and
 * JavaBean components.
 */
object JavaBeanFactory {

    var indentation = "  "
    val INDENT_TWO_CHARACTERS = "  "
    val INDENT_ONE_TAB = "\t"
  
    // TODO wrap in try
    def writeJavaBeanGetMethod(cd: ColumnData, newName: String, varName: String): String = {
        var s = "\n"
        s += getIndentation + "public " + cd.getJavaType + " get" + newName + "\n"
        s += getIndentation + "{\n"
        s += getIndentation + getIndentation + "return " + varName + ";\n"
        s += getIndentation + "}"
        s
    }
  
    // TODO wrap in try
    def writeJavaBeanSetMethod(cd: ColumnData, methodReturnType: String, newName: String, varName: String) {
        var s = "\n"
        s += getIndentation +  "public " + methodReturnType + " set" + newName + "(" + cd.getJavaType + " " + varName + ")\n"
        s += getIndentation +  "{\n"
        s += getIndentation +  getIndentation + "this." + varName + " = " + varName + ";\n"
        s += getIndentation +  "}"
        return s;
    }
  
    def setIndentation(newIndentation: String) {
        indentation = newIndentation
    }
  
    def getIndentation = indentation
  
    def getIndentation(numTimes: Int) {
        var s = ""
        for (i <- 0 to numTimes) {
            s += getIndentation
        }
        s
    }

}