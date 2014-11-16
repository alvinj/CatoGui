package com.devdaily.util;

public class StringUtils
{
  /**
   * Upper cases the first character in a String.
   */
  public static final String firstCharacterUpperCase(String s)
  {
    return (s.substring(0,1).toUpperCase()+s.substring(1,s.length()).toLowerCase());
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

  public static void main(String[] args)
  {
    System.out.println("main was called");
    String s = StringUtils.replace( "This string contains the phrase DOMAIN_OBJECT in it.",
                                    "DOMAIN_OBJECT",
                                    "toppingId" );
    System.out.println(s);
  }


}