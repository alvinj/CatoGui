//package com.devdaily.kickstart.view;
//
//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;
//import javax.swing.event.*;
//import com.devdaily.kickstart.controller.KSMainFrameController;
//import com.devdaily.kickstart.Constants;
//
////@todo - remember curtain location
//public class KSMainFrame extends JFrame
//{
//  JPanel contentPane;
//  JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
//
//  private JScrollPane lhsScrollPane = new JScrollPane();
//  String[] modules = {Constants.LIST_MENU_DB2_APP_ITEM, 
//                      Constants.LIST_MENU_SOURCE_DATABASE_ITEM,
//                      Constants.LIST_MENU_FOO_ITEM,
//                      Constants.LIST_MENU_BAR_ITEM};
//  private JList lhsList = new JList(modules);
//  ListSelectionModel listSelectionModel;
//
//  private JPanel rhsWelcomePanel = new JPanel();
//  private JPanel statusBarPanel = new JPanel();
//  private JLabel statusBarLabel = new JLabel("welcome");
//
//  KSMainFrameController ksMainFrameController;
//
//  public KSMainFrame(KSMainFrameController ksMainFrameController) 
//  {
//    this.ksMainFrameController = ksMainFrameController;
//    this.setTitle(Constants.APPLICATION_NAME);
//    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
//    try 
//    {
//      lhsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//      lhsList.setFixedCellHeight(Constants.LIST_MENU_CELL_HEIGHT);
//      lhsList.setFixedCellWidth(Constants.LIST_MENU_CELL_WIDTH);
//      lhsScrollPane.setViewportView(lhsList);
//
//      listSelectionModel = lhsList.getSelectionModel();
//      listSelectionModel.addListSelectionListener(new MenuListSelectionHandler());
//      
//      splitPane.setLeftComponent(lhsScrollPane);
//      splitPane.setRightComponent(rhsWelcomePanel);
//
//      // add split pane to content pane
//      contentPane = (JPanel) this.getContentPane();
//      contentPane.setLayout(new BorderLayout());
//      contentPane.add(splitPane,BorderLayout.CENTER);
//      
//      statusBarLabel.setForeground(Color.GRAY);
//      statusBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//      statusBarPanel.add(statusBarLabel);
//      contentPane.add(statusBarPanel,BorderLayout.SOUTH);
//
//      initializeSplitPane();
//
//      //contentPane.add(jToolBar, BorderLayout.NORTH);
//    }
//    catch(Exception e) {
//      e.printStackTrace();
//    }
//  }
//  
//  public void setStatusBarText(String s)
//  {
//    statusBarLabel.setText(s);
//  }
//
//  private void initializeSplitPane()
//  {
//    splitPane.setDividerLocation(Constants.SPLIT_PANE_DIVIDER_LOCATION);
//    splitPane.setDividerSize(Constants.SPLIT_PANE_DIVIDER_SIZE);
//    splitPane.setOneTouchExpandable(Constants.SPLIT_PANE_ONE_TOUCH_EXPANDABLE);
//  }
//
//  protected void processWindowEvent(WindowEvent e) 
//  {
//    if (e.getID() == WindowEvent.WINDOW_CLOSING) 
//    {
//      ksMainFrameController.doProcessWindowClosingEvent();
//    }
//  }
//
//  private KSMainFrame() 
//  {
//  }
//  
//  void doHandleListMenuSelectionChanged(int selectedIndex)
//  {
//    //@todo -- total hack here
//    if (selectedIndex == 0)
//    {
//      ksMainFrameController.showModule(Constants.LIST_MENU_DB2_APP_ITEM);
//    } 
//    else if (selectedIndex == 1)
//    {
//      ksMainFrameController.showModule(Constants.LIST_MENU_SOURCE_DATABASE_ITEM);
//    }
//  }
//
//  // made this an inner class b/c i was having problems getting the selected index
//  // of the jlist using the ListSelectionEvent or the ListSelectionModel.
//  class MenuListSelectionHandler implements ListSelectionListener 
//  {
//    public void valueChanged(ListSelectionEvent e) 
//    {
//      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
//      if (!e.getValueIsAdjusting())
//      {
//        int index = lhsList.getSelectedIndex();
//        doHandleListMenuSelectionChanged(index);
//      }
//    }
//  }
//
//  public void showModule(JPanel panel)
//  {
//    splitPane.setRightComponent(panel);
//  }
//
//} // end of class
//
//
//
//
//
