package com.alvinalexander.cato.model

import scala.reflect.BeanProperty

/**
 * TODO i tried to get this class to work with FreeMarker using the usual
 * JavaBean recipe, but I still got errors in FreeMarker, with it complaining
 * abou the cameCaseFieldName.
 * So, I'm currently using the com.alvinalexander.cato.Field class instead of
 * this class.
 */
//class Field(
//    @BeanProperty var fieldName: String,
//    @BeanProperty var camelCaseFieldName: String,
//    @BeanProperty var fieldType: String,
//    @BeanProperty var javaFieldType: String,
//    @BeanProperty var jsonFieldType: String,
//    @BeanProperty var phpFieldType: String,
//    @BeanProperty var playFieldType: String,
//    @BeanProperty var playOptionalFieldType: String,
//    @BeanProperty var scalaFieldType: String,
//    @BeanProperty var databaseFieldType: String,
//    @BeanProperty var isRequired: Boolean
//)
//
//object FieldDriver extends App {
//  
//    val f = new Field(
//            "fieldName",
//            "camel",
//            "fieldType",
//            "javaField",
//            "json",
//            "php",
//            "play",
//            "playOpt",
//            "scala",
//            "db",
//            true
//            )
//    
//    println(f.getCamelCaseFieldName())
//  
//}
