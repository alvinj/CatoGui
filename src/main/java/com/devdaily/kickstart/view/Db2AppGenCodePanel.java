package com.devdaily.kickstart.view;

import javax.swing.*;
import com.devdaily.kickstart.Constants;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class Db2AppGenCodePanel extends JPanel
{
  JList tableList = new JList();
  JScrollPane tableScrollPane = new JScrollPane(tableList);
  JButton generateBeanButton = new JButton("Generate Bean");
  JButton generateCRUDButton = new JButton("Generate CRUD");
  JButton generateCodeFromTemplatesButton = new JButton("Generate from Template");

  public Db2AppGenCodePanel()
  {
    layoutPanel();
    enablePanel(false);
    tableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tableList.setFixedCellWidth(Constants.DATABASE_TABLE_LIST_WIDTH);
  }

  public void enablePanel(boolean boo)
  {
    generateBeanButton.setEnabled(boo);
    generateCRUDButton.setEnabled(boo);
    generateCodeFromTemplatesButton.setEnabled(boo);
    tableList.setEnabled(boo);
  }

  private void layoutPanel()
  {
    FormLayout layout = new FormLayout( 
        "left:pref, 6dlu, left:pref, 2dlu",
        "p,4dlu,p,2dlu,p,2dlu,p,2dlu, p:grow");
    PanelBuilder builder = new PanelBuilder(layout, this);
    builder.setDefaultDialogBorder();
    CellConstraints cc = new CellConstraints();
    
    builder.addSeparator("Database Tables",       cc.xyw (1,1,3));
    builder.add(tableScrollPane,                  cc.xywh(1, 3, 1, 7));    
    builder.add(generateBeanButton,               cc.xy  (3,3));
    builder.add(generateCRUDButton,               cc.xy  (3,5));
    builder.add(generateCodeFromTemplatesButton,  cc.xy  (3,7));
  }
  
  //tester
  public static void main(String[] args)
  {
    JFrame f = new JFrame();
    f.setContentPane(new Db2AppGenCodePanel());
    f.pack();
    f.setVisible(true);
  }

  public JButton getGenerateBeanButton()
  {
    return generateBeanButton;
  }

  public JButton getGenerateCRUDButton()
  {
    return generateCRUDButton;
  }

  public JButton getGenerateCodeFromTemplatesButton()
  {
    return generateCodeFromTemplatesButton;
  }
  
  public JList getTableList()
  {
    return tableList;
  }
}
