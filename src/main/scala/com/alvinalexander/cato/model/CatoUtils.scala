package com.alvinalexander.cato.model

object CatoUtils {

    /**
     * Converts strings like "users" to "user".
     * TODO convert "persons" to "people".
     */
    def singularize(s: String) = {
        if (s.endsWith("s")) s.init else s
    }

}