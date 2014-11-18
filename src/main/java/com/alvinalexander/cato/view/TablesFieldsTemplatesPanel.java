package com.alvinalexander.cato.view;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Alvin Alexander
 */
public class TablesFieldsTemplatesPanel extends JPanel {
    public TablesFieldsTemplatesPanel() {
        initComponents();
    }

    public JScrollPane getTablesScrollPane() {
        return tablesScrollPane;
    }

    public JList getTablesJList() {
        return tablesJList;
    }

    public JScrollPane getFieldsScrollPane() {
        return fieldsScrollPane;
    }

    public JList getFieldsJList() {
        return fieldsJList;
    }

    public JScrollPane getTemplatesScrollPane() {
        return templatesScrollPane;
    }

    public JList getTemplatesJList() {
        return templatesJList;
    }

    public JButton getButton2() {
        return button2;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        headerLabel = new JLabel();
        helpText = new JLabel();
        tablesLabel = new JLabel();
        fieldsLabel = new JLabel();
        tablesScrollPane = new JScrollPane();
        tablesJList = new JList();
        fieldsScrollPane = new JScrollPane();
        fieldsJList = new JList();
        templatesLabel = new JLabel();
        templatesScrollPane = new JScrollPane();
        templatesJList = new JList();
        generateCodeButtonPanel = new JPanel();
        button2 = new JButton();
        CellConstraints cc = new CellConstraints();

        //======== this ========
        setLayout(new FormLayout(
            new ColumnSpec[] {
                new ColumnSpec(ColumnSpec.LEFT, Sizes.dluX(12), FormSpec.NO_GROW),
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, 0.5),
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                new ColumnSpec(ColumnSpec.LEFT, Sizes.DLUX6, FormSpec.NO_GROW),
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, 0.5),
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                new ColumnSpec(ColumnSpec.LEFT, Sizes.dluX(12), FormSpec.NO_GROW)
            },
            new RowSpec[] {
                new RowSpec(RowSpec.TOP, Sizes.dluY(12), FormSpec.NO_GROW),
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.PARAGRAPH_GAP_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                new RowSpec(RowSpec.FILL, Sizes.PREFERRED, 0.5),
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.UNRELATED_GAP_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                new RowSpec(RowSpec.FILL, Sizes.PREFERRED, FormSpec.DEFAULT_GROW),
                FormFactory.UNRELATED_GAP_ROWSPEC,
                new RowSpec(RowSpec.FILL, Sizes.PREFERRED, FormSpec.NO_GROW),
                FormFactory.LINE_GAP_ROWSPEC,
                new RowSpec(RowSpec.FILL, Sizes.DEFAULT, 0.30000000000000004),
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC
            }));

        //---- headerLabel ----
        headerLabel.setText("Generate Code");
        headerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
        add(headerLabel, cc.xy(3, 3));

        //---- helpText ----
        helpText.setText("Select a table, the desired fields, a template, and then generate your code.");
        add(helpText, cc.xywh(3, 5, 5, 1));

        //---- tablesLabel ----
        tablesLabel.setText("Tables");
        tablesLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
        add(tablesLabel, cc.xy(3, 9));

        //---- fieldsLabel ----
        fieldsLabel.setText("Fields");
        fieldsLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
        add(fieldsLabel, cc.xy(7, 9));

        //======== tablesScrollPane ========
        {

            //---- tablesJList ----
            tablesJList.setModel(new AbstractListModel() {
                String[] values = {
                    "Users",
                    "Notes",
                    "Contacts",
                    "Appointments"
                };
                public int getSize() { return values.length; }
                public Object getElementAt(int i) { return values[i]; }
            });
            tablesScrollPane.setViewportView(tablesJList);
        }
        add(tablesScrollPane, cc.xy(3, 11));

        //======== fieldsScrollPane ========
        {

            //---- fieldsJList ----
            fieldsJList.setModel(new AbstractListModel() {
                String[] values = {
                    "id",
                    "username",
                    "password",
                    "email_address",
                    "primary_role"
                };
                public int getSize() { return values.length; }
                public Object getElementAt(int i) { return values[i]; }
            });
            fieldsScrollPane.setViewportView(fieldsJList);
        }
        add(fieldsScrollPane, cc.xy(7, 11));

        //---- templatesLabel ----
        templatesLabel.setText("Templates");
        templatesLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
        add(templatesLabel, cc.xy(3, 15));

        //======== templatesScrollPane ========
        {

            //---- templatesJList ----
            templatesJList.setModel(new AbstractListModel() {
                String[] values = {
                    "PlayController",
                    "PlayDao",
                    "PlayModel",
                    "PlayListView",
                    "PlayEditForm"
                };
                public int getSize() { return values.length; }
                public Object getElementAt(int i) { return values[i]; }
            });
            templatesScrollPane.setViewportView(templatesJList);
        }
        add(templatesScrollPane, cc.xywh(3, 17, 5, 3));

        //======== generateCodeButtonPanel ========
        {
            generateCodeButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

            //---- button2 ----
            button2.setText("Generate Code");
            generateCodeButtonPanel.add(button2);
        }
        add(generateCodeButtonPanel, cc.xywh(1, 21, 9, 1));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel headerLabel;
    private JLabel helpText;
    private JLabel tablesLabel;
    private JLabel fieldsLabel;
    private JScrollPane tablesScrollPane;
    private JList tablesJList;
    private JScrollPane fieldsScrollPane;
    private JList fieldsJList;
    private JLabel templatesLabel;
    private JScrollPane templatesScrollPane;
    private JList templatesJList;
    private JPanel generateCodeButtonPanel;
    private JButton button2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}


