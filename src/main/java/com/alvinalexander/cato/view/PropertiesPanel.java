package com.alvinalexander.cato.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Alvin Alexander
 */
public class PropertiesPanel extends JPanel {
    public PropertiesPanel() {
        initComponents();
    }

    public JTextField getUrlTextField() {
        return urlTextField;
    }

    public JTextField getDriverTextField() {
        return driverTextField;
    }

    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public JTextField getPasswordTextField() {
        return passwordTextField;
    }

    public JButton getConnectDisconnectButton() {
        return connectDisconnectButton;
    }

    public JLabel getConnectSuccessLabel() {
        return connectSuccessLabel;
    }

    public JTextField getTemplatesDirectoryTextField() {
        return templatesDirectoryTextField;
    }

    public JButton getSelectTemplatesDirectoryButton() {
        return selectTemplatesDirectoryButton;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        headerLabel = new JLabel();
        headerHelpTextLabel = new JLabel();
        databasePropertiesPanel = new JPanel();
        urlLabel = new JLabel();
        urlTextField = new JTextField();
        driverLabel = new JLabel();
        driverTextField = new JTextField();
        usernameLabel = new JLabel();
        usernameTextField = new JTextField();
        passwordLabel = new JLabel();
        passwordTextField = new JTextField();
        connectButtonPanel = new JPanel();
        connectDisconnectButton = new JButton();
        connectSuccessLabel = new JLabel();
        templatesPropertiesPanel = new JPanel();
        templatesDirectoryLabel = new JLabel();
        templatesDirectoryTextField = new JTextField();
        selectTemplatesDirectoryButton = new JButton();
        CellConstraints cc = new CellConstraints();

        //======== this ========
        setLayout(new FormLayout(
            new ColumnSpec[] {
                FormFactory.UNRELATED_GAP_COLSPEC,
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                FormFactory.PREF_COLSPEC,
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                FormFactory.PREF_COLSPEC,
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                FormFactory.PREF_COLSPEC,
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                new ColumnSpec(ColumnSpec.FILL, Sizes.PREFERRED, 0.6),
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                new ColumnSpec(ColumnSpec.LEFT, Sizes.dluX(12), FormSpec.NO_GROW)
            },
            new RowSpec[] {
                FormFactory.PARAGRAPH_GAP_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                new RowSpec(RowSpec.TOP, Sizes.dluY(12), FormSpec.NO_GROW),
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.PARAGRAPH_GAP_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC
            }));

        //---- headerLabel ----
        headerLabel.setText("Properties");
        headerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
        add(headerLabel, cc.xywh(3, 3, 7, 1));

        //---- headerHelpTextLabel ----
        headerHelpTextLabel.setText("Enter your database properties and select the directory where your templates are located.");
        add(headerHelpTextLabel, cc.xywh(3, 5, 7, 1));

        //======== databasePropertiesPanel ========
        {
            databasePropertiesPanel.setBorder(new TitledBorder(" Database "));
            databasePropertiesPanel.setLayout(new FormLayout(
                new ColumnSpec[] {
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW),
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC
                },
                new RowSpec[] {
                    FormFactory.DEFAULT_ROWSPEC,
                    FormFactory.LINE_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC,
                    FormFactory.LINE_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC,
                    FormFactory.LINE_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC,
                    FormFactory.LINE_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC,
                    FormFactory.LINE_GAP_ROWSPEC,
                    FormFactory.PARAGRAPH_GAP_ROWSPEC
                }));

            //---- urlLabel ----
            urlLabel.setText("URL");
            databasePropertiesPanel.add(urlLabel, cc.xy(3, 1));
            databasePropertiesPanel.add(urlTextField, cc.xy(5, 1));

            //---- driverLabel ----
            driverLabel.setText("Driver");
            databasePropertiesPanel.add(driverLabel, cc.xy(3, 3));
            databasePropertiesPanel.add(driverTextField, cc.xy(5, 3));

            //---- usernameLabel ----
            usernameLabel.setText("Username");
            databasePropertiesPanel.add(usernameLabel, cc.xy(3, 5));
            databasePropertiesPanel.add(usernameTextField, cc.xy(5, 5));

            //---- passwordLabel ----
            passwordLabel.setText("Password");
            databasePropertiesPanel.add(passwordLabel, cc.xy(3, 7));
            databasePropertiesPanel.add(passwordTextField, cc.xy(5, 7));

            //======== connectButtonPanel ========
            {
                connectButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- connectDisconnectButton ----
                connectDisconnectButton.setText("Connect");
                connectButtonPanel.add(connectDisconnectButton);

                //---- connectSuccessLabel ----
                connectSuccessLabel.setText("connectSuccessLabel");
                connectButtonPanel.add(connectSuccessLabel);
            }
            databasePropertiesPanel.add(connectButtonPanel, cc.xy(5, 9));
        }
        add(databasePropertiesPanel, cc.xywh(3, 7, 7, 1));

        //======== templatesPropertiesPanel ========
        {
            templatesPropertiesPanel.setBorder(new TitledBorder(" Templates "));
            templatesPropertiesPanel.setLayout(new FormLayout(
                new ColumnSpec[] {
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, 0.7000000000000001),
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    new ColumnSpec(ColumnSpec.FILL, Sizes.PREFERRED, 0.2),
                    FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC
                },
                new RowSpec[] {
                    FormFactory.DEFAULT_ROWSPEC,
                    FormFactory.LINE_GAP_ROWSPEC,
                    FormFactory.PARAGRAPH_GAP_ROWSPEC,
                    FormFactory.LINE_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC,
                    FormFactory.LINE_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC,
                    FormFactory.LINE_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC
                }));

            //---- templatesDirectoryLabel ----
            templatesDirectoryLabel.setText("Templates Directory:");
            templatesPropertiesPanel.add(templatesDirectoryLabel, cc.xy(3, 1));
            templatesPropertiesPanel.add(templatesDirectoryTextField, cc.xywh(5, 1, 3, 1));

            //---- selectTemplatesDirectoryButton ----
            selectTemplatesDirectoryButton.setText("Select...");
            templatesPropertiesPanel.add(selectTemplatesDirectoryButton, cc.xywh(9, 1, 2, 1));
        }
        add(templatesPropertiesPanel, cc.xywh(3, 11, 7, 1));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel headerLabel;
    private JLabel headerHelpTextLabel;
    private JPanel databasePropertiesPanel;
    private JLabel urlLabel;
    private JTextField urlTextField;
    private JLabel driverLabel;
    private JTextField driverTextField;
    private JLabel usernameLabel;
    private JTextField usernameTextField;
    private JLabel passwordLabel;
    private JTextField passwordTextField;
    private JPanel connectButtonPanel;
    private JButton connectDisconnectButton;
    private JLabel connectSuccessLabel;
    private JPanel templatesPropertiesPanel;
    private JLabel templatesDirectoryLabel;
    private JTextField templatesDirectoryTextField;
    private JButton selectTemplatesDirectoryButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}



