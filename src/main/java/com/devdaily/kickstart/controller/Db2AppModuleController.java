package com.devdaily.kickstart.controller;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
//import com.apple.component.Component;
import com.devdaily.dbgrinder.model.Db2Wapp;
import com.devdaily.dbgrinder.model.Project;
import com.devdaily.dbgrinder.view.TextDisplayDialog;
import com.devdaily.kickstart.view.Db2AppConfigPanel;
import com.devdaily.kickstart.view.Db2AppGenCodePanel;
import com.devdaily.kickstart.view.Db2AppMainPanel;

/**
 * @todo Need to add "disconnect" capability
 *
 */
public class Db2AppModuleController
{

  // we know our "parent" controller
  KSMainFrameController ksMainFrameController;
  KSGenerateCRUDController ksGenerateCRUDController;
  KSGenerateCodeFromTemplateController ksGenerateCodeFromTemplateController;

  // panels we know about
  Db2AppMainPanel db2AppMainPanel;
  Db2AppConfigPanel db2AppConfigPanel;
  Db2AppGenCodePanel db2AppGenCodePanel;

  // controls we know about
  JButton connectToDatabaseButton;
  JButton generateBeanButton;
  JButton generateCRUDButton;
  JButton generateCodeFromTemplateButton;
  JTextField driverTextField;
  JTextField urlTextField;
  JTextField usernameTextField;
  JTextField passwordTextField;
  
  JList listOfTables;
  DefaultListModel databaseTablesListModel = new DefaultListModel();
  DatabaseTableListSelectionHandler listSelectionListener = new DatabaseTableListSelectionHandler();

  String driver;
  String url;
  String username;
  String password;
  String templatesDirectory;
  String currentDatabaseTableName;
  
  Properties currentProjectProperties;
  Connection connection;

  public Db2AppModuleController(KSMainFrameController ksMainFrameController)
  {
    this.ksMainFrameController = ksMainFrameController;
    // don't connect until needed
    this.db2AppMainPanel = new Db2AppMainPanel(this);
    //ksGenerateCRUDController = new KSGenerateCRUDController(this);
    ksGenerateCodeFromTemplateController = new KSGenerateCodeFromTemplateController(this);

    db2AppConfigPanel = db2AppMainPanel.getDb2AppConfigPanel();
    db2AppGenCodePanel = db2AppMainPanel.getDb2AppGenCodePanel();

    connectToControls();
    addListenersToControls();

    //@todo - defaults, for now
    driverTextField.setText("org.gjt.mm.mysql.Driver");
    urlTextField.setText("jdbc:mysql://localhost/webapp");
    usernameTextField.setText("root");
    passwordTextField.setText("");

  }
  
  public java.awt.Component getMainFrame()
  {
    return ksMainFrameController.getMainFrame();
  }

  public void doConnectToDatabaseAction()
  {
    // get info from textfields
    driver = driverTextField.getText();
    url = urlTextField.getText();
    username = usernameTextField.getText();
    password = passwordTextField.getText();
    if (!validateFields()) return;

    // connect to db
    doOldWayOfConnectingToDatabase();
    ksMainFrameController.setStatusBarText("probably connected to database");

    // @todo - can only assume this works okay for now
    db2AppGenCodePanel.enablePanel(true);
    
    // update the jlist
    Collection listOfTableNames = Project.getListOfTableNames();
    Iterator it = listOfTableNames.iterator();
    while (it.hasNext())
    {
      String currentTableName = (String) it.next();
      databaseTablesListModel.addElement(currentTableName);
    }
    listOfTables.setModel(databaseTablesListModel);
  }

  void doOldWayOfConnectingToDatabase()
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

  private boolean validateFields()
  {
    if (driver.trim().equals(""))
    {
      JOptionPane.showMessageDialog(ksMainFrameController.getMainFrame(), "I'm going to need that Driver.");
      return false;
    }
    if (url.trim().equals(""))
    {
      JOptionPane.showMessageDialog(ksMainFrameController.getMainFrame(), "I'm going to need that URL.");
      return false;
    }
    return true;
  }

  protected void doGenerateCodeFromTemplateButtonAction()
  {
    ksGenerateCodeFromTemplateController.doTemplateAction(null);
  }

  protected void doGenerateCRUDButtonAction()
  {
    // put this off until needed b/c it really wants a current db table name
    if (ksGenerateCRUDController == null)
    {
      ksGenerateCRUDController = new KSGenerateCRUDController(this);
    }
    ksGenerateCRUDController.doGenerateCRUDButtonAction(null);
  }

  private void doGenerateBeanButtonAction()
  {
    //@todo - Could refactor this into its own controller
    Properties props = currentProjectProperties;
    try
    {
      Db2Wapp db2Wapp = new Db2Wapp(props, Project.getMethodCreationMode());
      String modelClass = db2Wapp.getModelClass();
      TextDisplayDialog tdd = new TextDisplayDialog();
      if ( modelClass == null || modelClass.trim().equals("") )
      {
        modelClass = "Please select a database table to derive the class from.";
        tdd.setTitle( "Error generating class." );
      }
      else
      {
        tdd.setTitle("JavaBean (" + db2Wapp.getCurrentClassName() + ") generated from the current database table.");
      }
      tdd.setText(modelClass);
      tdd.pack();
      tdd.setLocationRelativeTo(ksMainFrameController.getMainFrame());
      tdd.setVisible(true);
    }
    catch (IOException ioe)
    {
      System.err.println( "ioe: " + ioe.getMessage() );
    }
    catch (SQLException se)
    {
      System.err.println( "se: " + se.getMessage() );
    }
    catch (ClassNotFoundException cnfe)
    {
      System.err.println( "cnfe: " + cnfe.getMessage() );
    }
  }

  private void addListenersToControls()
  {
    connectToDatabaseButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        doConnectToDatabaseAction();
      }
    });
    generateBeanButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        doGenerateBeanButtonAction();
      }
    });
    generateCRUDButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        doGenerateCRUDButtonAction();
      }
    });
    generateCodeFromTemplateButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        doGenerateCodeFromTemplateButtonAction();
      }
    });
    listOfTables.addListSelectionListener(listSelectionListener);
  }

  private void connectToControls()
  {
    connectToDatabaseButton = db2AppMainPanel.getConnectButton();
    generateBeanButton = db2AppMainPanel.getGenerateBeanButton();
    generateCRUDButton = db2AppMainPanel.getgenerateCRUDButton();
    generateCodeFromTemplateButton = db2AppMainPanel.getGenerateCodeFromTemplateButton();
    driverTextField = db2AppMainPanel.getDriverTextField();
    urlTextField = db2AppMainPanel.getURLTextField();
    usernameTextField = db2AppMainPanel.getUsernameTextField();
    passwordTextField = db2AppMainPanel.getPasswordTextField();
    listOfTables = db2AppMainPanel.getTableList();
  }

  public JPanel getMainPanel()
  {
    return db2AppMainPanel;
  }
  
  public void setCurrentDatabaseTableName(String name)
  {
    // kludge; this is null if i grab it too early
    currentProjectProperties = Project.getCurrentProperties();
    currentDatabaseTableName = name;
    currentProjectProperties.setProperty("table_list", currentDatabaseTableName);
    currentProjectProperties.setProperty("class_list", currentDatabaseTableName);
    ksMainFrameController.setStatusBarText("current table: " + currentDatabaseTableName);
  }
  

  /**
   * Listen to selections on the JList of database table names.
   */
  class DatabaseTableListSelectionHandler implements ListSelectionListener
  {
    private String currentSelection;

    public DatabaseTableListSelectionHandler()
    {
    }
    
    public void valueChanged(ListSelectionEvent e)
    {
      JList jlist = (JList) e.getSource();
      int index = jlist.getSelectedIndex();
      currentSelection = (String)jlist.getSelectedValue();
      setCurrentDatabaseTableName(currentSelection);
    }
    
    public String getCurrentSelection()
    {
      return this.currentSelection;
    }
  }


  public String getCurrentlySelectedTableName()
  {
    return currentDatabaseTableName;
  }

} // end of class
