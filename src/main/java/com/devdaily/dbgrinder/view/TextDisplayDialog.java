package com.devdaily.dbgrinder.view;

import java.awt.*;
import javax.swing.*;
import com.devdaily.utils.swing.DDDialog;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;

public class TextDisplayDialog extends DDDialog
{
  JPanel panel = new JPanel();
  BorderLayout borderLayout = new BorderLayout();
  JPanel buttonPanel = new JPanel();
  JScrollPane textScrollPane = new JScrollPane();
  JTextArea textArea = new JTextArea();
  FlowLayout flowLayout = new FlowLayout();

  JButton closeButton = new JButton();
  JButton clipboardButton = new JButton("Clipboard");

  public TextDisplayDialog(Frame frame, String title, boolean modal)
  {
    super(frame, title, modal);
    try
    {
      init();
      pack();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public TextDisplayDialog()
  {
    this(null, "", false);
  }
  void init() throws Exception
  {
    panel.setLayout(borderLayout);
    buttonPanel.setLayout(flowLayout);
    closeButton.setText("Close");
    closeButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        closeButton_mouseClicked(e);
      }
    });
    clipboardButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        clipboardButton_mouseClicked(e);
      }
    });
    textArea.setFont(new java.awt.Font("Monospaced", 0, 12));
    getContentPane().add(panel);
    panel.add(buttonPanel, BorderLayout.SOUTH);
    buttonPanel.add(clipboardButton, null);
    buttonPanel.add(closeButton, null);
    panel.add(textScrollPane, BorderLayout.CENTER);
    textScrollPane.getViewport().add(textArea, null);
  }
  
  protected void clipboardButton_mouseClicked(MouseEvent e)
  {
    writeToClipboard(textArea.getText(), null);
  }

  public void writeToClipboard(String s, ClipboardOwner owner)
  {
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    Transferable transferable = new StringSelection(s);
    clipboard.setContents(transferable, owner);  
  }

  void closeButton_mouseClicked(MouseEvent e)
  {
    this.dispose();
  }

  public void setText(String text)
  {
    textArea.setText(text);
  }

  @Override
  public void escapeCommandPerformed(KeyEvent e)
  {
    closeButton_mouseClicked(null);
  }

  @Override
  public void okCommandPerformed(KeyEvent e)
  {
    closeButton_mouseClicked(null);
  }
}