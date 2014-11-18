package com.alvinalexander.cato.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * My devdaily.com file utilities (reading and writing to files in Java).
 */
public class JavaFileUtils {

  /**
   * Opens and reads a file, and returns the contents as one String.
   */
  public static String readFileAsString(String filename) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line;
    StringBuilder sb = new StringBuilder();
    while ((line = reader.readLine()) != null) {
      sb.append(line + "\n");
    }
    reader.close();
    return sb.toString();
  }

  /**
   * Open and read a file, and return the lines in the file as a list of
   * Strings.
   */
  public static List<String> readFileAsListOfStrings(String filename) throws Exception {
    List<String> records = new ArrayList<String>();
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line;
    while ((line = reader.readLine()) != null) {
      records.add(line);
    }
    reader.close();
    return records;
  }

  /**
   * Save the given text to the given filename.
   * 
   * @param canonicalFilename
   *          Like /Users/al/foo/bar.txt
   * @param text
   *          All the text you want to save to the file as one String.
   * @throws IOException
   */
  public static void writeFile(String canonicalFilename, String text) throws IOException {
    File file = new File(canonicalFilename);
    BufferedWriter out = new BufferedWriter(new FileWriter(file));
    out.write(text);
    out.close();
  }

  /**
   * Write an array of bytes to a file. Presumably this is binary data; for
   * plain text use the writeFile method.
   */
  public static void writeFileAsBytes(String fullPath, byte[] bytes) throws IOException {
    OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fullPath));
    InputStream inputStream = new ByteArrayInputStream(bytes);
    int token = -1;

    while ((token = inputStream.read()) != -1) {
      bufferedOutputStream.write(token);
    }
    bufferedOutputStream.flush();
    bufferedOutputStream.close();
    inputStream.close();
  }
  
  /**
   * Important note: This method hasn't been tested yet, and was originally written a long, long time ago.
   * @param canonicalFilename
   * @param text
   * @throws IOException
   */
  public static void appendToFile(String canonicalFilename, String text) throws IOException {
    BufferedWriter bw = null;
    try {
      bw = new BufferedWriter(new FileWriter(canonicalFilename, true));
      bw.write(text);
      bw.flush();
    } catch (IOException ioe) {
      throw ioe;
    } finally { // always close the file
      if (bw != null)
        try {
          bw.close();
        } catch (IOException ioe2) {
          // ignore it
        }
    }

  }

}


