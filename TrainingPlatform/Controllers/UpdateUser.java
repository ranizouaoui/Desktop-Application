package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Classes.User;
import helpers.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateUser implements Initializable {

	@FXML
	private TextField loginfiled;

	@FXML
	private PasswordField passwordfield;

	@FXML
	private TextField rolefiled;

	@FXML
	private Label AlertMessage;
	@FXML
	private Label savedlabel;
	@FXML
	private Button exitbutton;

	private boolean update;
	int idfiled;

	String query = null;
	String query1 = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	User user = null;
	int index = -1;

	ObservableList<User> UserList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO

	}

	public void cancelButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) exitbutton.getScene().getWindow();
		stage.close();

	}

	@FXML
	private void save(ActionEvent e) {

		connection = DataBase.connecterBase();
		String login = loginfiled.getText();
		String password = passwordfield.getText();
		String role = rolefiled.getText();
		// String profile = combobox.getSelectionModel().getSelectedItem().toString();
		if (login.isBlank() || password.isBlank() || role.isBlank()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Fill All DATA");
			alert.showAndWait();
		} else {
			getQuery();
			insert();
			clean();

		}

	}

	private void insert() {

		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, loginfiled.getText());
			preparedStatement.setString(2, passwordfield.getText());
			preparedStatement.setString(3, rolefiled.getText());
			preparedStatement.execute();
			// Connection connectDB = DataBase.connecterBase();
		} catch (SQLException ex) {
			Logger.getLogger(UpdateUser.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@FXML
	private void clean() {
		loginfiled.setText(null);
		passwordfield.setText(null);
		rolefiled.setText(null);

	}

	private void getQuery() {

		if (update == false) {

			query = "INSERT INTO `user`(`user_name`, `user_password`, `role`) VALUES (?,?,?)";

		} else {
			query = "UPDATE `user` SET " + "`user_name`=?," + "`user_password`=?,"
					+ "`role`=?  WHERE code_utilisateur = '" + idfiled + "'";
		}

	}

	public void setUpdate(boolean b) {
		// TODO Auto-generated method stub

		this.update = b;

	}

	public void setTextField(int code_utilisateur, String user_name, String user_password, String role) {
		// TODO Auto-generated method stub
		idfiled = code_utilisateur;
		loginfiled.setText(user_name);
		passwordfield.setText(user_password);
		rolefiled.setText(role);

	}

}
