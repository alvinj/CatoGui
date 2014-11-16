package com.devdaily.utils;

public class Debug
{
  public static boolean DEBUG = true;

  public static void println(String line)
  {
    if (DEBUG)
    {
      System.err.println( line );
    }
  }
}