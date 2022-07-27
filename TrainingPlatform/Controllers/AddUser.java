package Controllers;

import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import Classes.CryptoUtils;
import Classes.User;
import helpers.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddUser implements Initializable {

	@FXML
	private TextField loginfiled;
	@FXML
	private PasswordField passwordfield;
	@FXML
	private Label AlertMessage;
	@FXML
	private Label savedlabel;
	@FXML
	private Button exitbutton;
	@FXML
	private ComboBox<String> combobox;
	String query2 = null;
	String query1 = null;
	Connection connection = null;
	ResultSet resultSet = null;
	PreparedStatement preparedStatement, preparedStatement1;
	User user = null;
	boolean aa = false;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ObservableList<String> list = FXCollections.observableArrayList("ADMIN", "SIMPLE");
		combobox.setItems(list);
	}

	@FXML
	private void save(ActionEvent e) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
		connection = DataBase.connecterBase();
		String login = loginfiled.getText();
		String password = passwordfield.getText();
		String role = combobox.getSelectionModel().getSelectedItem().toString();
		// String profile = combobox.getSelectionModel().getSelectedItem().toString();
		if (login.isBlank() || password.isBlank() || role.isBlank()) {
			AlertMessage.setText("Invalid ! Try again ");
		} else {
			if (aa == false) {
				Connection connectDB = DataBase.connecterBase();
				String veriflogin = "SELECT COUNT(1) FROM `user` WHERE user_name = '" + loginfiled.getText() + "'";
				try {
					Statement statement = connectDB.createStatement();
					ResultSet queryResult = statement.executeQuery(veriflogin);
					while (queryResult.next()) {
						if (queryResult.getInt(1) >= 1) {
							savedlabel.setText("");
							AlertMessage.setText("Username Already Exists! Try again ");
						} else {
							aa = true;
							System.out.println("ddd");
						}
					}
				} catch (Exception e1) {
					e1.fillInStackTrace();
				}
			}

			if (aa == true) {
				getQuery();
				insert();
				AlertMessage.setText("");
				savedlabel.setText("User successfully added ");
				clean();
			}
		}

	}

	@FXML
	private void clean() {
		loginfiled.setText(null);
		passwordfield.setText(null);
//		rolefiled.setText(null);

	}

	private void getQuery() {

		query2 = "INSERT INTO `user`(`user_name`, `user_password`, `role`) VALUES (?,?,?)";
		// query1 = "INSERT INTO `user`( `user_name`, `user_password`, `role`) VALUES
		// (?,?,?)";
	}

	private void insert() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
		try {

			preparedStatement = connection.prepareStatement(query2);
			preparedStatement.setString(1, loginfiled.getText());
			preparedStatement.setString(2, CryptoUtils.Encrypt(passwordfield.getText()));
			preparedStatement.setString(3, combobox.getSelectionModel().getSelectedItem().toString());
			preparedStatement.execute();
			Connection connectDB = DataBase.connecterBase();

		} catch (SQLException ex) {
			Logger.getLogger(AddParticipant.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public void cancelButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) exitbutton.getScene().getWindow();
		stage.close();

	}

	@FXML
	void Select(ActionEvent event) {
		// String s = combobox.getSelectionModel().getSelectedItem().toString();
		// label.setText(s);
	}

}
