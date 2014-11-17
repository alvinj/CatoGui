package com.alvinalexander.cato.model

import scala.collection.mutable.ArrayBuffer

/**
 * The DomainObject class will represent the DomainObject being built
 * as TableGen reads the database table(s). Examples are Product,
 * Menu, Topping, etc.
 */
class DomainObject(packageName: String, className: String) 
{
    var fields = new ArrayBuffer[Field]()
    var methods = new ArrayBuffer[Method]()
    var getMethods = new ArrayBuffer[Method]()
    var setMethods = new ArrayBuffer[Method]()
  
    def addField (f: Field) {
        fields += f
    }
  
    def addMethod (m: Method) {
        methods += m
    }
  
    def addSetMethod (m: Method) {
        setMethods += m
        addMethod(m)
    }
  
    def addGetMethod (m: Method) {
        getMethods += m
    }
  
}

