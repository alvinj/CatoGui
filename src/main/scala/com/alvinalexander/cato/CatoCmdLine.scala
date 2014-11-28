package com.alvinalexander.cato

import com.alvinalexander.cato.model.Database
import com.alvinalexander.cato.model.DatabaseUtils
import com.alvinalexander.cato.model.TableUtils
import com.alvinalexander.cato.examples.Common
import org.clapper.argot._
import ArgotConverters._
import com.alvinalexander.cato.utils.JavaFileUtils
import com.alvinalexander.cato.controllers.CodeGenerator
import com.alvinalexander.cato.controllers.DataTypeMappingsController
import java.io.File

class CatoCmdLine (
    templateFilenameAsString: String,
    dataTypesMappingFile: String,
    driver: String,
    url: String,
    username: String,
    password: String,
    dbTableName: String
) {

    import Common._
    
    def generateCode: String = {
        val database = new Database(driver, url, username, password)
        val connection = DatabaseUtils.makeConnection(database).get
        val metaData = DatabaseUtils.getTableMetaData(connection).get
        val columnData = TableUtils.getColumnData(dbTableName, metaData, null, null, true).get
        
        val templateFileAsString = JavaFileUtils.readFileAsString(templateFilenameAsString)
    
        // build up the `data` object from the selected Table and Fields
        val allDataTypeMappingsAsJsonString = JavaFileUtils.readFileAsString(dataTypesMappingFile)
        val allDataTypesAsMap = DataTypeMappingsController.getAllDataTypesAsMap(allDataTypeMappingsAsJsonString)
        
        // TODO create a Seq[String] to represent the desired fields, which is "all fields" in this case
        val fields = CodeGenerator.getFieldsForTableName(metaData, dbTableName)
        val data = CodeGenerator.buildDataObjectForTemplate(metaData, allDataTypesAsMap, dbTableName, fields)
    
        // apply the data to the template to get the desired code
        // templateFile
        val templateFile = new File(templateFilenameAsString)
        val generatedCode = CodeGenerator.generateCode(templateFile.getParent, templateFileAsString, data)
        generatedCode
    }
    
}


/**
 * This is a command-line version of Cato. It prints its output to STDOUT,
 * so you can redirect it to a file as desired, or copy and paste it in
 * a terminal window.
 * 
 * While that's what this application does, I'm really just writing it so
 * I can make sure that my code is reusable with more than one interface.
 * (For example, as I write this comment I know that I have too much 
 * functionality in the CatoGui app, functionality that needs to be more
 * generally available.)
 * 
 * Usage:
 * 
 *     java -jar CatoCmdLine.jar
 *          -classpath ... // add your database driver
 *          -t templatesDir
 *          -m dataTypesMappingFile
 *          -u user
 *          -p password
 *          -d driver
 *          -u url
 *          -t table
 *          
 * This application just uses all of the fields in the table. It doesn't
 * let you select individual fields the way the GUI does.
 * 
 * This code may also serve as a hint as to how the CatoGui works.
 * 
 */
object CatoCmdLine extends App {
    
    /**
     * Use Argot to read command-line arguments.
     * At this time the need for the "Data Types Mapping" file isn't really optional,
     * so halt the program if it can't be found. 
     */
    val parser = new ArgotParser("Cato", preUsage=Some("Cato, Version 0.1. Copyright (c) 2014, Alvin J. Alexander (alvinalexander.com)."))

    // define the command-line args we need
    val templateFileSVOption = (parser.option[String](List("x", "templatefile"), "filename", "The canonical name of the template file to use as input."))
    val mappingFileSVOption = (parser.option[String](List("m", "mappingfile"), "filename", "The canonical name of the data types mapping file."))
    val usernameSVOption = (parser.option[String](List("u", "user"), "username", "The username needed to connect to the database."))
    val passwordSVOption = (parser.option[String](List("p", "password"), "password", "The password needed to connect to the database."))
    val driverSVOption = (parser.option[String](List("d", "driver"), "driver", "The database driver (string)."))
    val urlSVOption = (parser.option[String](List("r", "url"), "url", "The database url (string)."))
    val tableSVOption = (parser.option[String](List("l", "table"), "dbTable", "The database table you want to generate code from."))
    
    parser.parse(args)
    
    for {
        templateFile <- templateFileSVOption.value
        mappingFile <- mappingFileSVOption.value
        username <- usernameSVOption.value
        password <- passwordSVOption.value
        driver <- driverSVOption.value
        url <- urlSVOption.value
        table <- tableSVOption.value
    } {
        val cato = new CatoCmdLine(
            templateFile,
            mappingFile,
            driver,
            url,
            username,
            password,
            table)

        // this is where the work is done
        val output = cato.generateCode
        println(output)
    }
    
    // TODO handle the situation where 1+ options were improperly defined

    private def printUsageStatementAndDie {
        System.err.println("")
        System.err.println("Usage: java -jar cato-0.1.jar -m dataTypesMapping.conf")
        System.err.println("")
        System.exit(1)
    }

}














