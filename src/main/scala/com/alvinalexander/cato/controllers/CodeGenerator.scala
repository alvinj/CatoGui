package com.alvinalexander.cato.controllers

import java.io._
import java.util._
import freemarker.template._
import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer
import com.alvinalexander.cato.TemplateEngine
 
object CodeGenerator {

    /**
     * Given a template and some other information (database model info),
     * generate the output source code.
     */
    def generateCode(template: String): String = {
        template
    }
    
    def generateCode(rootTemplateDir: String, templateAsString: String, data: Map[String, Object]): String = {
        val jmap = new java.util.HashMap[String, Object](data)
        val result = TemplateEngine.applyDataToTemplate(rootTemplateDir, templateAsString, jmap)
        result
    }
    
    /**
     * This code shows a demo of how this needs to work with FreeMarker.
     * The real method will need to accept (templateAsString, data) as
     * arguments, and return the transformed String.
     */
    def generateCodeExample(templateAsString: String, data: Map[String, Object]): String = {
        val data = scala.collection.mutable.Map[String, Object]()
        data += ("message" -> "Hello, world!")
    
        val countries = new ArrayBuffer[String]
        countries += ("India")
        countries += ("United States")
        countries += ("Germany")
        countries += ("France")
    
        // convert the data to java data types before calling the freemarker-based method.
        // http://www.scala-lang.org/api/current/index.html#scala.collection.JavaConversions$
        val jlist : java.util.List[String] = countries
        data.put("countries", jlist)    
        val jmap = new java.util.HashMap[String, Object](data)
        
        val result = TemplateEngine.applyDataToTemplate(null, templateAsString, jmap)
        result
    }
  
}


