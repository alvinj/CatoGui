package com.devdaily.kickstart.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.devdaily.kickstart.controller.Db2AppModuleController;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class Db2AppMainPanel extends JPanel
{

  Db2AppConfigPanel configPanel = new Db2AppConfigPanel();
  Db2AppGenCodePanel generateCodePanel = new Db2AppGenCodePanel();
  Db2AppModuleController db2AppModuleController;
  
  private Db2AppMainPanel()
  {
  }
  
  public Db2AppMainPanel(Db2AppModuleController db2AppModuleController)
  {
    this.db2AppModuleController = db2AppModuleController;
    FormLayout layout = new FormLayout( 
        "fill:pref:grow, 10dlu, left:pref:grow",
        "fill:pref:grow");
    PanelBuilder builder = new PanelBuilder(layout, this);
    builder.setDefaultDialogBorder();
    CellConstraints cc = new CellConstraints();
    
    builder.add(configPanel,       cc.xy(1,1));    
    builder.add(generateCodePanel, cc.xy  (3,1));
  }

  public JButton getConnectButton()
  {
    return configPanel.getConnectButton();
  }
  
  public JButton getGenerateBeanButton()
  {
    return generateCodePanel.getGenerateBeanButton();
  }
  
  public JButton getgenerateCRUDButton()
  {
    return generateCodePanel.getGenerateCRUDButton();
  }
  
  public JButton getGenerateCodeFromTemplateButton()
  {
    return generateCodePanel.getGenerateCodeFromTemplatesButton();
  }
  
  public JTextField getURLTextField()
  {
    return configPanel.getUrlField();
  }
  
  public JTextField getDriverTextField()
  {
    return configPanel.getDriverField();
  }
  
  public JTextField getUsernameTextField()
  {
    return configPanel.getUsernameField();
  }
  
  public JTextField getPasswordTextField()
  {
    return configPanel.getPasswordField();
  }
  
  //tester
  public static void main(String[] args)
  {
    JFrame f = new JFrame();
    f.setContentPane(new Db2AppMainPanel());
    f.pack();
    f.setVisible(true);
  }

  public Db2AppConfigPanel getDb2AppConfigPanel()
  {
    return configPanel;
  }

  public Db2AppGenCodePanel getDb2AppGenCodePanel()
  {
    return generateCodePanel;
  }

  public JList getTableList()
  {
    return generateCodePanel.getTableList();
  }

}
