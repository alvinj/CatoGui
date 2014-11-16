package com.devdaily.kickstart.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.jgoodies.forms.*;
import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.util.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

public class SourceCodeWarehouseMainPanel
extends JPanel
{
  
  public SourceCodeWarehouseMainPanel()
  {
    JTextField idField = new JTextField();
    JTextField powerField = new JTextField();
    JTextField lenField = new JTextField();
    JTextField daField = new JTextField();
    JTextField diField = new JTextField();
    JTextField da2Field = new JTextField();
    JTextField ptiField = new JTextField();
    JTextField rField = new JTextField();
    JTextField dField = new JTextField();
    JTextField di2Field = new JTextField();

    FormLayout layout = new FormLayout( 
        "right:max(40dlu;p), 4dlu, 80dlu, 7dlu, " // 1st major column 
      + "right:max(40dlu;p), 4dlu, 80dlu",        // 2nd major column 
        "");
//    FormLayout layout = new FormLayout( 
//        "pref, 3dlu, pref",
//        "");
    DefaultFormBuilder builder = new DefaultFormBuilder(layout, this); 
    builder.setDefaultDialogBorder(); 
    builder.appendSeparator("Foo"); 
    builder.append("Identifier",  idField); 
    builder.nextLine(); 

    builder.append("PTI [kW]",    ptiField); 
    builder.append("Power [kW]",  powerField); // auto-newline here?
    
    builder.append("len [mm]",    lenField); 
    builder.nextLine(); 

    builder.appendSeparator("Diameters"); 
    builder.append("da [mm]",     daField); 
    builder.append("di [mm]",     diField);
    
    builder.append("da2 [mm]",    da2Field); 
    builder.append("di2 [mm]",    di2Field); 
    
    builder.append("R [mm]",      rField); 
    builder.append("D [mm]",      dField);
  }
  
  // tester
  public static void main(String[] args)
  {
    JFrame f = new JFrame();
    f.setContentPane(new SourceCodeWarehouseMainPanel());
    f.pack();
    f.setVisible(true);
  }

}


