package com.devdaily.web;

/**
 * Copyright:    Copyright DevDaily Interactive, 2001. All Rights Reserved.
 * @author       Al Alexander
 * @version      1.0
 */

public class RequestParameter
{

  /**
   * Convert the given parameter into a boolean.
   */
  public static boolean getBoolean (String parameter)
  throws NullPointerException
  {
    Boolean b = new Boolean(parameter.trim());
    return b.booleanValue();
  }

  /**
   * Convert the given parameter into a byte.
   */
  public static byte getByte (String parameter)
  throws NumberFormatException, NullPointerException
  {
    return Byte.parseByte(parameter);
  }

  /**
   * Convert the given parameter into a char.
   */
  public static char getChar (String parameter)
  throws NullPointerException
  {
    return parameter.charAt(0);
  }

  /**
   * Convert the given parameter into a double.
   */
  public static double getDouble (String parameter)
  throws NumberFormatException, NullPointerException
  {
    return Double.parseDouble(parameter);
  }

  /**
   * Convert the given parameter into an float.
   */
  public static float getFloat (String parameter)
  throws NumberFormatException, NullPointerException
  {
    return Float.parseFloat(parameter);
  }

  /**
   * Convert the given parameter into an int.
   */
  public static int getInt (String parameter)
  throws NumberFormatException, NullPointerException
  {
    return Integer.parseInt(parameter);
  }

  /**
   * Convert the given parameter into a long.
   */
  public static long getLong (String parameter)
  throws NumberFormatException, NullPointerException
  {
    return Long.parseLong(parameter);
  }

  /**
   * Convert the given parameter into a short.
   */
  public static short getShort (String parameter)
  throws NumberFormatException, NullPointerException
  {
    return Short.parseShort(parameter);
  }

  public static void main (String[] args)
  {
    try
    {
      String nullString = null;

      int anInt = RequestParameter.getInt( "5" );

      // BOOLEAN TESTS
      System.out.println( "boolean test: " + RequestParameter.getBoolean( "TRUE" ) );
      System.out.println( "boolean test: " + RequestParameter.getBoolean( "true" ) );
      System.out.println( "boolean test: " + RequestParameter.getBoolean( "FALSE" ) );
      System.out.println( "boolean test: " + RequestParameter.getBoolean( "false" ) );
      //System.out.println( "boolean test: " + RequestParameter.getBoolean( nullString ) );

      // CHAR TESTS
      System.out.println( "char test: " + RequestParameter.getChar( "test" ) );
      //System.out.println( "char test: " + RequestParameter.getChar( nullString ) );

      // FLOAT TESTS
      System.out.println( "float test: " + RequestParameter.getFloat( "123" ) );
      System.out.println( "float test: " + RequestParameter.getFloat( "123.000" ) );
      System.out.println( "float test: " + RequestParameter.getFloat( "-123.000" ) );
      //System.out.println( "float test: " + RequestParameter.getFloat( nullString ) );

      // BYTE TESTS
      System.out.println( "byte test: " + RequestParameter.getByte( "127" ) );
      System.out.println( "byte test: " + RequestParameter.getByte( "-128" ) );
      // next two blow the method with a NumberFormatException
      //System.out.println( "byte test: " + RequestParameter.getByte( "128" ) );
      //System.out.println( "byte test: " + RequestParameter.getByte( "-129" ) );
    }
    catch (NumberFormatException nfe)
    {
      System.err.println( "oops, NumberFormatException: " + nfe.getMessage() );
    }
    catch (NullPointerException npe)
    {
      System.err.println( "oops, NullPointerException: " + npe.getMessage() );
    }
  }

}