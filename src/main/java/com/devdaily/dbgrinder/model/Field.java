package com.devdaily.dbgrinder.model;

/**
 * The Field class represents a field in a Java class.
 * A field has scope, type, and a name.
 */
public class Field
{
  public String scope;      // public, private, protected
  public String type;       // String, Date, Time
  public String fieldName;  // the name of the field, like "username"

  public Field (String scope, String type, String name)
  {
    this.scope = scope;
    this.type = type;
    this.fieldName = name;
  }

  public void print ()
  {
    //System.out.println("   field scope: " + scope);
    //System.out.println("   field type: " + type);
    //System.out.println("   field fieldName: " + fieldName + "\n");
  }
}


