package com.alvinalexander.cato.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * i couldn't get this code to work properly, and it's not very important to me, so i'm leaving it as is.
 * class-loading code from: stackoverflow.com/questions/11016092/how-to-load-classes-at-runtime-from-a-folder-or-jar
 */
public class ClassUtils {
  
    // TODO i couldn't get these to work easily to dynamically load the MySQL jar file
    // , and this isn't very important to me.
//  try {
//      //ClassUtils.loadAllClasses("/Users/Al/Projects/Scala/CatoGui/resources/mysql-connector-java-5.1.34-bin.jar")
//      //ClassUtils.loadOnlyMySql("/Users/Al/Projects/Scala/CatoGui/resources/mysql-connector-java-5.1.34-bin.jar")
//  } catch {
//      case t: Throwable => t.printStackTrace 
//  }


    public static void loadOnlyMySql(String pathToJar)
    throws Exception
    {
        try {
            URL urls [] = {};
            JarFileLoader cl = new JarFileLoader (urls);
            cl.addFile(pathToJar);
            cl.loadClass("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void loadAllClasses(String pathToJar) throws Exception
    {
      try {
          JarFile jarFile = new JarFile(pathToJar);
          Enumeration e = jarFile.entries();
    
          URL[] urls = { new URL("jar:file:" + pathToJar+"!/") };
          URLClassLoader cl = URLClassLoader.newInstance(urls);
          while (e.hasMoreElements()) {
              JarEntry je = (JarEntry) e.nextElement();
              //System.out.println(je.getName());
              if (je.isDirectory() || !je.getName().endsWith(".class")) {
                  continue;
              }
              if (je.getName().contains("hibernate") || je.getName().contains("jboss") || je.getName().contains("mchang")) {
                continue;
              }
              // -6 because of .class
              String className = je.getName().substring(0,je.getName().length()-6);
              className = className.replace('/', '.');
              try {
                Class c = cl.loadClass(className);
              } catch (Throwable t) {
                // do nothing
                t.printStackTrace();
              }
          }      
        } catch (Exception e) {
            throw e;
        }
    }
  
}



class JarFileLoader extends URLClassLoader
{
    public JarFileLoader (URL[] urls)
    {
        super (urls);
    }

    public void addFile (String path) throws MalformedURLException
    {
        String urlPath = "jar:file://" + path + "!/";
        addURL (new URL (urlPath));
    }

    public static void main (String args [])
    {
        try
        {
            System.out.println ("First attempt...");
            Class.forName ("org.gjt.mm.mysql.Driver");
        }
        catch (Exception ex)
        {
            System.out.println ("Failed.");
        }

        try
        {
            URL urls [] = {};

            JarFileLoader cl = new JarFileLoader (urls);
            cl.addFile ("/opt/mysql-connector-java-5.0.4/mysql-connector-java-5.0.4-bin.jar");
            System.out.println ("Second attempt...");
            cl.loadClass ("org.gjt.mm.mysql.Driver");
            System.out.println ("Success!");
        }
        catch (Exception ex)
        {
            System.out.println ("Failed.");
            ex.printStackTrace ();
        }
    }
}

