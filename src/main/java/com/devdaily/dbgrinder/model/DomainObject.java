package com.devdaily.dbgrinder.model;


// TODO delete this class

import java.util.*;

/**
 * The DomainObject class will represent the DomainObject being built
 * as TableGen reads the database table(s). Examples are Product,
 * Menu, Topping, etc.
 */
public class DomainObject
{
  public String className;
  public String packageName;
  public Vector fields = new Vector();
  public Vector methods = new Vector();
  public Vector getMethods = new Vector();
  public Vector setMethods = new Vector();

  public DomainObject (String packageName, String className)
  {
    this.packageName = packageName;
    this.className = className;
  }

//  public void addField (Field f)
//  {
//    fields.add(f);
//    f.print();
//  }

  private void addMethod (Method m)
  {
    methods.add(m);
  }

  public void addSetMethod (Method m)
  {
    setMethods.add(m);
    addMethod(m);
  }

  public void addGetMethod (Method m)
  {
    getMethods.add(m);
  }

  public String getPackageName()
  {
    return packageName;
  }

}

