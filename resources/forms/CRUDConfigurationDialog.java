import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Sun Dec 09 19:44:13 EST 2007
 */



/**
 * @author Alvin Alexander
 */
public class CRUDConfigurationDialog extends JDialog {
	public CRUDConfigurationDialog(Frame owner) {
		super(owner);
		initComponents();
	}

	public CRUDConfigurationDialog(Dialog owner) {
		super(owner);
		initComponents();
	}

	public JRadioButton getSelectRadioButton() {
		return selectRadioButton;
	}

	public JRadioButton getInsertRadioButton() {
		return insertRadioButton;
	}

	public JRadioButton getUpdateRadioButton() {
		return updateRadioButton;
	}

	public JRadioButton getDeleteRadioButton() {
		return deleteRadioButton;
	}

	public JList getAvailableTableFieldsList() {
		return availableTableFieldsList;
	}

	public JButton getMoveRightButton() {
		return moveRightButton;
	}

	public JButton getMoveAllRightButton() {
		return moveAllRightButton;
	}

	public JButton getMoveAllLeftButton() {
		return moveAllLeftButton;
	}

	public JButton getMoveLeftButton() {
		return moveLeftButton;
	}

	public JList getSelectedTableFieldsList() {
		return selectedTableFieldsList;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		label1 = new JLabel();
		panel1 = new JPanel();
		selectRadioButton = new JRadioButton();
		insertRadioButton = new JRadioButton();
		updateRadioButton = new JRadioButton();
		deleteRadioButton = new JRadioButton();
		label2 = new JLabel();
		label3 = new JLabel();
		scrollPane1 = new JScrollPane();
		availableTableFieldsList = new JList();
		vcrButtonPanel = new JPanel();
		moveRightButton = new JButton();
		moveAllRightButton = new JButton();
		moveAllLeftButton = new JButton();
		moveLeftButton = new JButton();
		scrollPane2 = new JScrollPane();
		selectedTableFieldsList = new JList();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(Borders.DIALOG_BORDER);
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new FormLayout(
					new ColumnSpec[] {
						new ColumnSpec(ColumnSpec.RIGHT, Sizes.DEFAULT, FormSpec.NO_GROW),
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW)
					},
					new RowSpec[] {
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.UNRELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						new RowSpec(RowSpec.TOP, Sizes.DEFAULT, FormSpec.NO_GROW),
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC
					}));

				//---- label1 ----
				label1.setText("Database Method");
				contentPanel.add(label1, cc.xywh(3, 1, 5, 1));

				//======== panel1 ========
				{
					panel1.setLayout(new FormLayout(
						new ColumnSpec[] {
							FormFactory.DEFAULT_COLSPEC,
							FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
							FormFactory.DEFAULT_COLSPEC,
							FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
							FormFactory.DEFAULT_COLSPEC,
							FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
							FormFactory.DEFAULT_COLSPEC
						},
						RowSpec.decodeSpecs("default")));

					//---- selectRadioButton ----
					selectRadioButton.setText("Select");
					selectRadioButton.setSelected(true);
					panel1.add(selectRadioButton, cc.xy(1, 1));

					//---- insertRadioButton ----
					insertRadioButton.setText("Insert");
					panel1.add(insertRadioButton, cc.xy(3, 1));

					//---- updateRadioButton ----
					updateRadioButton.setText("Update");
					panel1.add(updateRadioButton, cc.xy(5, 1));

					//---- deleteRadioButton ----
					deleteRadioButton.setText("Delete");
					panel1.add(deleteRadioButton, cc.xy(7, 1));
				}
				contentPanel.add(panel1, cc.xywh(3, 3, 7, 1));

				//---- label2 ----
				label2.setText("Available Fields");
				contentPanel.add(label2, cc.xy(3, 7));

				//---- label3 ----
				label3.setText("Selected Fields");
				contentPanel.add(label3, cc.xy(7, 7));

				//======== scrollPane1 ========
				{
					scrollPane1.setViewportView(availableTableFieldsList);
				}
				contentPanel.add(scrollPane1, cc.xy(3, 9));

				//======== vcrButtonPanel ========
				{
					vcrButtonPanel.setLayout(new FormLayout(
						ColumnSpec.decodeSpecs("default"),
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
							FormFactory.DEFAULT_ROWSPEC
						}));

					//---- moveRightButton ----
					moveRightButton.setText(">");
					moveRightButton.setToolTipText("Select highlighted fields");
					vcrButtonPanel.add(moveRightButton, cc.xy(1, 1));

					//---- moveAllRightButton ----
					moveAllRightButton.setText(">>");
					moveAllRightButton.setToolTipText("Select all fields");
					vcrButtonPanel.add(moveAllRightButton, cc.xy(1, 3));

					//---- moveAllLeftButton ----
					moveAllLeftButton.setText("<<");
					moveAllLeftButton.setToolTipText("Unselect all fields");
					vcrButtonPanel.add(moveAllLeftButton, cc.xy(1, 5));

					//---- moveLeftButton ----
					moveLeftButton.setText("<");
					moveLeftButton.setToolTipText("Unselect selected fields");
					vcrButtonPanel.add(moveLeftButton, cc.xy(1, 7));
				}
				contentPanel.add(vcrButtonPanel, cc.xy(5, 9));

				//======== scrollPane2 ========
				{
					scrollPane2.setViewportView(selectedTableFieldsList);
				}
				contentPanel.add(scrollPane2, cc.xy(7, 9));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
				buttonBar.setLayout(new FormLayout(
					new ColumnSpec[] {
						FormFactory.GLUE_COLSPEC,
						FormFactory.BUTTON_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.BUTTON_COLSPEC
					},
					RowSpec.decodeSpecs("pref")));

				//---- okButton ----
				okButton.setText("Generate");
				okButton.setToolTipText("Generate CRUD method");
				buttonBar.add(okButton, cc.xy(2, 1));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				cancelButton.setToolTipText("Cancel process");
				buttonBar.add(cancelButton, cc.xy(4, 1));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());

		//---- buttonGroup1 ----
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(selectRadioButton);
		buttonGroup1.add(insertRadioButton);
		buttonGroup1.add(updateRadioButton);
		buttonGroup1.add(deleteRadioButton);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JLabel label1;
	private JPanel panel1;
	private JRadioButton selectRadioButton;
	private JRadioButton insertRadioButton;
	private JRadioButton updateRadioButton;
	private JRadioButton deleteRadioButton;
	private JLabel label2;
	private JLabel label3;
	private JScrollPane scrollPane1;
	private JList availableTableFieldsList;
	private JPanel vcrButtonPanel;
	private JButton moveRightButton;
	private JButton moveAllRightButton;
	private JButton moveAllLeftButton;
	private JButton moveLeftButton;
	private JScrollPane scrollPane2;
	private JList selectedTableFieldsList;
	private JPanel buttonBar;
	private JButton okButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
