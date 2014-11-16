package com.devdaily.kickstart.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.jgoodies.forms.*;
import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.util.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

public class Db2AppConfigPanel
extends JPanel
{
  JTextField driverField = new JTextField(60);
  JTextField urlField = new JTextField(60);
  JTextField usernameField = new JTextField(15);
  JTextField passwordField = new JTextField(15);
  JButton connectButton = new JButton("Connect");

  public Db2AppConfigPanel()
  {
//    FormLayout layout = new FormLayout( 
//        "right:pref, 4dlu, fill:80dlu:grow",
//        "pref, 4dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref");
//    PanelBuilder builder = new PanelBuilder(layout, this);
//    builder.setDefaultDialogBorder();
//    CellConstraints cc = new CellConstraints();
//
//    builder.addSeparator("Database Properties", cc.xyw(1,1,3));
//    builder.addLabel("Driver",   cc.xy(1,3));
//    builder.add(driverField,     cc.xy(3,3));    
//    builder.addLabel("URL",      cc.xy(1,5));
//    builder.add(urlField,        cc.xy(3,5));
//    builder.addLabel("Username", cc.xy(1,7));
//    builder.add(usernameField,   cc.xy(3,7));
//    builder.addLabel("Password", cc.xy(1,9));
//    builder.add(passwordField,   cc.xy(3,9));    
//    builder.add(connectButton,   cc.xy(3,11,CellConstraints.LEFT, CellConstraints.DEFAULT));

    FormLayout layout = new FormLayout( 
        "right:pref, 6dlu, fill:80dlu:grow",
        "");
    DefaultFormBuilder builder = new DefaultFormBuilder(layout, this); 
    builder.setDefaultDialogBorder();
    builder.appendSeparator("Database Properties"); 
    builder.append("Driver",  driverField); 
    builder.append("URL",    urlField); 
    builder.append("Username",  usernameField);
    builder.append("Password",    passwordField);
    // a little dirty work to get the button in column3, left-aligned, default size
    CellConstraints cc = new CellConstraints();
    builder.nextLine();
    builder.appendRow(new RowSpec("pref"));
    builder.add(connectButton, cc.xy(3, builder.getRow(), CellConstraints.LEFT, CellConstraints.DEFAULT));
  }
  
  // tester
  public static void main(String[] args)
  {
    JFrame f = new JFrame();
    f.setContentPane(new Db2AppConfigPanel());
    f.pack();
    f.setVisible(true);
  }

  public JTextField getDriverField()
  {
    return driverField;
  }

  public JTextField getUrlField()
  {
    return urlField;
  }

  public JTextField getUsernameField()
  {
    return usernameField;
  }

  public JTextField getPasswordField()
  {
    return passwordField;
  }

  public JButton getConnectButton()
  {
    return connectButton;
  }

}


