package com.alvinalexander.cato.model

/**
 *  (converted to Scala by Alvin J. Alexander)
  * FKDefinition
  * <p>A Container for a Foreign Key definition for a table</p>
  * @author  I.Holsman
  * @version 1.6
  * (c) Ian Holsman 1998
  * Released under GPL. See LICENSE for full details.
  */
import scala.collection.mutable.ArrayBuffer

// TODO this code needs to be seriously re-thought
case class FKDefinition(PKTable: String, FKTable: String, pFKName: String) {

    val FKname = if (pFKName != null) pFKName else FKTable + PKTable
    val FKfields = ArrayBuffer[String]()
    val PKfields = ArrayBuffer[String]()

    /**
     * add the corresponding Foreign Key Name and Primary Key name
     */
    def addField(FKFieldName: String, PKFieldName: String) {
        FKfields += FKFieldName
        PKfields += PKFieldName
    }

    def getFKColList: String = {
        var sRet = ""
        for (e <- FKfields) {
            // TODO i have no idea why sRet is set to null in the 'else' clause here
            if (sRet != null) sRet = sRet + ", " + e else sRet = e 
        }
        return sRet.toLowerCase
    }
    
    def getPKColList: String = {
        var sRet = ""
        for (e <- FKfields) {
            // TODO i have no idea why sRet is set to null in the 'else' clause here
            if (sRet != null) sRet + ", " + e else sRet = e 
        }
        return sRet.toLowerCase
    }

    // TODO these methods are dangerous
    def getPKName = PKfields(0)
    def getFKName = FKfields(0)

}



