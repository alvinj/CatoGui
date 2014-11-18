import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Sun Dec 09 14:56:14 EST 2007
 */



/**
 * @author Alvin Alexander
 */
public class DatabasePropertiesForm extends JPanel {
	public DatabasePropertiesForm() {
		initComponents();
	}

	public JTextField getDriverTextField() {
		return driverTextField;
	}

	public JTextField getUrlTextField() {
		return urlTextField;
	}

	public JTextField getUsernameTextField() {
		return usernameTextField;
	}

	public JTextField getPasswordTextField() {
		return passwordTextField;
	}

	public JButton getConnectButton() {
		return connectButton;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		headerLabel = new JLabel();
		driverLabel = new JLabel();
		driverTextField = new JTextField();
		urlLabel = new JLabel();
		urlTextField = new JTextField();
		usernameLabel = new JLabel();
		usernameTextField = new JTextField();
		passwordLabel = new JLabel();
		passwordTextField = new JTextField();
		connectButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setLayout(new FormLayout(
			new ColumnSpec[] {
				new ColumnSpec(ColumnSpec.RIGHT, Sizes.DEFAULT, FormSpec.NO_GROW),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW),
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
				FormFactory.DEFAULT_ROWSPEC
			}));

		//---- headerLabel ----
		headerLabel.setText("Database Properties");
		add(headerLabel, cc.xywh(3, 1, 1, 1, CellConstraints.CENTER, CellConstraints.DEFAULT));

		//---- driverLabel ----
		driverLabel.setText("Driver");
		driverLabel.setLabelFor(driverTextField);
		add(driverLabel, cc.xy(1, 3));

		//---- driverTextField ----
		driverTextField.setColumns(20);
		add(driverTextField, cc.xy(3, 3));

		//---- urlLabel ----
		urlLabel.setText("URL");
		urlLabel.setLabelFor(urlTextField);
		add(urlLabel, cc.xy(1, 5));
		add(urlTextField, cc.xy(3, 5));

		//---- usernameLabel ----
		usernameLabel.setText("Username");
		usernameLabel.setLabelFor(usernameTextField);
		add(usernameLabel, cc.xy(1, 7));
		add(usernameTextField, cc.xy(3, 7));

		//---- passwordLabel ----
		passwordLabel.setText("Password");
		passwordLabel.setLabelFor(passwordTextField);
		add(passwordLabel, cc.xy(1, 9));
		add(passwordTextField, cc.xy(3, 9));

		//---- connectButton ----
		connectButton.setText("Connect");
		add(connectButton, cc.xywh(3, 11, 1, 1, CellConstraints.LEFT, CellConstraints.DEFAULT));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel headerLabel;
	private JLabel driverLabel;
	private JTextField driverTextField;
	private JLabel urlLabel;
	private JTextField urlTextField;
	private JLabel usernameLabel;
	private JTextField usernameTextField;
	private JLabel passwordLabel;
	private JTextField passwordTextField;
	private JButton connectButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
