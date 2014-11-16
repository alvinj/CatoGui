package com.devdaily;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.lang.Thread;

/*
  rev. 1.1: 2000-08-05:
  - added removePipeCharacters()
*/

public final class Utils
{

  // these variables define what to do with the html code that is given to the
  // method dealWithHTML.
  public static final int LEAVE_HTML = 1;                   // good for dd mondo
  public static final int REMOVE_HTML = 2;                  // good for adware
  public static final int CONVERT_HTML_TO_ISO_LATIN = 3;    // maybe good elsewhere

  // for adware
  public static final int HANDLE_HTML = REMOVE_HTML;

/*  public static void main (String[] poop)
  {
  Timestamp t = Timestamp.valueOf("1999-05-31 18:30:00.9");
  System.out.println("OUT: " + Utils.convertTimestampToString(t));
  }*/

  /**
   *  Remove characters from the given string that are not alpha characters.
   */
  public synchronized static String allowAlphaCharacters(String s)
  {
  StringBuffer sb = new StringBuffer();

  String mask= new String("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
  for (int i=0; i<s.length(); i++)
  {
    char c = s.charAt(i);
    if ( mask.indexOf(c) >= 0 )
    {
    sb.append(c);
    }
  }
  return new String(sb);
  }

  /**
   *  Remove characters from the given string that are not alpha or numeric characters.
   */
  public synchronized static String allowAlphaNumericCharacters(String s)
  {
  StringBuffer sb = new StringBuffer();

  String mask= new String("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
  for (int i=0; i<s.length(); i++)
  {
    char c = s.charAt(i);
    if ( mask.indexOf(c) >= 0 )
    {
    sb.append(c);
    }
  }
  return new String(sb);
  }

  /**
   *  Remove characters from the given string that are not in the given mask.
   *  This is an example mask that allows alpha-numeric characters and an underscore.
   *  mask = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";
   */
  public synchronized static String allowMaskedCharacters(String s, String mask)
  {
  StringBuffer sb = new StringBuffer();

  for (int i=0; i<s.length(); i++)
  {
    char c = s.charAt(i);
    if ( mask.indexOf(c) >= 0 )
    {
    sb.append(c);
    }
  }
  return new String(sb);
  }

  /**
   *  Remove characters from the given string that are not numeric characters.
   */
  public synchronized static String allowNumericCharacters(String s)
  {
  StringBuffer sb = new StringBuffer();

  String mask= new String("0123456789");
  for (int i=0; i<s.length(); i++)
  {
    char c = s.charAt(i);
    if ( mask.indexOf(c) >= 0 )
    {
    sb.append(c);
    }
  }
  return new String(sb);
  }

  /**
   * Convert a blank or null String to "&nbsp;"
   */
  public synchronized static String convertBlankOrNullToNbsp(String s)
  {
  String newS;
  newS = convertNullToNbsp(s);
  newS = convertBlankToNbsp(newS);
  return newS;
  }


  /**
   * Convert a blank String to "&nbsp;"
   */
  public synchronized static String convertBlankToNbsp(String s)
  {
  if ( s.trim().equals("") )
  {
    return "&nbsp;";
  }
  return s;
  }

  public synchronized static String convertBracketsToIsoLatin(String input)
  {
  StringBuffer buffer = new StringBuffer(input);
  StringBuffer output = new StringBuffer();
  for (int i = 0; i < buffer.length(); i++)
  {
    if (buffer.charAt(i) == '{')
    output.append("&#123;");
    else if (buffer.charAt(i) == '}')
    output.append("&#125;");
    else
    output.append(buffer.charAt(i));
  }
  return output.toString();
  }


  /**
   * Convert extended characters to ISO-Latin equivalents.
   * Can be improved (made faster) by using a HashMap to map the key/values.
   * However, I'm currently limited to a JDK1.1.x environment, not JDK2.
   */
  public synchronized static String convertExtendedCharactersToIsoLatin(String input)
  {
  StringBuffer buffer = new StringBuffer(input);
  StringBuffer output = new StringBuffer();
  for (int i = 0; i < buffer.length(); i++)
  {
    if (buffer.charAt(i) == '"')
    output.append("&#34;");
    //else if (buffer.charAt(i) == '!')
    //  output.append("&#33;");
    //else if (buffer.charAt(i) == '#')
    //  output.append("&#35;");
    //else if (buffer.charAt(i) == '&')
    //  output.append("&#38;");
    else if (buffer.charAt(i) == '\'')
    output.append("&#39;");
    else if (buffer.charAt(i) == '(')
    output.append("&#40;");
    else if (buffer.charAt(i) == ')')
    output.append("&#41;");
    //else if (buffer.charAt(i) == '*')
    //  output.append("&#42;");
    //else if (buffer.charAt(i) == '+')
    //  output.append("&#43;");
    //else if (buffer.charAt(i) == ',')
    //  output.append("&#44;");
    //else if (buffer.charAt(i) == '-')
    //  output.append("&#45;");
    //else if (buffer.charAt(i) == '.')
    //  output.append("&#46;");
    //else if (buffer.charAt(i) == '/')
    //  output.append("&#47;");
    //else if (buffer.charAt(i) == ':')
    //  output.append("&#58;");
    //else if (buffer.charAt(i) == ';')
    //  output.append("&#59;");
    else if (buffer.charAt(i) == '<')
    output.append("&#60;");
    //else if (buffer.charAt(i) == '=')
    //  output.append("&#61;");
    else if (buffer.charAt(i) == '>')
    output.append("&#62;");
    //else if (buffer.charAt(i) == '?')
    //  output.append("&#63;");
    //else if (buffer.charAt(i) == '@')
    //  output.append("&#64;");
    //else if (buffer.charAt(i) == '[')
    //  output.append("&#91;");
    else if (buffer.charAt(i) == '\\')
    output.append("&#92;");
    //else if (buffer.charAt(i) == ']')
    //  output.append("&#93;");
    //else if (buffer.charAt(i) == '^')
    //  output.append("&#94;");
    //else if (buffer.charAt(i) == '_')
    //  output.append("&#95;");
    else if (buffer.charAt(i) == '`')
    output.append("&#96;");
    else if (buffer.charAt(i) == '{')
    output.append("&#123;");
    //else if (buffer.charAt(i) == '|')
    //  output.append("&#124;");
    else if (buffer.charAt(i) == '}')
    output.append("&#125;");
    //else if (buffer.charAt(i) == '~')
    //  output.append("&#126;");
    else
    output.append(buffer.charAt(i));
  }
  return output.toString();
  }


  /**
   * Convert a null String to "&nbsp;"
   */
  public synchronized static String convertNullToNbsp(String s)
  {
  if ( s == null )
  {
    return "&nbsp;";
  }
  return s;
  }


  /**
   *  Convert java.sql.Timestamp to the String that we want (mm-dd-yyyy hh:mm:ss).
   */
  public synchronized static String convertTimestampToString(Timestamp t)
  {
  // t.toString() should be: "yyyy-mm-dd hh:mm:ss.nanoseconds"
  String tOld = t.toString();

  if ( tOld.length() < 21 )
    return "";

  String tNew = tOld.substring(5,7)   + "-"
        + tOld.substring(8,10)   + "-"
        + tOld.substring(0,4)   + " "
        + tOld.substring(11,13) + ":"
        + tOld.substring(14,16) + ":"
        + tOld.substring(17,19);

  return tNew;
}


  /**
   Takes the given String and reformats it so it is suitable as a URL string.
   Specifically, it resolves URL problems with Netscape browsers.
   */
  public synchronized static String convertToURLFormat(String input)
  {
  StringBuffer buffer = new StringBuffer(input);
  StringBuffer output = new StringBuffer();
  for (int i = 0; i < buffer.length(); i++)
  {
  if (buffer.charAt(i) == ' ')
    output.append("%20");
  else
    output.append(buffer.charAt(i));
  }
  return output.toString();
  }
  // for devdaily
  //public static final int HANDLE_HTML = LEAVE_HTML;

  /**
   * Decides what to do with the HTML tags that might be in the input String.
   * Based on that decision, it will return a modified String. There are
   * three possible decisions at this time:<br>
   * (1) Leave the HTML as-is.<br>
   * (2) Remove all HTML tags in input.<br>
   * (3) Convert the HTML tags to ISO Latin.<br>
   *
   * The default is to remove all HTML tags in input.
   */
  public synchronized static String dealWithHTML(String input)
  {
  String s = null;

  if ( Utils.HANDLE_HTML == Utils.LEAVE_HTML )
  {
    s = convertBracketsToIsoLatin(input);   // brackets freak out mysql during updates 10-8-2000
  }
  else if ( Utils.HANDLE_HTML == Utils.CONVERT_HTML_TO_ISO_LATIN )
  {
    s = convertExtendedCharactersToIsoLatin(input);
  }
  else
  {
    s = removePipeCharacters(input);
    s = removeHtmlTags(s);
    s = convertBracketsToIsoLatin(s);   // brackets freak out mysql during updates 10-8-2000
  }
  return s;
  }


  /**
   * Replace double quotes with their HTML equivalent.
   */
  public synchronized static String escapeDoubleQuotes(String input)
  {
  StringBuffer buffer = new StringBuffer(input);
  StringBuffer output = new StringBuffer();
  for (int i = 0; i < buffer.length(); i++)
  {
    if (buffer.charAt(i) == '\"')
    output.append("&quot;");
    else
    output.append(buffer.charAt(i));
  }
  return output.toString();
  }


  /**
   * Replace single quotes with a backslash-single-quote.
   */
  public synchronized static String escapeSingleQuotes(String input)
  {
  StringBuffer buffer = new StringBuffer(input);
  StringBuffer output = new StringBuffer();
  for (int i = 0; i < buffer.length(); i++)
  {
    if (buffer.charAt(i) == '\'')
    {
    output.append('\'');
    }
    output.append(buffer.charAt(i));
  }
  return output.toString();
  }


  /**
   * Return today's date as a String formatted properly for use with a SQL database.
   * The format is yyyy-mm-dd.
   */
  public synchronized static String getTodayAsDBString ()
  {
  Date d = new Date();
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  return sdf.format(d);
  }


  /**
   * Print the given SQL Exception information to standard error.
   */
  public synchronized static void printSQLExceptionToSystemError (SQLException se)
  {
  System.err.println("SQLException: " + se.getMessage());
  System.err.println("SQLState:     " + se.getSQLState());
  System.err.println("VendorError:  " + se.getErrorCode());
  }


  /**
   * Remove backslash characters (\) from the given String.
   */
  public synchronized static String removeBackslashCharacters(String input)
  {
  StringBuffer buffer = new StringBuffer(input);
  StringBuffer output = new StringBuffer();
  for (int i = 0; i < buffer.length(); i++)
  {
    if (buffer.charAt(i) == '\\')
    ; // do nothing
    else
    output.append(buffer.charAt(i));
  }
  return output.toString();
  }


  /**
   * Remove ALL offensive characters (those that cause DB or HTML errors.
   */
  public synchronized static String removeBadCharacters(String input)
  {
  String output = null;
  output = Utils.escapeSingleQuotes(input);
  output = Utils.escapeDoubleQuotes(output);
//	output = Utils.removeHtmlTags(output);
  output = Utils.dealWithHTML(output);
  return output;
  }


  /**
   * Remove HTML symbols.
   */
  public synchronized static String removeHtmlSymbols(String input)
  {
  StringBuffer buffer = new StringBuffer(input);
  StringBuffer output = new StringBuffer();
  for (int i = 0; i < buffer.length(); i++)
  {
    if (buffer.charAt(i) == '>')
    output.append("&gt;");
    else if (buffer.charAt(i) == '<')
    output.append("&lt;");
    else
    output.append(buffer.charAt(i));
  }
  return output.toString();
  }


/**
 * Remove any HTML tags in string "ins".
 * Note: this only removes things enclosed between '<' and '>', and does not remove
 * other special HTML symbols, and does not discriminate about what is between the
 * '<' and the '>' symbols.
 */

  public synchronized static String removeHtmlTags(String ins)
  {
  StringBuffer finalStr = new StringBuffer();

  boolean intag = false;
  for (int i=0; i<ins.length(); i++)
  {
    if (!intag)
    {
    if (ins.charAt(i) == '<')
      intag = true;
    else if (ins.charAt(i) != '\t' && ins.charAt(i)!= '\n')
       finalStr.append(ins.charAt(i));
    }
    else if (ins.charAt(i) == '>')
    {
    intag=false;
    }
  }
  return finalStr.toString();
  }


  /**
   * Remove pipe characters (|) from the given String.
   */
  public synchronized static String removePipeCharacters(String input)
  {
  StringBuffer buffer = new StringBuffer(input);
  StringBuffer output = new StringBuffer();
  for (int i = 0; i < buffer.length(); i++)
  {
    if (buffer.charAt(i) == '|')
    ; // do nothing
    else
    output.append(buffer.charAt(i));
  }
  return output.toString();
  }


  /**
   *  Replace the \r\n character sequence with an HTML paragraph tag.
   */
  public synchronized static String replaceNewlineCharsWithParagraphTag(String s)
  {
  //StringBuffer sb = new StringBuffer("<br>");
  StringBuffer sb = new StringBuffer("<p>");
  int stringLength = s.length();
  for ( int i=0; i<stringLength; i++ )
  {
    char c = s.charAt(i);
/*
    if ( c == '\r' )
    {
    sb.append("\\R");
    }
    else if ( c == '\n' )
    {
    sb.append("\\N");
    }
    else if ( c == '\f' )
    {
    sb.append("\\F");
    }
*/
    if ( c == '\r' )
    {
    int j=i+1;
    if ( j<stringLength )
    {
      if ( s.charAt(j) == '\n' || s.charAt(j) == '\r' || s.charAt(j) == '\f' )
      {
      sb.append("<p>");
      i++;  // already accounted for this next character, so skip it
      }
      else
      {
      sb.append("<br>");
      }
    }
    }
    else
    {
    sb.append(c);
    }
  }
  sb.append("<p>");
  return new String(sb);
  }


  /**
   * A method to simplify the call to Thread.sleep.
   * Remember that sleepTime is in milliseconds.
   */
  public synchronized static void sleep(int sleepTime)
  {
  try
  {
    Thread.sleep(sleepTime);
  }
  catch (InterruptedException e)
  {
    // do nothing
  }
  }
}
