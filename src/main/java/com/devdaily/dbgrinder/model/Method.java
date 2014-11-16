package com.devdaily.dbgrinder.model;

import java.util.Vector;

/**
 * The Method class represents a method in a Java class.
 */
public class Method
{
  public String scope;         // public, private, protected
  public String returnType;    // String, Date, Time
  public String methodName;    // the name of the method, like "setPassword"
  public Vector args = new Vector();  // a list of MethodArguments passed into this method

  public Method(String scope, String returnType, String methodName)
  {
    this.scope = scope;
    this.returnType = returnType;
    this.methodName = methodName;
  }

  public void addMethodArgument(MethodArgument ma)
  {
    args.add(ma);
    //ma.print();
  }

}

