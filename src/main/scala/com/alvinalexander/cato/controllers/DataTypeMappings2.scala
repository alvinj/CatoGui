package com.alvinalexander.cato.controllers

import net.liftweb.json.DefaultFormats
import net.liftweb.json._
import com.alvinalexander.cato.utils.FileUtils
import com.alvinalexander.cato.utils.JavaFileUtils
    
/**
 * This is the "controller" that handles things related to mapping data types,
 * including reading the data type mapping config file and making those mappings
 * available to others.
 */
class DataTypeMappings2 {
    
    def convertJsonStringToDataTypeMappings(jsonString: String) = {
        implicit val formats = DefaultFormats
        val jsonAsJValue = parse(jsonString)
        val mappings = (jsonAsJValue \\ "datatype").children
        for (mapping <- mappings) {
            val m = mapping.extract[DataTypeMap]
            println(s"Data Type: language: ${m.language}, blob: ${m.blob}, boolean: ${m.boolean}")
        }
    }

}

object DataTypeMappingTest extends App {
    val jsonString = JavaFileUtils.readFileAsString("/Users/Al/Projects/Scala/CatoGui/resources/datatypemappings.json")
    println(jsonString)
    val ctrl = new DataTypeMappings2
    ctrl.convertJsonStringToDataTypeMappings(jsonString)
}


// a case class to match the JSON data
case class DataTypeMap (
    language: String,  // java, scala, play, etc.
    blob: String,
    boolean: String,
    date: String,
    double: String,
    float: String,
    integer: String,
    long: String,
    text: String,
    timestamp: String
)

