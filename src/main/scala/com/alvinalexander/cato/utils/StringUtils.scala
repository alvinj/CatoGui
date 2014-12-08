package com.alvinalexander.cato.utils

import java.io.StringWriter
import java.io.PrintWriter

object StringUtils {

    def isNullOrEmpty(s: String) = if (s==null || s.trim.equals("")) true else false

    def getStackTraceAsString(t: Throwable) = {
        val sw = new StringWriter
        t.printStackTrace(new PrintWriter(sw))
        sw.toString
    }
    
    def capitalizeAllWordsInString(s: String) = s.split(' ').map(_.capitalize).mkString(" ")

}