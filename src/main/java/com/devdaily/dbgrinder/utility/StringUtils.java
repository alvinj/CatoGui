package com.devdaily.dbgrinder.utility;

public class StringUtils
{

  /**
    * Wrap single quotes around the appropriate types for sql statements.
    */
  public static String sqlQuote(String name,String type)
  {
    String item=name;

    if (type.compareTo("String") == 0)
      item = "DataObject.formatString("+name+")";
    //else if (type.compareTo("byte") == 0)
    //    item = "\"\'\"+"+name+"+\"\'\"";
    else if (type.compareTo("Date") == 0)
      item = "\"\'\"+"+name+"+\"\'\"";
    else if (type.compareTo("Timestamp") == 0)
      item = "DataObject.formatTimeStamp("+name+")";
    else if (type.compareTo("Timestamp") == 0)
      item = "\"\'\"+"+name+"+\"\'\"";
    else if (type.compareTo("Time") == 0)
      item = "\"\'\"+"+name+"+\"\'\"";
    else if (type.compareTo("long") == 0)
      item = name;
    else if (type.compareTo("int") == 0)
      item = name;
    else if (type.compareTo("short") == 0)
      item = name;
    else if (type.compareTo("float") == 0)
      item = name;
    else if (type.compareTo("double") == 0)
      item = name;
    else if (type.compareTo("byte") == 0)
      item = name;
    else if (type.compareTo("byte[]") == 0)  // this ain't gonna work!
      item = name;
    else if (type.compareTo("char") == 0)
      item = "\"\'\"+"+name+"+\"\'\"";
    else
      System.out.println("Warning! Unknown type : "+type+" in sqlQuote");

    return item;
  }

  /**
    * Makes the first character of the given String lower case.
    */
  public static String makeFirstCharLowerCase(String s)
  {
    String newName = s.substring(0,1).toLowerCase()+
                     s.substring(1,s.length());
    return newName;
  }

  /**
    * Makes the first character of the given String upper case.
    */
  public static String makeFirstCharUpperCase(String s)
  {
    String newName = s.substring(0,1).toUpperCase()+
                     s.substring(1,s.length());
    return newName;
  }

  /**
    * Remove the letter 's' from the end of a String if it is there.
    * This helps convert table names like Users to class names like User and UserDO.
    */
  public static String removeTrailingS(String s)
  {
    String newString = s;
    if ( s.charAt( s.length()-1 ) == 's' )
    {
      s = s.substring(0,s.length()-1);
      newString = s;
    }
    return newString;
  }

  /**
   * Upper cases the first character in a String.
   */
  public static final String firstCharacterUpperCase(String s)
  {
    return (s.substring(0,1).toUpperCase()+s.substring(1,s.length()));
  }

  /**
   * Makes the first character of the String lower case.
   */
  public static final String firstCharacterLowerCase(String s)
  {
     return ( s.substring(0,1).toLowerCase() + s.substring(1,s.length()) );
  }


  /**
   * Replace all occurrences of searchFor with replaceWith in the String stringToModify.
   */
  public static final String replaceAll(String stringToModify, String searchFor, String replaceWith)
  {
    int indexOfFind = 0;
    String returnString = stringToModify;
    while ( (indexOfFind = returnString.indexOf(searchFor)) >= 0 )
    {
      returnString = replace( returnString, searchFor, replaceWith );
    }
    return returnString;

  }

  /**
   * Replaces the String "find" in the String "stringToModify" with the String "replaceWith".
   */
  public static final String replace(String stringToModify, String find, String replaceWith)
  {
    int indexOfFind = stringToModify.indexOf(find);
    if ( indexOfFind < 0 )
      return stringToModify;

    StringBuffer sb = new StringBuffer();
    for ( int i=0; i<stringToModify.length(); i++ )
    {
      if ( i<indexOfFind )
        sb.append( stringToModify.charAt(i) );
      if ( i == indexOfFind )
        sb.append( replaceWith );
      if ( i > indexOfFind+find.length()-1 )
        sb.append( stringToModify.charAt(i) );
    }
    return new String(sb);
  }

  public static String convertCamelCaseToUnderscore (final String s)
  {
    return s.replaceAll("([A-Z])", "_" +"$1").toLowerCase();
  }

  public static String convertUnderscoreNameToUpperCase (final String s)
  {
    StringBuffer sb = new StringBuffer();
    for ( int i=0; i<s.length(); i++ )
    {
      char c = s.charAt(i);
      if ( c == '_' )
      {
        // don't add underscore to the new name, but make the next
        // character uppercase
        i++;
        c = s.charAt(i);
        sb.append(Character.toUpperCase(c));
      }
      else
      {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  public static void main(String[] args)
  {
    System.out.println("main was called");
    String s = StringUtils.replace( "This string contains the phrase DOMAIN_OBJECT in it.",
                                    "DOMAIN_OBJECT",
                                    "toppingId" );
    System.out.println(s);
  }


}