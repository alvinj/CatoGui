//package com.devdaily.dbgrinder.controller;
//
//import java.awt.event.MouseEvent;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.Iterator;
//import java.util.Properties;
//import java.util.Vector;
//import javax.swing.JButton;
//import javax.swing.JOptionPane;
//import com.devdaily.dbgrinder.model.ColumnData;
//import com.devdaily.dbgrinder.model.Db2Wapp;
//import com.devdaily.dbgrinder.model.Project;
//import com.devdaily.dbgrinder.model.Table;
//import com.devdaily.dbgrinder.view.NewMainFrame;
//import com.devdaily.dbgrinder.view.TextDisplayDialog;
//
//public class NewMainFrameController
//{
//  private NewMainFrame newMainFrame;
//  private JButton generateBeanButton;
//  private JButton generateCRUDButton;
//  private JButton generateTemplateButton;
//  
//  // controllers
//  GenerateCRUDController generateCRUDController;
//
//  public void addListenerToGenerateBeanButton(JButton generateBeanButton)
//  {
//    generateBeanButton.addMouseListener(new java.awt.event.MouseAdapter()
//    {
//      public void mouseClicked(MouseEvent e)
//      {
//        generateBeanButtonMouseWasClicked(e);
//      }
//    });
//  }
//
//  public void addListenerToGenerateCRUDButton(JButton generateCRUDButton)
//  {
//    generateCRUDButton.addMouseListener(new java.awt.event.MouseAdapter()
//    {
//      public void mouseClicked(MouseEvent e)
//      {
//        generateCRUDButtonMouseWasClicked(e);
//      }
//    });
//  }
//
//  public void addListenerToGenerateTemplateButton(JButton generateTemplateButton)
//  {
//    generateTemplateButton.addMouseListener(new java.awt.event.MouseAdapter()
//    {
//      public void mouseClicked(MouseEvent e)
//      {
//        generateTemplateButtonMouseWasClicked(e);
//      }
//    });
//  }
//
//  private String getUsersDesiredColumns()
//  {
//    String catalog = "";
//    String schema  = "";
//    StringBuffer sb = new StringBuffer();
//    Vector v;
//    try
//    {
//      // todo: this code is duplicated elsewhere; refactor
//      v = Table.getColumnData(Project.getCurrentDatabaseTable(),Project.getDatabaseMetaData(),null,null,true);
//      Iterator it = v.iterator();
//      while ( it.hasNext() )
//      {
//        ColumnData cd = (ColumnData)it.next();
//        sb.append(cd.getName());
//      }
//    }
//    catch (SQLException e)
//    {
//      System.err.println(e.getMessage());
//      JOptionPane.showMessageDialog(newMainFrame, "Error occurred trying to get list of fields for table\n." + e.getMessage());
//    }
//    return sb.toString();
//  }
//
//  private void generateCRUDButtonMouseWasClicked(MouseEvent mouseEvent)
//  {
//    // always create a new controller; gets rid of my refresh/reset problems for now
//    generateCRUDController = null;
//    generateCRUDController = new GenerateCRUDController(this);
////    if (generateCRUDController==null)
////    {
////      generateCRUDController = new GenerateCRUDController(this);
////    }
////    else
////    {
////      generateCRUDController.refresh();
////    }
//    generateCRUDController.doGenerateCRUDButtonAction(mouseEvent);
//  }
//
//  private void generateTemplateButtonMouseWasClicked(MouseEvent mouseEvent)
//  {
//    JOptionPane.showMessageDialog(newMainFrame, "DO TEMPLATE ACTION");
//  }
//
//  private Properties getCurrentProperties()
//  {
//    Properties props = Project.getCurrentProperties();
//    props.setProperty("table_list",     Project.getCurrentDatabaseTable());
//    props.setProperty("class_list",     Project.getCurrentDatabaseTable());
//    props.setProperty("desiredColumns", getUsersDesiredColumns() );
//    return props;
//  }
//  
//  public String getCurrentlySelectedTableName()
//  {
//    return newMainFrame.getCurrentDatabaseTableName();
//  }
//  
//  public NewMainFrame getNewMainFrame()
//  {
//    return newMainFrame;
//  }
//
//  private void generateBeanButtonMouseWasClicked(MouseEvent mouseEvent)
//  {
//    String currentDatabaseTableName = newMainFrame.getCurrentDatabaseTableName();
//    if (currentDatabaseTableName == null || currentDatabaseTableName.trim().equals(""))
//    {
//      JOptionPane.showMessageDialog(newMainFrame, "Please select a database table name first.");
//      return;
//    }
//
//    Project.setCurrentDatabaseTable(currentDatabaseTableName);
//    Properties props = getCurrentProperties();
//    // keep the project in sync for now
//    Project.setCurrentProperties(props);
//    try
//    {
//      Db2Wapp db2Wapp = new Db2Wapp(props, Project.getMethodCreationMode());
//      String modelClass = db2Wapp.getModelClass();
//      TextDisplayDialog tdd = new TextDisplayDialog();
//      if ( modelClass == null || modelClass.trim().equals("") )
//      {
//        modelClass = "Please select a database table to derive the class from.";
//        tdd.setTitle( "Error generating class." );
//      }
//      else
//      {
//        tdd.setTitle("JavaBean (" + db2Wapp.getCurrentClassName() + ") generated from the current database table.");
//      }
//      tdd.setText(modelClass);
//      tdd.pack();
//      tdd.setLocationRelativeTo(newMainFrame);
//      tdd.setVisible(true);
//    }
//    catch (IOException ioe)
//    {
//      System.err.println( "ioe: " + ioe.getMessage() );
//      JOptionPane.showMessageDialog(newMainFrame, ioe.getMessage());
//    }
//    catch (SQLException se)
//    {
//      System.err.println( "se: " + se.getMessage() );
//      JOptionPane.showMessageDialog(newMainFrame, se.getMessage());
//    }
//    catch (ClassNotFoundException cnfe)
//    {
//      System.err.println( "cnfe: " + cnfe.getMessage() );
//      JOptionPane.showMessageDialog(newMainFrame, cnfe.getMessage());
//    }
//  }
//
//  public void setNewMainFrame(NewMainFrame newMainFrame)
//  {
//    this.newMainFrame = newMainFrame;
//  }
//}
