import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Sun Dec 09 15:19:59 EST 2007
 */



/**
 * @author Alvin Alexander
 */
public class ListAndButtonsForm extends JPanel {
	public ListAndButtonsForm() {
		initComponents();
	}

	public JList getDatabaseTableList() {
		return databaseTableList;
	}

	public JButton getGenerateBeanButton() {
		return generateBeanButton;
	}

	public JButton getGenerateCrudButton() {
		return generateCrudButton;
	}

	public JButton getParseTemplateButton() {
		return parseTemplateButton;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		databaseTablesLabel = new JLabel();
		scrollPane1 = new JScrollPane();
		databaseTableList = new JList();
		generateBeanButton = new JButton();
		generateCrudButton = new JButton();
		parseTemplateButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setLayout(new FormLayout(
			new ColumnSpec[] {
				new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, 0.5),
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				new RowSpec(RowSpec.CENTER, Sizes.DEFAULT, 0.5),
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC
			}));

		//---- databaseTablesLabel ----
		databaseTablesLabel.setText("Database Tables");
		add(databaseTablesLabel, cc.xywh(1, 1, 1, 1, CellConstraints.CENTER, CellConstraints.DEFAULT));

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(databaseTableList);
		}
		add(scrollPane1, cc.xywh(1, 3, 1, 15));

		//---- generateBeanButton ----
		generateBeanButton.setText("Generate Bean");
		add(generateBeanButton, cc.xy(3, 3));

		//---- generateCrudButton ----
		generateCrudButton.setText("Generate CRUD Code");
		add(generateCrudButton, cc.xy(3, 5));

		//---- parseTemplateButton ----
		parseTemplateButton.setText("Parse Template");
		add(parseTemplateButton, cc.xy(3, 7));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel databaseTablesLabel;
	private JScrollPane scrollPane1;
	private JList databaseTableList;
	private JButton generateBeanButton;
	private JButton generateCrudButton;
	private JButton parseTemplateButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
