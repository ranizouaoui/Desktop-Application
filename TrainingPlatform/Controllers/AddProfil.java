package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Classes.Profil;
import helpers.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProfil implements Initializable {

	@FXML
	private Button exitbutton;

	@FXML
	private TextField libellefiled;
	@FXML
	private Label AlertMessage;
	@FXML
	private Label savedlabel;
	String query2 = null;
	String query1 = null;
	Connection connection = null;
	ResultSet resultSet = null;
	PreparedStatement preparedStatement, preparedStatement1;
	Profil profil = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void save(ActionEvent e) {

		connection = DataBase.connecterBase();
		// String code = codefiled.getText();
		String libelle = libellefiled.getText();
		// String profile = combobox.getSelectionModel().getSelectedItem().toString();
		if (libelle.isBlank()) {
			AlertMessage.setText("Invalide ! Try again ");
		} else {
			getQuery();
			insert();
			AlertMessage.setText("");
			savedlabel.setText("Profil successfully added ");
			clean();

		}

	}

	@FXML
	private void clean() {
		// codefiled.setText(null);
		libellefiled.setText(null);

	}

	private void getQuery() {

		query2 = "INSERT INTO `profil`(`Libelle`) VALUES (?)";
	}

	private void insert() {

		try {

			preparedStatement = connection.prepareStatement(query2);
			// preparedStatement.setString(1, codefiled.getText());
			preparedStatement.setString(1, libellefiled.getText());
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

}
