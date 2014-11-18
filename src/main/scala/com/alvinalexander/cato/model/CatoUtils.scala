package com.alvinalexander.cato.model

object CatoUtils {

    /**
     * Converts strings like "users" to "user".
     * TODO convert "persons" to "people".
     */
    def singularize(s: String) = {
        if (s.endsWith("s")) s.init else s
    }

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


}