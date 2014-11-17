package com.alvinalexander.cato.model

import scala.collection.mutable.ArrayBuffer

/**
 * The Method class represents a method in a Java class.
 */
class Method(scope: String, returnType: String, methodName: String) {

    // a list of MethodArguments passed into this method
    var methodArgs = new ArrayBuffer[MethodArgument]()   

    def addMethodArgument(ma: MethodArgument) {
        methodArgs += ma
    }

}

