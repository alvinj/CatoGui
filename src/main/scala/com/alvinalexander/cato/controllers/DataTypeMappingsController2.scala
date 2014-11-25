package com.alvinalexander.cato.controllers

import net.liftweb.json.DefaultFormats
import net.liftweb.json._
import com.alvinalexander.cato.utils.FileUtils
import com.alvinalexander.cato.utils.JavaFileUtils
import com.alvinalexander.cato.CatoGui
    
/**
 * This is the "controller" that handles things related to mapping data types,
 * including reading the data type mapping config file and making those mappings
 * available to others.
 */
object DataTypeMappingsController2 {
    
    type DataTypeMap = Map[String, DataTypeRepresentation]

    // these strings must match the keys in the 'datatypemappings.json' file
    val JAVA           = "java"
    val JSON           = "json"
    val PHP            = "php"
    val PLAY           = "play"
    val PLAY_OPTIONAL  = "playOptional"
    val SCALA          = "scala"

    val BLOB      = "blob"
    val BOOLEAN   = "boolean"
    val DATE      = "date"
    val DOUBLE    = "double"
    val FLOAT     = "float"
    val INTEGER   = "integer"
    val LONG      = "long"
    val TEXT      = "text"
    val TIMESTAMP = "timestamp"

    /**
     * Returns a `DataTypeMap`, which is a type of `Map[String, DataTypeRepresentation]`.
     * Use this map when calling `getDataTypeMap`, like this:
     * 
     * val allDataTypes = getAllDataTypesAsMap(jsonString)
     * val javaMap = getDataTypeMap(JAVA, allDataTypes)
     * 
     */
    def getAllDataTypesAsMap(jsonString: String): DataTypeMap = {
        // "java" -> its map, "scala" -> its map, etc.
        val dataTypeMappings = scala.collection.mutable.Map[String, DataTypeRepresentation]()
        implicit val formats = DefaultFormats
        val jsonAsJValue = parse(jsonString)
        val mappings = (jsonAsJValue \\ "datatype").children
        for (mapping <- mappings) {
            val m = mapping.extract[DataTypeRepresentation]
            dataTypeMappings += (m.language -> m)
        }
        dataTypeMappings.toMap
    }
    
    /**
     * Usage: getMap(JAVA)  // returns the java data mapping as read from the json cfg file
     */
    def getDataTypeMap(mapKey: String, dataTypeMappings: DataTypeMap) = {
        val map = dataTypeMappings(mapKey)
        Map(
            BLOB      -> map.blob,
            BOOLEAN   -> map.boolean,
            DATE      -> map.date,
            DOUBLE    -> map.double,
            FLOAT     -> map.float,
            INTEGER   -> map.integer,
            LONG      -> map.long,
            TEXT      -> map.text,
            TIMESTAMP -> map.timestamp
        )
    } 

}


object DataTypeMappingTest extends App {
    import DataTypeMappingsController2._
    val jsonString = JavaFileUtils.readFileAsString("/Users/Al/Projects/Scala/CatoGui/resources/datatypemappings.json")
    val allDataTypesAsMap = DataTypeMappingsController2.getAllDataTypesAsMap(jsonString)
    val javaMap = DataTypeMappingsController2.getDataTypeMap(JAVA, allDataTypesAsMap)
    println(javaMap)
}


// a case class to match the JSON data
case class DataTypeRepresentation (
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





