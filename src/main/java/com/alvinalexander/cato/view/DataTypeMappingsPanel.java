package com.alvinalexander.cato.view;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Sun Nov 23 14:34:46 MST 2014
 */



/**
 * @author Alvin Alexander
 */
public class DataTypeMappingsPanel extends JPanel {
	public DataTypeMappingsPanel() {
		initComponents();
	}

	// AJA added <String>
	public JComboBox<String> getBuiltInMappingsComboBox() {
		return builtInMappingsComboBox;
	}

	public JTextField getBlobTextField() {
		return blobTextField;
	}

	public JTextField getBooleanTextField() {
		return booleanTextField;
	}

	public JTextField getDateTextField() {
		return dateTextField;
	}

	public JTextField getIntegerTextField() {
		return integerTextField;
	}

	public JTextField getLongTextField() {
		return longTextField;
	}

	public JTextField getFloatTextField() {
		return floatTextField;
	}

	public JTextField getTextVarcharTextField() {
		return textVarcharTextField;
	}

	public JTextField getTimestampTextField() {
		return timestampTextField;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		headerLabel = new JLabel();
		helpLabel1 = new JLabel();
		helpLabel2 = new JLabel();
		helpLabel3 = new JLabel();
		panel1 = new JPanel();
		builtInMappingsLabel = new JLabel();
		builtInMappingsComboBox = new JComboBox();
		dbDataTypeLabel = new JLabel();
		outputTypeLabel = new JLabel();
		blobLabel = new JLabel();
		// AJA give one field an initial size; makes all textfields wider
		blobTextField = new JTextField(20);
		booleanLabel = new JLabel();
		booleanTextField = new JTextField();
		dateLabel = new JLabel();
		dateTextField = new JTextField();
		integerLabel = new JLabel();
		integerTextField = new JTextField();
		longLabel = new JLabel();
		longTextField = new JTextField();
		floatLabel = new JLabel();
		floatTextField = new JTextField();
		textVarcharLabel = new JLabel();
		textVarcharTextField = new JTextField();
		timestampLabel = new JLabel();
		timestampTextField = new JTextField();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setLayout(new FormLayout(
			new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				new ColumnSpec(ColumnSpec.LEFT, Sizes.DEFAULT, FormSpec.NO_GROW),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC
			},
			new RowSpec[] {
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				new RowSpec(RowSpec.TOP, Sizes.DLUY1, FormSpec.NO_GROW),
				FormFactory.DEFAULT_ROWSPEC,
				new RowSpec(RowSpec.TOP, Sizes.DLUY1, FormSpec.NO_GROW),
				FormFactory.DEFAULT_ROWSPEC,
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC
			}));

		//---- headerLabel ----
		headerLabel.setText("Data Type Mappings");
		headerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		add(headerLabel, cc.xywh(3, 3, 9, 1));

		//---- helpLabel1 ----
		helpLabel1.setText("Use this form to set the data types that will be used in your generated code.");
		add(helpLabel1, cc.xywh(3, 5, 9, 1));

		//---- helpLabel2 ----
		helpLabel2.setText("For each database data type, supply what it should be mapped to.");
		add(helpLabel2, cc.xywh(3, 7, 9, 1));

		//---- helpLabel3 ----
		helpLabel3.setText("Use the dropdown list to choose a built-in data type mapping.");
		add(helpLabel3, cc.xywh(3, 9, 9, 1));

		//======== panel1 ========
		{
			panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

			//---- builtInMappingsLabel ----
			builtInMappingsLabel.setText("Built-in Type Mappings: ");
			panel1.add(builtInMappingsLabel);

			//---- builtInMappingsComboBox ----
			builtInMappingsComboBox.setModel(new DefaultComboBoxModel(new String[] {
				"Java",
				"JSON",
				"PHP",
				"Play",
				"Scala"
			}));
			panel1.add(builtInMappingsComboBox);
		}
		add(panel1, cc.xywh(3, 12, 9, 1));

		//---- dbDataTypeLabel ----
		dbDataTypeLabel.setText("DB Data Type");
		dbDataTypeLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		add(dbDataTypeLabel, cc.xy(3, 18));

		//---- outputTypeLabel ----
		outputTypeLabel.setText("Output Type");
		outputTypeLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		add(outputTypeLabel, cc.xy(7, 18));

		//---- blobLabel ----
		blobLabel.setText("blob");
		blobLabel.setFont(new Font("Monaco", Font.PLAIN, 12));
		add(blobLabel, cc.xy(3, 20));
		add(blobTextField, cc.xy(7, 20));

		//---- booleanLabel ----
		booleanLabel.setText("boolean");
		booleanLabel.setFont(new Font("Monaco", Font.PLAIN, 12));
		add(booleanLabel, cc.xy(3, 22));
		add(booleanTextField, cc.xy(7, 22));

		//---- dateLabel ----
		dateLabel.setText("date");
		dateLabel.setFont(new Font("Monaco", Font.PLAIN, 12));
		add(dateLabel, cc.xy(3, 24));
		add(dateTextField, cc.xy(7, 24));

		//---- integerLabel ----
		integerLabel.setText("integer");
		integerLabel.setFont(new Font("Monaco", Font.PLAIN, 12));
		add(integerLabel, cc.xy(3, 26));
		add(integerTextField, cc.xy(7, 26));

		//---- longLabel ----
		longLabel.setText("long");
		longLabel.setFont(new Font("Monaco", Font.PLAIN, 12));
		add(longLabel, cc.xy(3, 28));
		add(longTextField, cc.xy(7, 28));

		//---- floatLabel ----
		floatLabel.setText("float");
		floatLabel.setFont(new Font("Monaco", Font.PLAIN, 12));
		add(floatLabel, cc.xy(3, 30));
		add(floatTextField, cc.xy(7, 30));

		//---- textVarcharLabel ----
		textVarcharLabel.setText("text/varchar");
		textVarcharLabel.setFont(new Font("Monaco", Font.PLAIN, 12));
		add(textVarcharLabel, cc.xy(3, 32));
		add(textVarcharTextField, cc.xy(7, 32));

		//---- timestampLabel ----
		timestampLabel.setText("timestamp");
		timestampLabel.setFont(new Font("Monaco", Font.PLAIN, 12));
		add(timestampLabel, cc.xy(3, 34));
		add(timestampTextField, cc.xy(7, 34));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel headerLabel;
	private JLabel helpLabel1;
	private JLabel helpLabel2;
	private JLabel helpLabel3;
	private JPanel panel1;
	private JLabel builtInMappingsLabel;
    // AJA added <String>
	private JComboBox<String> builtInMappingsComboBox;
	private JLabel dbDataTypeLabel;
	private JLabel outputTypeLabel;
	private JLabel blobLabel;
	private JTextField blobTextField;
	private JLabel booleanLabel;
	private JTextField booleanTextField;
	private JLabel dateLabel;
	private JTextField dateTextField;
	private JLabel integerLabel;
	private JTextField integerTextField;
	private JLabel longLabel;
	private JTextField longTextField;
	private JLabel floatLabel;
	private JTextField floatTextField;
	private JLabel textVarcharLabel;
	private JTextField textVarcharTextField;
	private JLabel timestampLabel;
	private JTextField timestampTextField;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
