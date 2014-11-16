package com.devdaily.dbgrinder.view;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import com.devdaily.dbgrinder.controller.GenerateCRUDController;
import com.devdaily.dbgrinder.controller.GenerateTemplateController;
import com.devdaily.dbgrinder.controller.NewMainFrameController;
import com.devdaily.dbgrinder.model.Db2Wapp;
import com.devdaily.dbgrinder.model.Project;

public class NewMainFrame extends JFrame
{
  private DatabasePropertiesForm databasePropertiesForm;
  private ListAndButtonsForm listAndButtonsForm;
  private BorderLayout borderLayout = new BorderLayout();

  // the list of database tables
  private JList databaseTablesJlist;
  DefaultListModel databaseTablesListModel = new DefaultListModel();
  private String currentDatabaseTableName;
  DatabaseTableListSelectionHandler listSelectionListener;
  
  // buttons
  private JButton generateBeanButton;
  private JButton generateCRUDButton;
  private JButton generateTemplateButton;
  
  // controllers
  NewMainFrameController newMainFrameController; 
  GenerateCRUDController generateCRUDController;
  GenerateTemplateController generateTemplateController;  
  
  Properties currentProjectProperties;
  
  public NewMainFrame(NewMainFrameController newMainFrameController,
      GenerateCRUDController generateCRUDController,
      GenerateTemplateController generateTemplateController,
      DatabasePropertiesForm lhsPanel,
      ListAndButtonsForm rhsPanel)
  {
    this.newMainFrameController = newMainFrameController;
    this.generateCRUDController = generateCRUDController;
    this.generateTemplateController = generateTemplateController;
    this.newMainFrameController.setNewMainFrame(this);
    this.databasePropertiesForm = lhsPanel;
    this.listAndButtonsForm = rhsPanel;
    this.setTitle("KickStart");
    
    currentProjectProperties = Project.getCurrentProperties();

    addPanelsToContentPane(lhsPanel, rhsPanel);
    setupDatabaseTablesJList();
    setupButtons(rhsPanel);

    // not sure why this is working, but it is
    populateListOfTableNames();

    this.pack();
  }

  private void setupButtons(ListAndButtonsForm rhsPanel)
  {
    generateBeanButton = rhsPanel.getGenerateBeanButton();
    generateCRUDButton = rhsPanel.getGenerateCrudButton();
    generateTemplateButton = rhsPanel.getParseTemplateButton();

    newMainFrameController.addListenerToGenerateBeanButton(generateBeanButton);
    newMainFrameController.addListenerToGenerateCRUDButton(generateCRUDButton);
    newMainFrameController.addListenerToGenerateTemplateButton(generateTemplateButton);
  }

  public void setCurrentDatabaseTableName(String name)
  {
    currentDatabaseTableName = name;
    currentProjectProperties.setProperty("table_list", currentDatabaseTableName);
    currentProjectProperties.setProperty("class_list", currentDatabaseTableName);
  }
  
  public String getCurrentDatabaseTableName()
  {
    return this.currentDatabaseTableName;
  }

  private void addPanelsToContentPane(DatabasePropertiesForm lhsPanel,
      ListAndButtonsForm rhsPanel)
  {
    this.getContentPane().setLayout(borderLayout);
    this.getContentPane().add(lhsPanel, BorderLayout.WEST);
    this.getContentPane().add(rhsPanel, BorderLayout.CENTER);
  }

  private void setupDatabaseTablesJList()
  {
    databaseTablesJlist = listAndButtonsForm.getDatabaseTableList();
    databaseTablesJlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listSelectionListener = new DatabaseTableListSelectionHandler(this);
    databaseTablesJlist.addListSelectionListener(listSelectionListener);
  }

  private void populateListOfTableNames()
  {
    Collection listOfTableNames = Project.getListOfTableNames();
    Iterator it = listOfTableNames.iterator();
    while (it.hasNext())
    {
      String currentTableName = (String) it.next();
      databaseTablesListModel.addElement(currentTableName);
    }

    databaseTablesJlist.setModel(databaseTablesListModel);
  }

  // private -- don't use it
  private NewMainFrame()
  {
  }


  /**
   * Listen to selections on the JList of database table names.
   */
  class DatabaseTableListSelectionHandler implements ListSelectionListener
  {
    private String currentSelection;
    private NewMainFrame newMainFrame;

    public DatabaseTableListSelectionHandler(NewMainFrame newMainFrame)
    {
      this.newMainFrame = newMainFrame;
    }
    
    public void valueChanged(ListSelectionEvent e)
    {
      // todo - should enable buttons here; should have been disabled beforehand
      JList jlist = (JList) e.getSource();
      int index = jlist.getSelectedIndex();
      currentSelection = (String)jlist.getSelectedValue();
      newMainFrame.setCurrentDatabaseTableName(currentSelection);
    }
    
    public String getCurrentSelection()
    {
      return this.currentSelection;
    }
  }

}





