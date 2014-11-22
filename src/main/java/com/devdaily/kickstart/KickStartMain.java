//package com.devdaily.kickstart;
//
///**
// * The new main kickstart controller.
// */
//
//import javax.swing.JFrame;
//import javax.swing.UIManager;
//import javax.swing.WindowConstants;
//import java.awt.*;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.sql.*;
//import java.util.*;
//import com.devdaily.dbgrinder.controller.GenerateCRUDController;
//import com.devdaily.dbgrinder.controller.GenerateTemplateController;
//import com.devdaily.dbgrinder.controller.NewMainFrameController;
//import com.devdaily.dbgrinder.model.Project;
//import com.devdaily.dbgrinder.view.DatabasePropertiesForm;
//import com.devdaily.dbgrinder.view.ListAndButtonsForm;
//import com.devdaily.dbgrinder.view.NewMainFrame;
//import com.devdaily.kickstart.controller.KSMainFrameController;
//
///**
// * This is the class that gets it all started.
// */
//public class KickStartMain
//{
//  private static Connection connection;
//  private String driver = "";
//  private String url = "";
//  private String username = "";
//  private String password = "";
//
//  // controllers
//  KSMainFrameController ksMainFrameController;
//  GenerateCRUDController generateCRUDController;
//  GenerateTemplateController generateTemplateController;
//  
//  /*
//   * startup
//   * initialize controllers (get preferences data)
//   * set up session
//   * build mainframe
//   * show mainframe
//   * 
//   */
//
//  public static void main(String[] args)
//  {
//    initForMacOSXIfNeeded();
//    try
//    {
//      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//    }
//    catch(Exception e)
//    {
//      e.printStackTrace();
//    }
//    
//    new KickStartMain();
//  }
//
//  public KickStartMain()
//  {
//    // controllers
//    ksMainFrameController = new KSMainFrameController(this);
//    ksMainFrameController.showMainFrame();
//  }
//
//  private static void initForMacOSXIfNeeded()
//  {
//    String lcOSName = System.getProperty("os.name").toLowerCase();
//    boolean IS_MAC = lcOSName.startsWith("mac os x");
//    if (IS_MAC)
//    {
//      System.setProperty("apple.laf.useScreenMenuBar", "true");
//      System.setProperty("com.apple.mrj.application.apple.menu.about.name", Constants.APPLICATION_NAME);
//    }
//  }
//
//  private void handleWindowClosingEvent(NewMainFrame f)
//  {
//    f.addWindowListener(new WindowAdapter() {
//      public void windowClosing(WindowEvent we) {
//        //todo - should disconnect from the database here
//        System.exit(0);
//      }
//    });
//  }
//
//  public void doSystemExit()
//  {
//    System.exit(0);
//  }
//
//}