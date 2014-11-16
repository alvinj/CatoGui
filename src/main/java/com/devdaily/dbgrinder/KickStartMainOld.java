package com.devdaily.dbgrinder;

/**
 * Copyright DevDaily Interactive, 1999 and beyond. All Rights Reserved.
 */

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.*;
import com.devdaily.dbgrinder.controller.GenerateCRUDController;
import com.devdaily.dbgrinder.controller.GenerateTemplateController;
import com.devdaily.dbgrinder.controller.NewMainFrameController;
import com.devdaily.dbgrinder.model.Database;
import com.devdaily.dbgrinder.model.Project;
import com.devdaily.dbgrinder.view.DatabasePropertiesForm;
import com.devdaily.dbgrinder.view.ListAndButtonsForm;
import com.devdaily.dbgrinder.view.NewMainFrame;

/**
 * This is the class that gets it all started.
 */
public class KickStartMainOld
{
  private static Connection connection;
  private String driver = "";
  private String url = "";
  private String username = "";
  private String password = "";

  // controllers
  NewMainFrameController newMainFrameController;
  GenerateCRUDController generateCRUDController;
  GenerateTemplateController generateTemplateController;

  public static void main(String[] args)
  {
    initForMacOSXIfNeeded();
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    
    new KickStartMainOld(args);
  }

  private static void initForMacOSXIfNeeded()
  {
    String lcOSName = System.getProperty("os.name").toLowerCase();
    boolean IS_MAC = lcOSName.startsWith("mac os x");
    if (IS_MAC)
    {
      System.setProperty("apple.laf.useScreenMenuBar", "true");
      System.setProperty("com.apple.mrj.application.apple.menu.about.name", "KickStart");
    }
  }

  public KickStartMainOld(String[] args)
  {
    getDbInfoFromCommandLine(args);
    initializeProjectParameters();

    DatabasePropertiesForm lhs = new DatabasePropertiesForm();
    setDatabasePropertiesFormInitialValues(lhs);
    
    // controllers
    newMainFrameController = new NewMainFrameController();

    ListAndButtonsForm rhs = new ListAndButtonsForm();
    NewMainFrame f = new NewMainFrame(newMainFrameController,
        generateCRUDController,
        generateTemplateController,
        lhs,
        rhs);
    handleWindowClosingEvent(f);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
    
    // OLD STUFF
//    MainFrame frame = new MainFrame();
//    displayMainFrame(frame);
  }

  private void handleWindowClosingEvent(NewMainFrame f)
  {
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        //todo - should disconnect from the database here
        System.exit(0);
      }
    });
  }

  private void setDatabasePropertiesFormInitialValues(DatabasePropertiesForm lhs)
  {
    lhs.getUsernameTextField().setText(Project.getUsername());
    lhs.getPasswordTextField().setText(Project.getPassword());
    lhs.getDriverTextField().setText(Project.getDriver());
    lhs.getUrlTextField().setText(Project.getUrl());
  }

  public static void setConnection(Connection newConnection)
  {
    connection = newConnection;
  }

  public static Connection getConnection()
  {
    return connection;
  }

  private void getDbInfoFromCommandLine(String[] args)
  {
    if ( args.length < 2 )
    {
      showUsageStatement();
      System.exit(1);
    }
    driver = args[0];
    url    = args[1];
    if ( args.length == 4 )
    {
      username = args[2];
      password = args[3];
    }
    driver.trim();
    url.trim();
    username.trim();
    password.trim();
    System.err.println( "driver:   " + driver );
    System.err.println( "url:      " + url );
    System.err.println( "username: " + username );
    System.err.println( "password: " + password );
  }

  private void showUsageStatement()
  {
    System.err.println( "\nUsage: java com.devdaily.dbgrinder.Main driver url [username password]" );
    System.err.println( "Sample: java com.devdaily.dbgrinder.Main org.gjt.mm.mysql.Driver jdbc:mysql://localhost/devdaily\n" );
  }

  void initializeProjectParameters()
  {
    Project.setCurrentDatabaseTable("");
    Project.setDriver(driver);
    Project.setUrl(url);
    Project.setUsername(username);
    Project.setPassword(password);
    Project.connectToDatabase();
    Project.setMethodCreationMode(Project.CREATION_MODE_DEFAULT);
    Project.setCurrentProperties(getCurrentlyKnownProperties());
  }

  private Properties getCurrentlyKnownProperties()
  {
    Properties props = new Properties();
    props.setProperty("driver",           Project.getDriver());
    props.setProperty("url",              Project.getUrl());
    props.setProperty("username",         Project.getUsername());
    props.setProperty("password",         Project.getPassword());
    props.setProperty("package_name",     "YOUR_PACKAGE_HERE");
    //props.setProperty("home_dir",         "c:\\temp");
    //props.setProperty("class_output_dir", "c:\\temp");
    props.setProperty("database_type",    "NORMAL");
    //props.setProperty("table_list",       Project.getCurrentDatabaseTable());
    //props.setProperty("class_list",       Project.getCurrentDatabaseTable());
    //props.setProperty("desiredColumns",   null );
    return props;
  }

  void displayMainFrame(JFrame frame)
  {
    frame.pack();
    //frame.validate();

    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height)
    {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width)
    {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);
  }

}