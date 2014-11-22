//package com.devdaily.kickstart.controller;
//
//import java.awt.Component;
//import java.awt.event.ComponentEvent;
//import com.devdaily.kickstart.Constants;
//import com.devdaily.kickstart.KickStartMain;
//import com.devdaily.kickstart.view.*;
//import java.util.prefs.*;
//import javax.swing.JPanel;
//
//public class KSMainFrameController
//{
//  private KickStartMain ksMainController;
//  private Db2AppModuleController db2AppModuleController;
//
//  KSMainFrame ksMainFrame;
//  JPanel db2AppMainPanel;
//  
//  //@todo - fix this, i shouldn't know about this
//  SCWMainPanel scwMainPanel;
//  
//  // save the frame size info as preferences
//  Preferences prefs;
//  private String THE_X      = "X";
//  private String THE_Y      = "Y";
//  private String THE_HEIGHT = "HEIGHT";
//  private String THE_WIDTH  = "WIDTH";
//
//  public KSMainFrameController(KickStartMain ksMainController)
//  {
//    this.ksMainController = ksMainController;
//    ksMainFrame = new KSMainFrame(this);
//
//    db2AppModuleController = new Db2AppModuleController(this);
//    db2AppMainPanel = db2AppModuleController.getMainPanel();
//
//    scwMainPanel = new SCWMainPanel();
//  }
//
//  public void showMainFrame()
//  {
//    setInitialSizeAndLocation();
//    // used for preferences support
//    ksMainFrame.addComponentListener(new MainFrameComponentAdapter(this));
//    ksMainFrame.setVisible(true);
//  }
//  
//  private void setInitialSizeAndLocation() throws NumberFormatException 
//  {
//    ksMainFrame.pack();
//    prefs = Preferences.userNodeForPackage(this.getClass());
//    String sX = prefs.get(THE_X, "0");
//    String sY = prefs.get(THE_Y, "0");
//    String sHeight = prefs.get(THE_HEIGHT, "400");
//    String sWidth = prefs.get(THE_WIDTH, "600");
//    int theX = Integer.parseInt(sX);
//    int theY = Integer.parseInt(sY);
//    int theHeight = Integer.parseInt(sHeight);
//    int theWidth = Integer.parseInt(sWidth);
//    ksMainFrame.setLocation(theX,theY);
//    ksMainFrame.setSize(theWidth,theHeight);
//  }
//
//  /** preferences support -- component moved */
//  void ksMainFrameComponentMoved(ComponentEvent e) 
//  {
//    updateDimensions();
//  }
//
//  /** preferences support -- component resized */
//  void ksMainFrameComponentResized(ComponentEvent e) 
//  {
//    updateDimensions();
//  }
//
//  private void updateDimensions() {
//    int x = ksMainFrame.getX();
//    int y = ksMainFrame.getY();
//    int height = ksMainFrame.getHeight();
//    int width = ksMainFrame.getWidth();
//    prefs.put(THE_X, Integer.toString(x));
//    prefs.put(THE_Y, Integer.toString(y));
//    prefs.put(THE_HEIGHT, Integer.toString(height));
//    prefs.put(THE_WIDTH, Integer.toString(width));
//  }
//
//  public void doProcessWindowClosingEvent()
//  {
//    ksMainController.doSystemExit();
//  }
//
//  public void showModule(String moduleName)
//  {
//    // @todo -- total hack, part 2
//    if (moduleName.equals(Constants.LIST_MENU_DB2_APP_ITEM))
//    {
//      // show the db2App panel on the right side
//      ksMainFrame.showModule(db2AppMainPanel);
//    }
//    else if (moduleName.equals(Constants.LIST_MENU_SOURCE_DATABASE_ITEM))
//    {
//      ksMainFrame.showModule(scwMainPanel);
//    }
//  }
//  
//  public void setStatusBarText(String s)
//  {
//    ksMainFrame.setStatusBarText(s);
//  }
//
//  public Component getMainFrame()
//  {
//    return this.ksMainFrame;
//  }
//
//}
//
//class MainFrameComponentAdapter extends java.awt.event.ComponentAdapter 
//{
//  KSMainFrameController adaptee;
//
//  MainFrameComponentAdapter(KSMainFrameController adaptee) {
//    this.adaptee = adaptee;
//  }
//  public void componentMoved(ComponentEvent e) {
//    adaptee.ksMainFrameComponentMoved(e);
//  }
//  public void componentResized(ComponentEvent e) {
//    adaptee.ksMainFrameComponentResized(e);
//  }
//}
//
//
//
