//package com.devdaily.dbgrinder;
//
///**
// * Copyright DevDaily Interactive, 1999 and beyond. All Rights Reserved.
// */
//
//import java.awt.*;
//import java.awt.event.*;
//import java.io.IOException;
//import java.io.File;
//import java.sql.*;
//import java.util.*;
//import javax.swing.*;
//import javax.swing.tree.*;
//import com.borland.jbcl.layout.*;
//import com.devdaily.dbgrinder.model.ColumnData;
//import com.devdaily.dbgrinder.model.Project;
//import com.devdaily.dbgrinder.model.Table;
//import com.devdaily.dbgrinder.model.Db2Wapp;
//import com.devdaily.dbgrinder.model.WappGenerator;
//import com.devdaily.dbgrinder.view.TextDisplayDialog;
//import com.devdaily.dbgrinder.view.EditTextFieldDialog;
//
//
//public class MainFrame extends JFrame
//{
//  JPanel contentPane;
//  JMenuBar jMenuBar1 = new JMenuBar();
//  JMenu jMenuFile = new JMenu();
//  JMenuItem jMenuFileExit = new JMenuItem();
//  JMenu jMenuHelp = new JMenu();
//  JMenuItem jMenuHelpAbout = new JMenuItem();
//  ImageIcon image1;
//  ImageIcon image2;
//  ImageIcon image3;
//  JLabel statusBar = new JLabel();
//  BorderLayout borderLayout1 = new BorderLayout();
//  JPanel databasePanel = new JPanel();
//  BorderLayout borderLayout2 = new BorderLayout();
//  JPanel mainPanel = new JPanel();
//  JScrollPane treePane = new JScrollPane();
//
//  JPanel jpanelOnLeft = new JPanel();
//  BorderLayout jpanelOnLeftBorderLayout = new BorderLayout();
//  JPanel step1LabelJPanel = new JPanel();
//  JLabel step1JLabel = new JLabel();
//
//  JTree treeOfDatabaseTableNames = new JTree();
//  BorderLayout borderLayout3 = new BorderLayout();
//  JPanel sqlComboBoxPanel = new JPanel();
//  JPanel cardLayoutPanel = new JPanel();
//  JComboBox sqlComboBox = new JComboBox();
//  JButton generateMethodButton = new JButton();
//  CardLayout cardLayout1 = new CardLayout();
//  JPanel selectInsertUpdatePanel = new JPanel();
//  JPanel deletePanel = new JPanel();
//  GridLayout gridLayout1 = new GridLayout();
//  JPanel fieldOptionsPanel = new JPanel();
//  JPanel fieldsSelectedPanel = new JPanel();
//  XYLayout xYLayout1 = new XYLayout();
//  JLabel fieldOptionsLabel = new JLabel();
//  JList availableFieldsList = new JList();
//  JButton copyAllAvailableFieldsToSelected = new JButton();
//  XYLayout xYLayout2 = new XYLayout();
//  JLabel fieldsSelectedLabel = new JLabel();
//  JList fieldsSelectedList = new JList();
//  JMenu editMenu = new JMenu();
//  JMenuItem driverMenuItem = new JMenuItem();
//  JMenuItem urlMenuItem = new JMenuItem();
//  JMenuItem usernameMenuItem = new JMenuItem();
//  JMenuItem passwordMenuItem = new JMenuItem();
//  JScrollPane availableFieldsScrollPane = new JScrollPane();
//  JScrollPane selectedFieldsScrollPane = new JScrollPane();
//  JLabel jLabel1 = new JLabel();
//  FlowLayout flowLayout1 = new FlowLayout();
//  JTabbedPane jTabbedPane = new JTabbedPane();
//  JPanel dataObjectPanel = new JPanel();
//  JPanel javaBeanPanel = new JPanel();
//  JPanel jspPanel = new JPanel();
//  JPanel strutsPanel = new JPanel();
//  XYLayout xYLayout3 = new XYLayout();
//  JButton generateBeanButton1 = new JButton();
//  XYLayout xYLayout4 = new XYLayout();
//  JLabel jLabel2 = new JLabel();
//  JLabel jLabel3 = new JLabel();
//  JLabel jLabel4 = new JLabel();
//  JLabel jLabel5 = new JLabel();
//  JLabel jLabel6 = new JLabel();
//  JLabel jLabel7 = new JLabel();
//  XYLayout xYLayout5 = new XYLayout();
//  JButton generateJSPButton = new JButton();
//  //JButton generateMethodButton = new JButton();
//
//  // added by aja
//  //Vector fieldsSelectedVector = new Vector();
//
//  // db2wapp fields
//  //String _homeDir = "c:\\wapp";
//  String _classOutputDir = "c:\\wapp\\classes-out";
//  String _jspOutputDir = "";
//  String _jspTemplateDir = "";
//  Vector _domainObjects = new Vector();
//  String _prefix = "";   // pre-pended to jsp filenames
//  String _packageName = "com.devdaily.poop";
//
//  // File objects we need to write the model, container, and controllers to
//  File _modelFolder;
//  File _containerFolder;
//  File _controllerFolder;
//  JLabel jspTemplateLabel = new JLabel();
//  JLabel jLabel8 = new JLabel();
//  JTextField jspTemplateDirectoryTextField = new JTextField();
//  JTextField jspOutputDirectoryTextField = new JTextField();
//  JButton jspTemplateDirectoryButton = new JButton();
//  JButton jspOutputDirectoryButton1 = new JButton();
//  JLabel jLabel9 = new JLabel();
//  JLabel jLabel10 = new JLabel();
//  JLabel jLabel11 = new JLabel();
//
//  JFileChooser jFileChooser = new JFileChooser();
//  File mostRecentTemplateDirectory;
//  File mostRecentOutputDirectory;
//  XYLayout xYLayout6 = new XYLayout();
//  JLabel jLabel12 = new JLabel();
//  JLabel doCurrentTableLabel = new JLabel();
//  JTextField currentDbTableTextField = new JTextField();
//  JTextField jsp_currentDbTableTextField = new JTextField();
//  JLabel jspCurrentTableLabel = new JLabel();
//  JTextField bean_currentDbTableTextField = new JTextField();
//  JLabel bean_CurrentTableLabel = new JLabel();
//
//
//  /**Construct the frame*/
//  public MainFrame()
//  {
//    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
//    try
//    {
//      jbInit();
//    }
//    catch(Exception e)
//    {
//      e.printStackTrace();
//    }
//  }
//  /**Component initialization*/
//  private void jbInit() throws Exception
//  {
//    loadImages();
//    //setIconImage(Toolkit.getDefaultToolkit().createImage(MainFrame.class.getResource("[Your Icon]")));
//    contentPane = (JPanel) this.getContentPane();
//    contentPane.setLayout(borderLayout1);
//    this.setSize(new Dimension(514, 302));
//    this.setTitle("DevDaily.com KickStart");
//    statusBar.setText(" ");
//    jMenuFile.setText("File");
//    jMenuFileExit.setText("Exit");
//    jMenuFileExit.addActionListener(new ActionListener()
//    {
//      public void actionPerformed(ActionEvent e)
//      {
//        jMenuFileExit_actionPerformed(e);
//      }
//    });
//    jMenuHelp.setText("Help");
//    jMenuHelpAbout.setText("About");
//    jMenuHelpAbout.addActionListener(new ActionListener()
//    {
//      public void actionPerformed(ActionEvent e)
//      {
//        jMenuHelpAbout_actionPerformed(e);
//      }
//    });
//    databasePanel.setBackground(Color.pink);
//    databasePanel.setLayout(borderLayout2);
//    mainPanel.setBackground(new Color(124, 172, 172));
//    mainPanel.setLayout(borderLayout3);
//    sqlComboBoxPanel.setLayout(flowLayout1);
//
//    generateMethodButton.setText("Generate \"Data Object\" Method");
//    generateMethodButton.addMouseListener(new java.awt.event.MouseAdapter()
//    {
//      public void mouseClicked(MouseEvent e)
//      {
//        generateMethodButton_mouseClicked(e);
//      }
//    });
//
//    cardLayoutPanel.setLayout(cardLayout1);
//    selectInsertUpdatePanel.setBackground(Color.orange);
//    selectInsertUpdatePanel.setLayout(gridLayout1);
//    gridLayout1.setColumns(2);
//    fieldOptionsPanel.setBackground(new Color(182, 192, 192));
//    fieldOptionsPanel.setLayout(xYLayout1);
//    fieldOptionsLabel.setText("(3) Select Available Fields");
//    copyAllAvailableFieldsToSelected.setText("Copy All >>");
//    copyAllAvailableFieldsToSelected.addMouseListener(new java.awt.event.MouseAdapter()
//    {
//      public void mouseClicked(MouseEvent e)
//      {
//        copyAllAvailableFieldsToSelected_mouseClicked(e);
//      }
//    });
//    fieldsSelectedPanel.setLayout(xYLayout2);
//    fieldsSelectedLabel.setText("Selected Fields");
//    //
//    populateTreeOfTableNames();
//    availableFieldsList.addMouseListener(new java.awt.event.MouseAdapter()
//    {
//      public void mouseClicked(MouseEvent e)
//      {
//        availableFieldsList_mouseClicked(e);
//      }
//    });
//    fieldsSelectedList.addMouseListener(new java.awt.event.MouseAdapter()
//    {
//      public void mouseClicked(MouseEvent e)
//      {
//        fieldsSelectedList_mouseClicked(e);
//      }
//    });
//    driverMenuItem.setText("Driver...");
//    driverMenuItem.addMouseListener(new java.awt.event.MouseAdapter()
//    {
//      public void mouseClicked(MouseEvent e)
//      {
//        driverMenuItem_mouseClicked(e);
//      }
//      public void mousePressed(MouseEvent e)
//      {
//        driverMenuItem_mousePressed(e);
//      }
//    });
//    editMenu.setText("Edit");
//    urlMenuItem.setText("URL...");
//    urlMenuItem.addMouseListener(new java.awt.event.MouseAdapter()
//    {
//      public void mouseClicked(MouseEvent e)
//      {
//        urlMenuItem_mouseClicked(e);
//      }
//      public void mousePressed(MouseEvent e)
//      {
//        urlMenuItem_mousePressed(e);
//      }
//    });
//    usernameMenuItem.setText("Username...");
//    usernameMenuItem.addMouseListener(new java.awt.event.MouseAdapter()
//    {
//      public void mousePressed(MouseEvent e)
//      {
//        usernameMenuItem_mousePressed(e);
//      }
//      public void mouseClicked(MouseEvent e)
//      {
//        usernameMenuItem_mouseClicked(e);
//      }
//    });
//    passwordMenuItem.setText("Password...");
//    passwordMenuItem.addMouseListener(new java.awt.event.MouseAdapter()
//    {
//      public void mouseClicked(MouseEvent e)
//      {
//        passwordMenuItem_mouseClicked(e);
//      }
//      public void mousePressed(MouseEvent e)
//      {
//        passwordMenuItem_mousePressed(e);
//      }
//    });
//
//    jLabel1.setText("(2) Select a method to generate: ");
//    flowLayout1.setAlignment(FlowLayout.LEFT);
//    fieldsSelectedPanel.setBackground(new Color(192, 182, 192));
//    step1LabelJPanel.setBackground(new Color(192, 192, 202));
//    javaBeanPanel.setLayout(xYLayout3);
//    generateBeanButton1.addMouseListener(new java.awt.event.MouseAdapter()
//    {
//      public void mouseClicked(MouseEvent e)
//      {
//        generateBeanButton1_mouseClicked(e);
//      }
//    });
//    generateBeanButton1.setText("Generate JavaBean");
//    dataObjectPanel.setLayout(xYLayout4);
//    jTabbedPane.setToolTipText("");
//    jLabel2.setToolTipText("");
//    jLabel2.setText("Click this button to generate a data object method");
//    jLabel3.setText("corresponding to the SQL INSERT/UPDATE/DELETE/SELECT ");
//    jLabel3.setToolTipText("");
//    jLabel4.setToolTipText("");
//    jLabel4.setText("option currently selected.");
//    jLabel5.setText("currently selected.");
//    jLabel5.setToolTipText("");
//    jLabel6.setToolTipText("");
//    jLabel6.setText("corresponding to the database table ");
//    jLabel7.setText("Click this button to generate a JavaBean ");
//    jLabel7.setToolTipText("");
//    jspPanel.setLayout(xYLayout5);
//    generateJSPButton.setText("Generate From Templates");
//    generateJSPButton.addActionListener(new java.awt.event.ActionListener()
//    {
//      public void actionPerformed(ActionEvent e)
//      {
//        generateJSPButton_actionPerformed(e);
//      }
//    });
//    jspTemplateLabel.setText("Directory containing your templates:");
//    jLabel8.setText("Directory to write output files to:");
//    jspTemplateDirectoryButton.setText("...");
//    jspTemplateDirectoryButton.addActionListener(new java.awt.event.ActionListener()
//    {
//      public void actionPerformed(ActionEvent e)
//      {
//        jspTemplateDirectoryButton_actionPerformed(e);
//      }
//    });
//    jspOutputDirectoryButton1.setText("...");
//    jspOutputDirectoryButton1.addActionListener(new java.awt.event.ActionListener()
//    {
//      public void actionPerformed(ActionEvent e)
//      {
//        jspOutputDirectoryButton1_actionPerformed(e);
//      }
//    });
//    jLabel9.setText("Fill out these fields and press the button below to read your ");
//    jLabel10.setText("templates (JSPs, etc.) and generate output files.");
//    jLabel11.setText("(File names will correspond to the current database.)");
//    strutsPanel.setLayout(xYLayout6);
//    jLabel12.setText("Sorry, the Struts Form and Action options are not available yet.");
//    doCurrentTableLabel.setText("Current database table:");
//    currentDbTableTextField.setEditable(false);
//    dataObjectPanel.addComponentListener(new java.awt.event.ComponentAdapter()
//    {
//      public void componentShown(ComponentEvent e)
//      {
//        dataObjectPanel_componentShown(e);
//      }
//    });
//    jsp_currentDbTableTextField.setEditable(false);
//    jspCurrentTableLabel.setText("Current database table:");
//    jspPanel.addComponentListener(new java.awt.event.ComponentAdapter()
//    {
//      public void componentShown(ComponentEvent e)
//      {
//        jspPanel_componentShown(e);
//      }
//    });
//    bean_currentDbTableTextField.setEditable(false);
//    bean_CurrentTableLabel.setText("Current database table:");
//    javaBeanPanel.addComponentListener(new java.awt.event.ComponentAdapter()
//    {
//      public void componentShown(ComponentEvent e)
//      {
//        javaBeanPanel_componentShown(e);
//      }
//    });
//    jMenuFile.add(jMenuFileExit);
//    jMenuHelp.add(jMenuHelpAbout);
//    jMenuBar1.add(jMenuFile);
//    jMenuBar1.add(editMenu);
//    jMenuBar1.add(jMenuHelp);
//    this.setJMenuBar(jMenuBar1);
//    contentPane.add(statusBar, BorderLayout.SOUTH);
//    //buttonPanelOnBottom.add(generateBeanButton, null);
//    jpanelOnLeft.setLayout(jpanelOnLeftBorderLayout);
//    step1JLabel.setText( "(1) Choose a Table" );
//    contentPane.add(jTabbedPane, BorderLayout.CENTER);
//    jTabbedPane.add(databasePanel, "Database");
//    databasePanel.add(mainPanel, BorderLayout.CENTER);
//    mainPanel.add(sqlComboBoxPanel, BorderLayout.NORTH);
//    sqlComboBoxPanel.add(jLabel1, null);
//    sqlComboBoxPanel.add(sqlComboBox, null);
//    //buttonPanelOnBottom.add(generateMethodButton, null);
//    mainPanel.add(cardLayoutPanel, BorderLayout.CENTER);
//    cardLayoutPanel.add(selectInsertUpdatePanel, "jPanel4");
//    selectInsertUpdatePanel.add(fieldOptionsPanel, null);
//    fieldOptionsPanel.add(copyAllAvailableFieldsToSelected, new XYConstraints(30, 149, -1, -1));
//    fieldOptionsPanel.add(availableFieldsScrollPane, new XYConstraints(19, 34, 155, 110));
//    fieldOptionsPanel.add(fieldOptionsLabel, new XYConstraints(25, 14, -1, -1));
//    selectInsertUpdatePanel.add(fieldsSelectedPanel, null);
//    fieldsSelectedPanel.add(fieldsSelectedLabel, new XYConstraints(58, 15, -1, -1));
//    fieldsSelectedPanel.add(selectedFieldsScrollPane, new XYConstraints(10, 34, 173, 110));
//    cardLayoutPanel.add(deletePanel, "jPanel4");
//    databasePanel.add(jpanelOnLeft, BorderLayout.WEST);
//    jpanelOnLeft.add(treePane, BorderLayout.CENTER);
//    jpanelOnLeft.add(step1LabelJPanel, BorderLayout.NORTH);
//    step1LabelJPanel.add(step1JLabel, null);
//    jTabbedPane.add(javaBeanPanel, "Bean");
//    javaBeanPanel.add(jLabel7, new XYConstraints(24, 23, 300, -1));
//    javaBeanPanel.add(jLabel6, new XYConstraints(24, 43, 277, -1));
//    javaBeanPanel.add(jLabel5, new XYConstraints(24, 63, 229, -1));
//    javaBeanPanel.add(generateBeanButton1, new XYConstraints(161, 178, -1, -1));
//    javaBeanPanel.add(bean_currentDbTableTextField, new XYConstraints(164, 99, 166, -1));
//    javaBeanPanel.add(bean_CurrentTableLabel, new XYConstraints(23, 101, 140, -1));
//    jTabbedPane.add(dataObjectPanel, "Data Object");
//    jTabbedPane.add(jspPanel, "Templates");
//    jspPanel.add(jLabel9, new XYConstraints(18, 23, 345, -1));
//    jspPanel.add(jLabel10, new XYConstraints(18, 43, 311, -1));
//    jspPanel.add(jLabel11, new XYConstraints(16, 63, 318, -1));
//    jspPanel.add(jspTemplateDirectoryTextField, new XYConstraints(258, 144, 168, -1));
//    jspPanel.add(jspTemplateLabel, new XYConstraints(18, 146, 239, -1));
//    jspPanel.add(jspTemplateDirectoryButton, new XYConstraints(433, 144, -1, 23));
//    jspPanel.add(jLabel8, new XYConstraints(18, 173, 235, -1));
//    jspPanel.add(jspOutputDirectoryTextField, new XYConstraints(258, 174, 168, -1));
//    jspPanel.add(jspOutputDirectoryButton1, new XYConstraints(433, 173, -1, 23));
//    jspPanel.add(generateJSPButton, new XYConstraints(258, 210, -1, -1));
//    jspPanel.add(jsp_currentDbTableTextField, new XYConstraints(157, 98, 166, -1));
//    jspPanel.add(jspCurrentTableLabel, new XYConstraints(16, 100, 140, -1));
//    jTabbedPane.add(strutsPanel, "Struts");
//    strutsPanel.add(jLabel12, new XYConstraints(24, 23, 382, -1));
//    dataObjectPanel.add(jLabel2, new XYConstraints(24, 23, 300, -1));
//    dataObjectPanel.add(jLabel3, new XYConstraints(24, 43, 381, -1));
//    dataObjectPanel.add(jLabel4, new XYConstraints(24, 63, 341, -1));
//    dataObjectPanel.add(doCurrentTableLabel, new XYConstraints(23, 101, 140, -1));
//    dataObjectPanel.add(currentDbTableTextField, new XYConstraints(164, 99, 166, -1));
//    dataObjectPanel.add(generateMethodButton, new XYConstraints(164, 157, -1, -1));
//    treePane.getViewport().add(treeOfDatabaseTableNames, null);
//    selectedFieldsScrollPane.getViewport().add(fieldsSelectedList, null);
//    availableFieldsScrollPane.getViewport().add(availableFieldsList, null);
//    editMenu.add(driverMenuItem);
//    editMenu.add(urlMenuItem);
//    editMenu.add(usernameMenuItem);
//    editMenu.add(passwordMenuItem);
//    populateSqlComboBox();
//    jTabbedPane.setSelectedComponent(databasePanel);
//  }
//  private void loadImages()
//  {
//    image1 = new ImageIcon(com.devdaily.dbgrinder.MainFrame.class.getResource("openFile.gif"));
//    image2 = new ImageIcon(com.devdaily.dbgrinder.MainFrame.class.getResource("closeFile.gif"));
//    image3 = new ImageIcon(com.devdaily.dbgrinder.MainFrame.class.getResource("help.gif"));
//  }
//
//  private void populateTreeOfTableNames()
//  {
//    Collection listOfTableNames = Project.getListOfTableNames();
//    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Database Tables");
//    Iterator it = listOfTableNames.iterator();
//    int i = 0;
//    DefaultMutableTreeNode[] child = new DefaultMutableTreeNode[listOfTableNames.size()];
//    while ( it.hasNext() )
//    {
//      String currentTableName = (String)it.next();
//      child[i] = new DefaultMutableTreeNode(currentTableName);
//      root.add(child[i]);
//      i++;
//    }
//    DefaultTreeModel treeModel = new DefaultTreeModel((TreeNode)root);
//    treeOfDatabaseTableNames.setModel(treeModel);
//    //treeOfDatabaseTableNames = new JTree(root);
//    treeOfDatabaseTableNames.addMouseListener(new java.awt.event.MouseAdapter()
//    {
//      public void mouseClicked(MouseEvent e)
//      {
//        treeOfDatabaseTableNames_mouseClicked(e);
//      }
//    });
//    treeOfDatabaseTableNames.updateUI();
//  }
//
//  private void populateSqlComboBox()
//  {
//    DefaultComboBoxModel model = new DefaultComboBoxModel();
//    model.addElement( "DELETE" );
//    model.addElement( "INSERT" );
//    model.addElement( "SELECT" );
//    model.addElement( "UPDATE" );
//    sqlComboBox.setModel(model);
//    // set the deault method to generate to insert
//    sqlComboBox.setSelectedItem( "INSERT" );
//    sqlComboBox.addItemListener(new java.awt.event.ItemListener()
//    {
//      public void itemStateChanged(ItemEvent e)
//      {
//        sqlComboBox_itemStateChanged(e);
//      }
//    });
//    sqlComboBox.setEditable(false);
//  }
//
//
//  /**File | Exit action performed*/
//  public void jMenuFileExit_actionPerformed(ActionEvent e)
//  {
//    System.exit(0);
//  }
//
//  /**Help | About action performed*/
//  public void jMenuHelpAbout_actionPerformed(ActionEvent e)
//  {
//    MainFrame_AboutBox dlg = new MainFrame_AboutBox(this);
//    //Dimension dlgSize = dlg.getPreferredSize();
//    //Dimension frmSize = getSize();
//    //Point loc = getLocation();
//    //dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
//    dlg.setModal(true);
//    dlg.pack();
//    dlg.show();
//  }
//
//  /**Overridden so we can exit when window is closed*/
//  protected void processWindowEvent(WindowEvent e)
//  {
//    super.processWindowEvent(e);
//    if (e.getID() == WindowEvent.WINDOW_CLOSING)
//    {
//      jMenuFileExit_actionPerformed(null);
//    }
//  }
//
//  void treeOfDatabaseTableNames_mouseClicked(MouseEvent e)
//  {
//    int x=e.getX();
//    int y=e.getY();
//    TreePath treePath = treeOfDatabaseTableNames.getClosestPathForLocation(x,y);
//    DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
//    Project.setCurrentDatabaseTable(node.toString());
//    updateListOfPossibleFields(Project.getCurrentDatabaseTable());
//    emptyListOfSelectedFields();
//  }
//
//  private void emptyListOfSelectedFields()
//  {
//    Vector emptyVector = new Vector();
//    Project.setFieldsSelectedVector(emptyVector);
//    fieldsSelectedList.setListData(emptyVector);
//    fieldsSelectedList.updateUI();
//  }
//
//  /**
//   * Should really go get a list of fields for the given table, and put that list
//   * into the list of available field names.
//   */
//  private void updateListOfPossibleFields(String databaseTableName)
//  {
//    String catalog = "";
//    String schema  = "";
//    try
//    {
//      //Vector v = Table.getColumnData(Project.getCurrentDatabaseTable(),Project.getDatabaseMetaData(),catalog,schema,true);
//      Vector v = Table.getColumnData(Project.getCurrentDatabaseTable(),Project.getDatabaseMetaData(),null,null,true);
//      Iterator it = v.iterator();
//      Vector fieldNames = new Vector();
//      while ( it.hasNext() )
//      {
//        ColumnData cd = (ColumnData)it.next();
//        fieldNames.add(cd.getName());
//      }
//      availableFieldsList.setListData(fieldNames);
//    }
//    catch (SQLException se)
//    {
//      statusBar.setText(se.getMessage());
//    }
//  }
//
//  void availableFieldsList_mouseClicked(MouseEvent e)
//  {
//    String selectedField = (String)availableFieldsList.getSelectedValue();
//    Project.addItemToFieldsSelectedVector(selectedField);
//    fieldsSelectedList.setListData(Project.getFieldsSelectedVector());
//    fieldsSelectedList.updateUI();
//  }
//
//  void fieldsSelectedList_mouseClicked(MouseEvent e)
//  {
//    String selectedField = (String)fieldsSelectedList.getSelectedValue();
//    Project.removeItemFromFieldsSelectedVector(selectedField);
//    fieldsSelectedList.updateUI();
//  }
//
//  void sqlComboBox_itemStateChanged(ItemEvent e)
//  {
//    String selectedItem = (String)sqlComboBox.getSelectedItem();
//    Project.setMethodCreationMode(selectedItem);
//  }
//
//  private Properties getCurrentProperties()
//  {
//    Properties props = new Properties();
//    props.setProperty("driver",           Project.getDriver());
//    props.setProperty("url",              Project.getUrl());
//    props.setProperty("username",         Project.getUsername());
//    props.setProperty("password",         Project.getPassword());
//    props.setProperty("package_name",     "YOUR_PACKAGE_HERE");
//    //props.setProperty("home_dir",         "c:\\temp");
//    //props.setProperty("class_output_dir", "c:\\temp");
//    props.setProperty("database_type",    "NORMAL");
//    props.setProperty("table_list",       Project.getCurrentDatabaseTable());
//    props.setProperty("class_list",       Project.getCurrentDatabaseTable());
//    props.setProperty("desiredColumns",   getUsersDesiredColumns() );
//    return props;
//  }
//
//  /**
//   * Create a comma-separated list of db table columns that the user wants to
//   * use during the code-generation process.
//   */
//  private String getUsersDesiredColumns()
//  {
//    int numItems = fieldsSelectedList.getModel().getSize();
//    String desiredColumns = new String();
//    for ( int i=0; i<numItems; i++ )
//    {
//      desiredColumns += fieldsSelectedList.getModel().getElementAt(i);
//      if ( i<numItems-1 )
//      {
//        desiredColumns += ",";
//      }
//    }
//    return desiredColumns;
//  }
//
//  void generateMethodButton_mouseClicked(MouseEvent me)
//  {
//    Properties props = getCurrentProperties();
//    try
//    {
//      Db2Wapp db2Wapp = new Db2Wapp(props, Project.getMethodCreationMode());
//      String modelClass = db2Wapp.getModelClass();
//      String dataObjectClass = db2Wapp.getDataObjectClass();
//      if ( dataObjectClass == null )
//      {
//        System.err.println( "\t\tERROR: dataObjectClass == null" );
//      }
//      if ( dataObjectClass.trim().equals("") )
//      {
//        System.err.println( "\tERROR: dataObjectClass == ''\n\n" );
//      }
//      if ( dataObjectClass == null || dataObjectClass.trim().equals("") )
//      {
//        dataObjectClass = "Please select a database table that we should derive from.";
//      }
//      TextDisplayDialog tdd = new TextDisplayDialog();
//      tdd.setText(dataObjectClass);
//      tdd.setTitle("DataObject method generated from the current database table.");
//      tdd.pack();
//      tdd.show();
//    }
//    catch (IOException ioe)
//    {
//      System.err.println( "ioe: " + ioe.getMessage() );
//      statusBar.setText( ioe.getMessage() );
//    }
//    catch (SQLException se)
//    {
//      System.err.println( "se: " + se.getMessage() );
//      statusBar.setText( se.getMessage() );
//    }
//    catch (ClassNotFoundException cnfe)
//    {
//      System.err.println( "cnfe: " + cnfe.getMessage() );
//      statusBar.setText( cnfe.getMessage() );
//    }
//    catch (Exception e)
//    {
//      System.err.println( "e: " + e.getMessage() );
//      statusBar.setText( e.getMessage() );
//    }
//  }
//
//  void driverMenuItem_mouseClicked(MouseEvent e)
//  {
//    EditTextFieldDialog dialog = new EditTextFieldDialog();
//    dialog.setLabel("Driver: ");
//    dialog.setTitle("Specify the database Driver");
//    dialog.setDefaultTextForTextField(Project.getDriver());
//    dialog.pack();
//    dialog.setModal(true);
//    dialog.setLocationRelativeTo(this);
//    dialog.show();
//    if ( dialog.userClickedOk() )
//    {
//      Project.setDriver(dialog.getTextFromTextField());
//      Project.connectToDatabase();
//      populateTreeOfTableNames();
//    }
//    editMenu.setPopupMenuVisible(false);
//  }
//
//  void urlMenuItem_mouseClicked(MouseEvent e)
//  {
//    EditTextFieldDialog dialog = new EditTextFieldDialog();
//    dialog.setLabel("URL: ");
//    dialog.setTitle("Specify the database URL");
//    dialog.setDefaultTextForTextField(Project.getUrl());
//    dialog.pack();
//    dialog.setModal(true);
//    dialog.setLocationRelativeTo(this);
//    dialog.show();
//    if ( dialog.userClickedOk() )
//    {
//      Project.setUrl(dialog.getTextFromTextField());
//      Project.connectToDatabase();
//      populateTreeOfTableNames();
//    }
//    editMenu.setPopupMenuVisible(false);
//  }
//
//  private void usernameMenuItem_mouseClicked(MouseEvent e)
//  {
//    EditTextFieldDialog dialog = new EditTextFieldDialog();
//    dialog.setLabel("Username: ");
//    dialog.setTitle("Specify the database Username");
//    dialog.setDefaultTextForTextField(Project.getUsername());
//    dialog.setModal(true);
//    dialog.setLocationRelativeTo(this);
//    dialog.show();
//    if ( dialog.userClickedOk() )
//    {
//      Project.setUsername(dialog.getTextFromTextField());
//      Project.connectToDatabase();
//      populateTreeOfTableNames();
//    }
//    editMenu.setPopupMenuVisible(false);
//  }
//
//  void passwordMenuItem_mouseClicked(MouseEvent e)
//  {
//    EditTextFieldDialog dialog = new EditTextFieldDialog();
//    dialog.setLabel("Password: ");
//    dialog.setTitle("Specify the database Password");
//    dialog.setDefaultTextForTextField(Project.getPassword());
//    dialog.setModal(true);
//    dialog.setLocationRelativeTo(this);
//    dialog.show();
//    if ( dialog.userClickedOk() )
//    {
//      Project.setPassword(dialog.getTextFromTextField());
//      Project.connectToDatabase();
//      populateTreeOfTableNames();
//    }
//    editMenu.setPopupMenuVisible(false);
//  }
//
//  private void driverMenuItem_mousePressed(MouseEvent e)
//  {
//    driverMenuItem_mouseClicked(e);
//  }
//
//  private void urlMenuItem_mousePressed(MouseEvent e)
//  {
//    urlMenuItem_mouseClicked(e);
//  }
//
//  private void usernameMenuItem_mousePressed(MouseEvent e)
//  {
//    usernameMenuItem_mouseClicked(e);
//  }
//
//  private void passwordMenuItem_mousePressed(MouseEvent e)
//  {
//    passwordMenuItem_mouseClicked(e);
//  }
//
//  void generateBeanButton1_mouseClicked(MouseEvent e)
//  {
//    Properties props = getCurrentProperties();
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
//      tdd.show();
//    }
//    catch (IOException ioe)
//    {
//      System.err.println( "ioe: " + ioe.getMessage() );
//    }
//    catch (SQLException se)
//    {
//      System.err.println( "se: " + se.getMessage() );
//    }
//    catch (ClassNotFoundException cnfe)
//    {
//      System.err.println( "cnfe: " + cnfe.getMessage() );
//    }
//  }
//
//  void copyAllAvailableFieldsToSelected_mouseClicked(MouseEvent e)
//  {
//    int numAvailableItems = availableFieldsList.getModel().getSize();
//    String currentField = null;
//    for ( int i=0; i<numAvailableItems; i++ )
//    {
//      currentField = (String)availableFieldsList.getModel().getElementAt(i);
//      Project.addItemToFieldsSelectedVector(currentField);
//    }
//    fieldsSelectedList.setListData(Project.getFieldsSelectedVector());
//    fieldsSelectedList.updateUI();
//  }
//
//  /*
//   * 
//   * I THINK THIS IS THE CODE THAT PARSES/GENERATES THE TEMPLATES.
//   * 
//   */
//  void generateJSPButton_actionPerformed(ActionEvent e)
//  {
//    Properties props = getCurrentProperties();
//
//    // do this stuff here to populate _domainObjects
//    _domainObjects = new Vector();
//    try
//    {
//      Db2Wapp db2Wapp = new Db2Wapp(props, Project.getMethodCreationMode(),_domainObjects);
//      String modelClass = db2Wapp.getModelClass();
//    }
//    catch (Exception e2)
//    {
//      System.err.println( "caught an exception, message is: " + e2.getMessage() );
//    }
//
//    if ( _domainObjects == null )
//    {
//      System.err.println( "warning, we're about to crash, _domainObjects == null" );
//    }
//    else
//    {
//      System.err.println( "_domainObjects.size = " + _domainObjects.size() );
//    }
//    // ok, now generate the JSPs and Servlets
//    //String resourcesDir = _homeDir + "/" + "resources";
//    _jspTemplateDir = jspTemplateDirectoryTextField.getText();
//    if ( (_jspTemplateDir == null) || (_jspTemplateDir.trim().equals("")) )
//    {
//      statusBar.setText("ERROR: Cannot create JSPs because template directory is not set.");
//      return;
//    }
//    _jspOutputDir = jspOutputDirectoryTextField.getText();
//    if ( (_jspOutputDir == null) || (_jspOutputDir.trim().equals("")) )
//    {
//      statusBar.setText("ERROR: Cannot create JSPs because output directory is not set.");
//      return;
//    }
//    createClassOutputFolders();
//    WappGenerator wapp = new WappGenerator(_domainObjects,
//                                           _prefix,
//                                           _jspTemplateDir,
//                                           _jspOutputDir);
//    wapp.printUIComponents();
//    // printWebXmlFile() needs a lot of work:
//    //wapp.printWebXmlFile();
//
//    // printControllers() needs a lot of work:
//    //wapp.printControllers(_controllerFolder);
//  }
//
//  /**
//   * Create the model, container, and controller folders, using the "_classOutputDir"
//   * and "_packageName" as the "root" for these other folders.
//   */
//  private void createClassOutputFolders ()
//  {
//    // create the model, container, and controller folders
//    File rootPackageFile = new File(_classOutputDir + "/" + _packageName.replace('.','/') );
//    rootPackageFile.mkdirs();
//    _modelFolder = new File(rootPackageFile, "model");
//    _containerFolder = new File(rootPackageFile, "container");
//    _controllerFolder = new File(rootPackageFile, "controller");
//    _modelFolder.mkdir();
//    _containerFolder.mkdir();
//    _controllerFolder.mkdir();
//  }
//
//  void jspTemplateDirectoryButton_actionPerformed(ActionEvent e)
//  {
//    int status = getDirectoryFromUser(jFileChooser,mostRecentTemplateDirectory);
//    if ( status == JFileChooser.APPROVE_OPTION )
//    {
//      mostRecentTemplateDirectory = jFileChooser.getSelectedFile();
//      jspTemplateDirectoryTextField.setText(mostRecentTemplateDirectory.getAbsolutePath());
//    }
//  }
//
//  void jspOutputDirectoryButton1_actionPerformed(ActionEvent e)
//  {
//    int status = getDirectoryFromUser(jFileChooser,mostRecentOutputDirectory);
//    if ( status == JFileChooser.APPROVE_OPTION )
//    {
//      mostRecentOutputDirectory = jFileChooser.getSelectedFile();
//      jspOutputDirectoryTextField.setText(mostRecentOutputDirectory.getAbsolutePath());
//    }
//  }
//
//  private int getDirectoryFromUser(JFileChooser fileChooser, File mostRecentDirectory)
//  {
//    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//    if ( mostRecentDirectory != null )
//    {
//      fileChooser.setCurrentDirectory(mostRecentDirectory);
//    }
//    int status = fileChooser.showDialog(this,"Select Directory");
//    if ( status == JFileChooser.APPROVE_OPTION )
//    {
//      mostRecentDirectory = fileChooser.getSelectedFile();
//    }
//    return status;
//  }
//
//  private void dataObjectPanel_componentShown(ComponentEvent e)
//  {
//    updateTextFieldWithCurrentDatabaseName(currentDbTableTextField);
//  }
//
//  private void jspPanel_componentShown(ComponentEvent e)
//  {
//    updateTextFieldWithCurrentDatabaseName(jsp_currentDbTableTextField);
//  }
//
//  void javaBeanPanel_componentShown(ComponentEvent e)
//  {
//    updateTextFieldWithCurrentDatabaseName(bean_currentDbTableTextField);
//  }
//
//  private void updateTextFieldWithCurrentDatabaseName(JTextField textField)
//  {
//    if ( Project.getCurrentDatabaseTable() == null )
//    {
//      textField.setText("<none>");
//    }
//    else
//    {
//      textField.setText(Project.getCurrentDatabaseTable());
//    }
//  }
//
//}
//
