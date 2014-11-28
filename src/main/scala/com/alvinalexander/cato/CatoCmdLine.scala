package com.alvinalexander.cato

import com.alvinalexander.cato.model.Database
import com.alvinalexander.cato.model.DatabaseUtils
import com.alvinalexander.cato.model.TableUtils
import com.alvinalexander.cato.examples.Common
import org.clapper.argot._
import ArgotConverters._

class CatoCmdLine (
    templatesDir: String,
    dataTypesMappingFile: String,
    driver: String,
    url: String,
    username: String,
    password: String,
    dbTable: String
) {

    import Common._
    val database = new Database(driver, url, username, password)
    val connection = DatabaseUtils.makeConnection(database).get
    val metaData = DatabaseUtils.getTableMetaData(connection).get
    val tableNames = DatabaseUtils.getTableNames(connection, metaData)
    
    // Vector v = Table.getColumnData(currentlySelectedDatabaseTableName,Project.getDatabaseMetaData(),null,null,true);
    val columns = TableUtils.getColumnData("transactions", metaData, null, null, true).get
    for (cd <- columns) {
        println(s"ColumnName:  ${cd.getColumnName}")
        println(s"ColumnType:  ${cd.getJavaType}")
        println(s"ColumnWidth: ${cd.getNumColumns}")
        println("")
    }
    
    
    def run: String = {
        "Here's the output!"
    }

    printHeader("Class Names")
    for (t <- tableNames) {
        println(TableUtils.convertTableNameToClassName(t))
    }

    private def printHeader(s: String) {
        println(s"\n$s")
        println(genUnderlines(s))
    }

    private def genUnderlines(s: String) = s.map(_ => "-").mkString
    
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
    
    /**
     * This line is Argot's way of saying that we support the `-m filename` and `--mappingfile filename`
     * command line options.
     */
    val templatesDirOption = (parser.option[String](List("-x", "templatesdir"), "dir", "The canonical name of the data types mapping file.")).value
    val mappingFileOption = (parser.option[String](List("m", "mappingfile"), "filename", "The canonical name of the data types mapping file.")).value
    val usernameOption = (parser.option[String](List("-u", "user"), "username", "The username needed to connect to the database.")).value
    val passwordOption = (parser.option[String](List("-p", "password"), "password", "The password needed to connect to the database.")).value
    val driverOption = (parser.option[String](List("-d", "driver"), "driver", "The database driver (string).")).value
    val urlOption = (parser.option[String](List("-r", "url"), "url", "The database url (string).")).value
    val tableOption = (parser.option[String](List("-l", "table"), "dbTable", "The database table you want to generate code from.")).value
    
    for {
        templatesDir <- templatesDirOption
        mappingFile <- mappingFileOption
        username <- usernameOption
        password <- passwordOption
        driver <- driverOption
        url <- urlOption
        table <- tableOption
    } {
        val cato = new CatoCmdLine(
            templatesDir,
            mappingFile,
            driver,
            url,
            username,
            password,
            table)
        
        // 3 - get the output and print it
        val output = cato.run
        println(output)
    }

    private def printUsageStatementAndDie {
        System.err.println("")
        System.err.println("Usage: java -jar cato-0.1.jar -m dataTypesMapping.conf")
        System.err.println("")
        System.exit(1)
    }

}














