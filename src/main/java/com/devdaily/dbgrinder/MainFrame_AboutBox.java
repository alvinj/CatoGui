package com.devdaily.dbgrinder;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Title:
 * Description:
 * Copyright:
 * Company: devdaily.com
 * @author Alvin J. Alexander
 * @version
 */

public class MainFrame_AboutBox extends JDialog implements ActionListener
{

  JPanel panel1 = new JPanel();
  JPanel insetsPanel1 = new JPanel();
  JButton button1 = new JButton();
  BorderLayout borderLayout1 = new BorderLayout();
  String product = "DevDaily.com KickStart";
  String version = "version unknown";
  String copyright = "copyright devdaily.com, 2001. all rights reserved.";
  String comments = "comments";
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea jTextArea1 = new JTextArea();
  public MainFrame_AboutBox(Frame parent)
  {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    pack();
  }
  /**Component initialization*/
  private void jbInit() throws Exception
  {
    //imageLabel.setIcon(new ImageIcon(MainFrame_AboutBox.class.getResource("[Your Image]")));
    this.setModal(true);
    this.setTitle("Help on DevDaily.com KickStart");
    panel1.setLayout(borderLayout1);
    button1.setText("Ok");
    button1.addActionListener(this);
    jTextArea1.setText(getAboutText());
    jTextArea1.setEditable(false);
    jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 12));
    this.getContentPane().add(panel1, null);
    insetsPanel1.add(button1, null);
    panel1.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(jTextArea1, null);
    panel1.add(insetsPanel1, BorderLayout.SOUTH);
  }

  private String getAboutText()
  {
    String aboutText = "product:   devdaily.com DbGrinder.\n"
                     + "copyright: devdaily.com, 2001. all rights reserved.\n"
                     + "version:   unknown.\n"
                     + "\n"
                     + "usage:\n"
                     + "------\n"
                     + "  java com.devdaily.dbgrinder.Main dbDriver dbURL\n"
                     + "\n"
                     + "purpose:\n"
                     + "--------\n"
                     + "  this program is used to generate (a) JavaBeans and\n"
                     + "  (b) SQL-related Java access methods, like insert(),\n"
                     + "  update(), delete(), and select().\n"
                     + "\n"
                     + "  the first thing you need to do is provide the database\n"
                     + "  driver name and url at the command line when you start\n"
                     + "  this program.\n"
                     + "\n"
                     + "  after that, generating a JavaBean is a 2-step process: \n"
                     + "    1. select a table within the database to read from\n"
                     + "    2. generate the bean\n"
                     + "\n"
                     + "  generating a db-access method is a 4-step process:\n"
                     + "    1. select a table within the database to read from\n"
                     + "    2. select the type of db-access method to generate\n"
                     + "       (select, insert, update, delete)\n"
                     + "    3. select the fields your db-access method should use \n"
                     + "    4. generate the db-access method\n"
                     + "\n"
                     + "requirements:\n"
                     + "-------------\n"
                     + "  1. you need to have your JDBC database driver in your classpath. \n"
                     + "  2. you need a JVM v1.2 or more recent.\n"
                     + "\n"
                     + "\n"
                     + "sample stuff for mysql:\n"
                     + "-----------------------\n"
                     + "  sample url:     jdbc:mysql://localhost/devdaily\n"
                     + "  sample driver:  org.gjt.mm.mysql.Driver\n"
                     + "\n"
                     + "sample stuff for postgresql:\n"
                     + "----------------------------\n"
                     + "  sample url:     jdbc:postgresql://host:port/database\n"
                     + "  sample driver:  org.postgresql.Driver \n"
                     + "\n";
    return aboutText;
  }

  /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e)
  {
    if (e.getID() == WindowEvent.WINDOW_CLOSING)
    {
      cancel();
    }
    super.processWindowEvent(e);
  }
  /**Close the dialog*/
  void cancel()
  {
    dispose();
  }
  /**Close the dialog on a button event*/
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == button1)
    {
      cancel();
    }
  }
}