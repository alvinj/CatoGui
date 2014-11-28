//package com.alvinalexander.cato.examples
//import com.devdaily.dbgrinder.model.Project
//import java.util.Properties
//import com.devdaily.dbgrinder.model.Table
//import java.sql.SQLException
//import com.devdaily.dbgrinder.model.ColumnData
//import scala.collection.mutable.ArrayBuffer
//
//object DatabaseConnectionTest extends App {
//
//    // /Applications/MAMP/tmp/mysql/mysql.sock
//    val driver = "com.mysql.jdbc.Driver"
//    val url = "jdbc:mysql://localhost:8889/finance"
//    val username = "root"
//    val password = "root"
//  
//    Project.setDriver(driver)
//    Project.setUrl(url)
//    Project.setUsername(username)
//    Project.setPassword(password)
//    Project.connectToDatabase()
//    Project.setMethodCreationMode(Project.CREATION_MODE_DEFAULT)
//    Project.setCurrentDatabaseTable("users")
//    Project.setCurrentProperties(getCurrentlyKnownProperties)
//  
//    val fieldNames = getListOfTableFields
//    fieldNames match {
//        case Some(fields) => fields.foreach(println)  
//        case None => println("No fields found!")
//    }
//
//    def getListOfTableFields: Option[Seq[String]] = {
//        val fieldNames = new ArrayBuffer[String]()
//        try {
//            val vector = Table.getColumnData(Project.getCurrentDatabaseTable, Project.getDatabaseMetaData, null, null, true)
//            val it = vector.iterator
//            while (it.hasNext) {
//                val cd = it.next.asInstanceOf[ColumnData]
//                fieldNames ++ cd.getName
//            }
//            Some(fieldNames)
//        }
//        catch {
//            case e: SQLException => 
//                System.err.println(e.getMessage)
//                None
//        }
//    }
//
//    
//    
//    
//    
//    def getCurrentlyKnownProperties: Properties = {
//        val props = new Properties
//        props.setProperty("driver",        Project.getDriver)
//        props.setProperty("url",           Project.getUrl)
//        props.setProperty("username",      Project.getUsername)
//        props.setProperty("password",      Project.getPassword)
//        props.setProperty("package_name",  "com.aa.myapp")
//        props.setProperty("database_type", "NORMAL");
//        props.setProperty("table_list",       Project.getCurrentDatabaseTable);
//        //props.setProperty("class_list",       Project.getCurrentDatabaseTable());
//        //props.setProperty("desiredColumns",   null );
//        return props;
//    }
//
//
//}
//
//
//
//
