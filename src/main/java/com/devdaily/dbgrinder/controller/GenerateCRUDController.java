package com.devdaily.dbgrinder.controller;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import com.devdaily.dbgrinder.model.ColumnData;
import com.devdaily.dbgrinder.model.Db2Wapp;
import com.devdaily.dbgrinder.model.Project;
import com.devdaily.dbgrinder.model.Table;
import com.devdaily.dbgrinder.view.CRUDConfigurationDialog;
import com.devdaily.dbgrinder.view.TextDisplayDialog;
import javax.swing.*;
import javax.swing.event.ListDataListener;

public class GenerateCRUDController
{
  // our lists
  private JList availableFieldsList;
  private JList selectedFieldsList;
  private DefaultListModel availableFieldsModel = new DefaultListModel();
  private DefaultListModel selectedFieldsModel = new DefaultListModel();

  // our buttons
  private JButton moveSelectedItemsToSelectedListButton;
  private JButton moveAllAvailableFieldsToSelectedButton;
  private JButton moveAllLeftButton;
  private JButton moveLeftButton;
  private JButton generateCRUDButton;
  private JButton cancelButton;
  
  // our radio buttons
  private JRadioButton selectRadioButton;
  private JRadioButton insertRadioButton;
  private JRadioButton updateRadioButton;
  private JRadioButton deleteRadioButton;

  // controllers
  NewMainFrameController newMainFrameController;
  CRUDConfigurationDialog crudConfigurationDialog;


  // don't use this one any more
  private GenerateCRUDController()
  {
  }

  public GenerateCRUDController(NewMainFrameController newMainFrameController)
  {
    this.newMainFrameController = newMainFrameController;
    crudConfigurationDialog = new CRUDConfigurationDialog(newMainFrameController.getNewMainFrame());
    setTitle();
    initializeDialogButtons();
    initializeDialogLists();
    initializeRadioButtons();
    
    addActionListeners();
    populateListOfFieldsAvailable(newMainFrameController.getCurrentlySelectedTableName());
  }

  private void setTitle()
  {
    crudConfigurationDialog.setTitle("Generate a CRUD Method (" + getCurrentDatabaseTableName() + ")");
  }
  
  private String getCurrentDatabaseTableName()
  {
    Properties p = Project.getCurrentProperties();
    return p.getProperty("table_list");
  }

  private void initializeRadioButtons()
  {
    selectRadioButton = crudConfigurationDialog.getSelectRadioButton();
    insertRadioButton = crudConfigurationDialog.getInsertRadioButton();
    updateRadioButton = crudConfigurationDialog.getUpdateRadioButton();
    deleteRadioButton = crudConfigurationDialog.getDeleteRadioButton();
  }
  
  private void clearAvailableFieldsList()
  {
    availableFieldsModel.clear();
//    ListDataListener ldl[] = availableFieldsModel.getListDataListeners();
//    for (int i=0; i<ldl.length; i++)
//    {
//      ldl[i].???
//    }
  }

  private void clearSelectedFieldsList()
  {
    selectedFieldsModel.clear();
  }

  private void populateListOfFieldsAvailable(String currentlySelectedDatabaseTableName)
  {
    String catalog = "";
    String schema  = "";
    try
    {
      //Vector v = Table.getColumnData(Project.getCurrentDatabaseTable(),Project.getDatabaseMetaData(),null,null,true);
      Vector v = Table.getColumnData(currentlySelectedDatabaseTableName,Project.getDatabaseMetaData(),null,null,true);
      Iterator it = v.iterator();
      //Vector fieldNames = new Vector();
      availableFieldsModel.clear();
      while ( it.hasNext() )
      {
        ColumnData cd = (ColumnData)it.next();
        //fieldNames.add(cd.getName());
        availableFieldsModel.addElement(cd.getName());
      }
      //availableFieldsModel.notifyAll();
    }
    catch (SQLException se)
    {
      //statusBar.setText(se.getMessage());
    }
  }

  private void initializeDialogLists()
  {
    availableFieldsList = crudConfigurationDialog.getAvailableTableFieldsList();
    availableFieldsList.setModel(availableFieldsModel);

    selectedFieldsList = crudConfigurationDialog.getSelectedTableFieldsList();
    selectedFieldsList.setModel(selectedFieldsModel);
  }

  private void initializeDialogButtons()
  {
    moveSelectedItemsToSelectedListButton = crudConfigurationDialog.getMoveRightButton();
    moveAllAvailableFieldsToSelectedButton = crudConfigurationDialog.getMoveAllRightButton();
    moveLeftButton = crudConfigurationDialog.getMoveLeftButton();
    moveAllLeftButton = crudConfigurationDialog.getMoveAllLeftButton();
    generateCRUDButton = crudConfigurationDialog.getOkButton();
    cancelButton = crudConfigurationDialog.getCancelButton();
  }

  /*
   * I think this is the method that generates a data object method
   * based on the selected database table and fields.
   */
  void doGenerateDesiredMethod(MouseEvent me)
  {
    // get the "creation mode" from the radio buttons
    setCreationModeFromRadioButtons();

    // get the list of fields that are selected
    ListModel lm = selectedFieldsList.getModel();
    if (lm.getSize()==0)
    {
      JOptionPane.showMessageDialog(crudConfigurationDialog, "Please select at least one field");
      return;
    }
    String desiredColumns = getSelectedFieldsAsString(lm);

    Properties props = Project.getCurrentProperties();
    props.setProperty("desiredColumns", desiredColumns );

    try
    {
      Db2Wapp db2Wapp = new Db2Wapp(props, Project.getMethodCreationMode());
      String modelClass = db2Wapp.getModelClass();
      String dataObjectClass = db2Wapp.getDataObjectClass();
      if ( dataObjectClass == null )
      {
        System.err.println( "\t\tERROR: dataObjectClass == null" );
      }
      if ( dataObjectClass.trim().equals("") )
      {
        System.err.println( "\tERROR: dataObjectClass == ''\n\n" );
      }
      if ( dataObjectClass == null || dataObjectClass.trim().equals("") )
      {
        dataObjectClass = "Please select a database table that we should derive from.";
      }
      TextDisplayDialog tdd = new TextDisplayDialog();
      tdd.setText(dataObjectClass);
      tdd.setTitle("DataObject method generated from the current database table.");
      tdd.pack();
      tdd.setLocationRelativeTo(newMainFrameController.getNewMainFrame());
      tdd.setVisible(true);
    }
    catch (IOException ioe)
    {
      System.err.println( "ioe: " + ioe.getMessage() );
      //statusBar.setText( ioe.getMessage() );
      JOptionPane.showMessageDialog(crudConfigurationDialog, ioe.getMessage());
    }
    catch (SQLException se)
    {
      System.err.println( "se: " + se.getMessage() );
      //statusBar.setText( se.getMessage() );
      JOptionPane.showMessageDialog(crudConfigurationDialog, se.getMessage());
    }
    catch (ClassNotFoundException cnfe)
    {
      System.err.println( "cnfe: " + cnfe.getMessage() );
      //statusBar.setText( cnfe.getMessage() );
      JOptionPane.showMessageDialog(crudConfigurationDialog, cnfe.getMessage());
    }
    catch (Exception e)
    {
      System.err.println( "e: " + e.getMessage() );
      //statusBar.setText( e.getMessage() );
      JOptionPane.showMessageDialog(crudConfigurationDialog, e.getMessage());
    }
  }

  /*
   * Convert the items in the list to a comma-separated string.
   */
  private String getSelectedFieldsAsString(ListModel lm)
  {
    String desiredColumns;
    StringBuffer sb = new StringBuffer();
    for (int i=0; i<lm.getSize(); i++)
    {
      String s = (String)lm.getElementAt(i);
      sb.append(s);
      if (i<lm.getSize()-1)
      {
        sb.append(",");
      }
    }
    desiredColumns = sb.toString();
    return desiredColumns;
  }

  private void setCreationModeFromRadioButtons()
  {
    Project.setMethodCreationMode(Project.CREATION_MODE_DEFAULT);
    if (selectRadioButton.isSelected()) Project.setMethodCreationMode(Project.CREATION_MODE_SELECT);
    if (insertRadioButton.isSelected()) Project.setMethodCreationMode(Project.CREATION_MODE_INSERT);
    if (updateRadioButton.isSelected()) Project.setMethodCreationMode(Project.CREATION_MODE_UPDATE);
    if (deleteRadioButton.isSelected()) Project.setMethodCreationMode(Project.CREATION_MODE_DELETE);
  }  
  
  
  private void addActionListeners()
  {
    //
    generateCRUDButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        doGenerateDesiredMethod(e);
      }
    });
    //
    cancelButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        doCancelButtonAction(e);
      }
    });
    //
    moveLeftButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        doMoveLeftButtonAction(e);
      }
    });
    //
    moveAllLeftButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        doMoveAllLeftButtonAction(e);
      }
    });
    //
    moveSelectedItemsToSelectedListButton.addMouseListener(new java.awt.event.MouseAdapter()
    { 
      public void mouseClicked(MouseEvent e)
      {
        doMoveSelectedFieldsToSelectedList(e);
      }
    });
    //
    moveAllAvailableFieldsToSelectedButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        doMoveAllAvailableFieldsToSelectedList(e);
      }
    });
    //
    availableFieldsList.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        availableFieldsList_mouseClicked(e);
      }
    });
    //
    selectedFieldsList.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        fieldsSelectedList_mouseClicked(e);
      }
    });
  }

  void doGenerateCRUDButtonAction(MouseEvent e)
  {
    // get the currently selected table name
    String currentlySelectedTableName = newMainFrameController.getCurrentlySelectedTableName();
    if (currentlySelectedTableName==null || currentlySelectedTableName.trim().equals(""))
    {
      JOptionPane.showMessageDialog(newMainFrameController.getNewMainFrame(), "Please select a table name to begin this process.");
      return;
    }
    // display the crud dialog
    crudConfigurationDialog.setVisible(true);
  }

  void doCancelButtonAction(MouseEvent e)
  {
    crudConfigurationDialog.dispose();
  }

  void doMoveLeftButtonAction(MouseEvent e)
  {
  }

  void doMoveAllLeftButtonAction(MouseEvent e)
  {
  }

  
  void availableFieldsList_mouseClicked(MouseEvent e)
  {
    String selectedField = (String)availableFieldsList.getSelectedValue();
    Project.addItemToFieldsSelectedVector(selectedField);
    selectedFieldsModel.addElement(selectedField);
    availableFieldsModel.removeElement(selectedField);
  }

  void doMoveSelectedFieldsToSelectedList(MouseEvent e)
  {
  }

  void doMoveAllAvailableFieldsToSelectedList(MouseEvent e)
  {
    // copy all available fields to the "selected" list
    int numAvailableItems = availableFieldsModel.getSize();
    String currentField = null;
    for ( int i=0; i<numAvailableItems; i++ )
    {
      currentField = (String)availableFieldsModel.getElementAt(i);
      selectedFieldsModel.addElement(currentField);
    }
    
    // since we're moving them all, clear the "available" list
    availableFieldsModel.clear();

    // update this with the fields in the "selected" list
    Project.getFieldsSelectedVector().clear();
    int numSelectedItems = selectedFieldsModel.getSize();
    for (int i=0; i<numSelectedItems; i++)
    {
      String s = (String)selectedFieldsModel.getElementAt(i);
      Project.addItemToFieldsSelectedVector(s);
    }
  }

  void fieldsSelectedList_mouseClicked(MouseEvent e)
  {
    String selectedField = (String)selectedFieldsList.getSelectedValue();
    Project.removeItemFromFieldsSelectedVector(selectedField);
    selectedFieldsModel.removeElement(selectedField);
    availableFieldsModel.addElement(selectedField);
    //selectedFieldsList.updateUI();
  }

  public void refresh()
  {
    setTitle();
    clearAvailableFieldsList();
    clearSelectedFieldsList();
    populateListOfFieldsAvailable(Project.getCurrentDatabaseTable());
  }



}
