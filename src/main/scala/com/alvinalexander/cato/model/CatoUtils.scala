package com.alvinalexander.cato.model

import java.io.StringWriter
import java.io.PrintWriter
import com.alvinalexander.inflector.Inflections

object CatoUtils {

    /**
     * Converts strings like "users" to "user".
     */
    def singularize(s: String) = Inflections.singularize(s)

    /**
     * Converts strings like "user" to "users".
     */
    def pluralize(s: String) = Inflections.pluralize(s)

    def convertUnderscoreNameToCamelCase(s: String): String = {
        val sb = new StringBuilder
        var ucNextChar = false
        for (c <- s) {
            if (c == '_') {
                ucNextChar = true
                //sb.append(Character.toUpperCase(c))
            } else {
                if (ucNextChar) sb.append(Character.toUpperCase(c)) else sb.append(c)
                ucNextChar = false
            }
        }
        sb.toString
    }

    def getStackTraceString(t: Throwable) = {
        val sw = new StringWriter
        t.printStackTrace(new PrintWriter(sw))
        sw.toString
    }

}