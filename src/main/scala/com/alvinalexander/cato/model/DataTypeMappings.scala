package com.alvinalexander.cato.model

object DataTypeMappings {
  
    val BLOB      = "blob"
    val BOOLEAN   = "boolean"
    val DATE      = "date"
    val DOUBLE    = "double"
    val FLOAT     = "float"
    val INTEGER   = "integer"
    val LONG      = "long"
    val TEXT      = "text"
    val TIMESTAMP = "timestamp"

    val phpTypesMap = Map(
        BLOB      -> "Object",
        BOOLEAN   -> "Boolean",
        DATE      -> "String",
        DOUBLE    -> "Float",
        FLOAT     -> "Float",
        INTEGER   -> "Integer",
        LONG      -> "Integer",
        TEXT      -> "String",
        TIMESTAMP -> "String"
    )

    val javaTypesMap = Map(
        BLOB      -> "Object",
        BOOLEAN   -> "boolean",
        DATE      -> "Date",
        DOUBLE    -> "double",
        FLOAT     -> "float",
        INTEGER   -> "int",
        LONG      -> "long",
        TEXT      -> "String",
        TIMESTAMP -> "Timestamp"
    )

    val jsonTypesMap = Map(
        BLOB      -> "JsString",
        BOOLEAN   -> "JsBoolean",
        DATE      -> "JsString",
        DOUBLE    -> "JsNumber",
        INTEGER   -> "JsNumber",
        LONG      -> "JsNumber",
        FLOAT     -> "JsNumber",
        TEXT      -> "JsString",
        TIMESTAMP -> "JsString"
    )
    
    val scalaTypesMap = Map(
        BLOB      -> "Object" ,
        BOOLEAN   -> "Boolean",
        DATE      -> "java.util.Date",
        DOUBLE    -> "Double",
        FLOAT     -> "Float",
        INTEGER   -> "Integer",
        LONG      -> "Long",
        TEXT      -> "String",
        TIMESTAMP -> "java.util.Date"
    )

    val playTypesMap = Map(
        BLOB      -> "nonEmptyText",  //TODO
        BOOLEAN   -> "TODO",
        DATE      -> "sqlDate",
        DOUBLE    -> "Double",  //TODO
        FLOAT     -> "Double",  //TODO
        INTEGER   -> "number",
        LONG      -> "longNumber",
        TEXT      -> "nonEmptyText",
        TIMESTAMP -> "sqlDate"
    )

    val playOptionalTypesMap = Map(
        BLOB      -> "optional(text)",  //TODO
        BOOLEAN   -> "optional(number)",
        DATE      -> "optional(sqlDate)",
        DOUBLE    -> "optional(number)",
        FLOAT     -> "optional(number)",
        INTEGER   -> "optional(number)",
        LONG      -> "optional(longNumber)",
        TEXT      -> "optional(text)",
        TIMESTAMP -> "optional(sqlDate)"
    )

    
}








