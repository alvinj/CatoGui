//package com.devdaily.kickstart.controller;
//
//import java.awt.FileDialog;
//import java.awt.Frame;
//import java.awt.event.ActionEvent;
//import java.util.Properties;
//import java.util.Vector;
//import java.util.Iterator;
//import java.util.prefs.Preferences;
//import com.devdaily.dbgrinder.model.Db2Wapp;
//import com.devdaily.dbgrinder.model.DomainObject;
//import com.devdaily.dbgrinder.model.Project;
//import com.devdaily.dbgrinder.view.TextDisplayDialog;
//
//public class KSGenerateCodeFromTemplateController
//{
//
//  Db2AppModuleController db2AppModuleController;
//  Vector domainObjects;
//  String templateInputDir;
//  
//  Preferences prefs;
//  private static final String TEMPLATE_DIR = "";
//  
//  public KSGenerateCodeFromTemplateController(Db2AppModuleController db2AppModuleController)
//  {
//    this.db2AppModuleController = db2AppModuleController;
//    prefs = Preferences.userNodeForPackage(this.getClass());
//    templateInputDir = prefs.get(TEMPLATE_DIR, ""); 
//  }
//
//  void doTemplateAction(ActionEvent e)
//  {
//    Properties props = Project.getCurrentProperties();
//
//    // do this stuff here to populate _domainObjects
//    domainObjects = new Vector();
//    try
//    {
//      Db2Wapp db2Wapp = new Db2Wapp(props, Project.getMethodCreationMode(),domainObjects);
//      String currentFilename = null;
//      FileDialog fileDialog = new FileDialog((Frame) db2AppModuleController.getMainFrame());
//      fileDialog.setModal(true);
//      fileDialog.setMode(FileDialog.LOAD);
//      fileDialog.setTitle("Select a Template to Parse");
//      if (templateInputDir!=null && !templateInputDir.trim().equals(""))
//      {
//        fileDialog.setDirectory(templateInputDir);
//      }
//      fileDialog.setVisible(true);
//      
//      // after the dialog is used ...
//      if (fileDialog.getDirectory() != null)
//      {
//        // remember the last directory
//        templateInputDir = fileDialog.getDirectory();
//        prefs.put(TEMPLATE_DIR, templateInputDir);
//      }
//      String filename = fileDialog.getDirectory() + System.getProperty("file.separator") + fileDialog.getFile();
//      if (fileDialog.getFile() != null)
//      {
//        currentFilename = filename;
//        KSTemplateGenerator gen = new KSTemplateGenerator(domainObjects);
//        String output = gen.createOutputFromTemplate(currentFilename);
//
//        TextDisplayDialog tdd = new TextDisplayDialog();
//        tdd.setText(output);
//        tdd.pack();
//        tdd.setLocationRelativeTo(db2AppModuleController.getMainFrame());
//        tdd.setVisible(true);
//      }
//      else 
//      {
//        // ???
//      }
//      
//    }
//    catch (Exception e2)
//    {
//      System.err.println( "caught an exception, message is: " + e2.getMessage() );
//    }
//
////    String templateDir = "";
////    KSTemplateGenerator wapp = new KSTemplateGenerator(domainObjects, templateDir);
////    String output = wapp.createOutputFromTemplate(templateFileName);
//  }
//}
