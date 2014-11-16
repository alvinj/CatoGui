package com.devdaily.dbgrinder.model;

/**
  * FKDefinition
  * <p>A Container for a Foreign Key definition for a table</p>
  * @author  I.Holsman
  * @version 1.6
  * (c) Ian Holsman 1998
  * Released under GPL. See LICENSE for full details.
  */
import java.util.*;

public class FKDefinition
{
  protected String FKname;
  protected String FKtable;
  protected String PKtable;
  protected Vector FKfields;
  protected Vector PKfields;
  /**
    * Constructor
    */
  public FKDefinition( String pPKTable, String pFKTable, String pFKName )
  {
    FKtable = pFKTable;
    PKtable = pPKTable;
    if ( pFKName != null )
      FKname = pFKName;
    else
      FKname = FKtable + PKtable;
    FKfields = new Vector();
    PKfields = new Vector();
  }
  public  String getPKTableName( ) { return PKtable;}
  public  String getFKTableName( ) { return FKtable;}
  /**
   * add the corresponding Foreign Key Name and Primary Key name
   */
  public void addField( String FKFieldName, String PKFieldName )
    {
      FKfields.addElement( FKFieldName);
      PKfields.addElement( PKFieldName);
    }
  public String getFKColList() {
    String sRet = null;

    for (Enumeration e = FKfields.elements() ; e.hasMoreElements() ;) {
      if ( sRet != null )
        sRet = sRet + ", " + (String)e.nextElement();
      else
        sRet = (String)e.nextElement();
    }
    return sRet.toLowerCase();
  }
  public String getPKColList() {
    String sRet = null;

    for (Enumeration e = PKfields.elements() ; e.hasMoreElements() ;) {
      if ( sRet != null )
        sRet = sRet + ", " + (String)e.nextElement();
      else
        sRet = (String)e.nextElement();
    }
    return sRet.toLowerCase();
  }
  public String getPKName()
  {
    return (String)PKfields.firstElement();
  }
  public  String getFKName( )
  {
    return (String)FKfields.firstElement();
  }
  public Vector getFKFields()
  {
    return FKfields;
  }
  public Vector getPKFields()
  {
    return PKfields;
  }

}
