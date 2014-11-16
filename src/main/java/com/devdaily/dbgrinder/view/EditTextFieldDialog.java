package com.devdaily.dbgrinder.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import com.borland.jbcl.layout.*;

public class EditTextFieldDialog extends JDialog
{
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton okButton = new JButton();
  JButton cancelButton = new JButton();
  JPanel northJPanel = new JPanel();
  JLabel jLabel1 = new JLabel();
  JTextField jTextField = new JTextField();

  private boolean theUserClickedOk = false;
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public EditTextFieldDialog(Frame frame, String title, boolean modal)
  {
    super(frame, title, modal);
    try
    {
      jbInit();
      pack();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public EditTextFieldDialog()
  {
    this(null, "", false);
  }

  void jbInit() throws Exception
  {
    panel1.setLayout(borderLayout1);
    jPanel1.setLayout(flowLayout1);
    okButton.setText("OK");
    okButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        okButton_mouseClicked(e);
      }
    });
    cancelButton.setText("Cancel");
    cancelButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        cancelButton_mouseClicked(e);
      }
    });
    northJPanel.setLayout(gridBagLayout1);
    jLabel1.setText("Driver or URL:");
    // don't know how to properly set this size to a default value
    jTextField.setMaximumSize(new Dimension(200, 21));
    jTextField.setMinimumSize(new Dimension(100, 21));
    jTextField.setPreferredSize(new Dimension(100, 21));
    jTextField.setSize(new Dimension(100, 21));
    //jTextField.setText("                              ");
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(okButton, null);
    jPanel1.add(cancelButton, null);
    panel1.add(northJPanel, BorderLayout.NORTH);
    northJPanel.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(6, 24, 0, 0), 0, 0));
    northJPanel.add(jTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(6, 17, 0, 19), 162, 0));
  }

  public void setLabel(String s)
  {
    jLabel1.setText(s);
  }

  void cancelButton_mouseClicked(MouseEvent e)
  {
    this.dispose();
  }

  void okButton_mouseClicked(MouseEvent e)
  {
    this.theUserClickedOk = true;
    this.dispose();
  }

  public boolean userClickedOk()
  {
    System.err.println( "userClickedOk called " + theUserClickedOk );
    return this.theUserClickedOk;
  }

  public String getTextFromTextField()
  {
    return jTextField.getText().trim();
  }

  public void setDefaultTextForTextField (String s)
  {
    this.jTextField.setText(s);
  }

}