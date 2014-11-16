package **PACKAGE_NAME**.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

import **PACKAGE_NAME**.controller.*;
import **PACKAGE_NAME**.dao.*;
import **PACKAGE_NAME**.model.*;


public class **CLASS_NAME**EntryDialog extends JDialog
{
  protected JPanel panel1 = new JPanel();
  protected String errorMessage;

  protected boolean okButtonWasPressed = false;
  protected BorderLayout borderLayout1 = new BorderLayout();
  protected JPanel centerPanel = new JPanel();
  protected JPanel southPanel = new JPanel();
  protected FlowLayout flowLayout1 = new FlowLayout();
  protected JButton cancelButton = new JButton();
  protected JButton okButton = new JButton();
  XYLayout xYLayout1 = new XYLayout();

  // repeat here for each field
  **FOREACH_FIELD**
  JTextField **CLASS_INSTANCE_NAME**TextField = new JTextField();
  **END_FOREACH_FIELD**

  // repeat here for each field
  **FOREACH_FIELD**
  JLabel **CLASS_INSTANCE_NAME**Label = new JJLabel();
  **END_FOREACH_FIELD**

  public DataEntryDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public DataEntryDialog() {
    this(null, "", false);
  }

  private void jbInit() throws Exception
  {
    panel1.setLayout(borderLayout1);
    southPanel.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new DataEntryDialog_cancelButton_actionAdapter(this));
    okButton.setText("OK");
    okButton.addActionListener(new DataEntryDialog_okButton_actionAdapter(this));
    centerPanel.setLayout(xYLayout1);
    getContentPane().add(panel1, BorderLayout.CENTER);
    panel1.add(centerPanel, BorderLayout.CENTER);

    // repeat here for each field
    **FOREACH_FIELD**
    **CLASS_INSTANCE_NAME**Label.setText("**CLASS_INSTANCE_NAME**:");
    **END_FOREACH_FIELD**

    // repeat here for each field
    // TODO: increment the second data column by 25 points for each instance (70,95, ...)
    **FOREACH_FIELD**
    centerPanel.add(**CLASS_INSTANCE_NAME**Label,  new XYConstraints(83, 70, 53, -1));
    **END_FOREACH_FIELD**

    // repeat here for each field
    // TODO: increment the second data column by 25 points for each instance (70,95, ...)
    **FOREACH_FIELD**
    centerPanel.add(**CLASS_INSTANCE_NAME**TextField,  new XYConstraints(151, 68, 75, -1));
    **END_FOREACH_FIELD**

    panel1.add(southPanel,  BorderLayout.SOUTH);
    southPanel.add(okButton, null);
    southPanel.add(cancelButton, null);
  }

  public boolean okButtonWasPressed()
  {
    return okButtonWasPressed;
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    okButtonWasPressed = false;
    this.dispose();
  }

  void okButton_actionPerformed(ActionEvent e) {
    okButtonWasPressed = true;
    this.dispose();
  }
}

class DataEntryDialog_okButton_actionAdapter implements java.awt.event.ActionListener {
  DataEntryDialog adaptee;

  DataEntryDialog_okButton_actionAdapter(DataEntryDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.okButton_actionPerformed(e);
  }
}

class DataEntryDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
  DataEntryDialog adaptee;

  DataEntryDialog_cancelButton_actionAdapter(DataEntryDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}
