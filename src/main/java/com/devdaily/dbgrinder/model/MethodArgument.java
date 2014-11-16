package com.devdaily.dbgrinder.model;

/**
 * The MethodArgument class represents the arguments that can be passed to
 * a method in a Java class.
 */
public class MethodArgument
{
  public String argType;   // String, int, float, etc.
  public String argName;   // the name of the arg being passed
  public void print ()
  {
    System.out.println("   method arg type: " + argType);
    System.out.println("   method arg name: " + argName);
  }
}

