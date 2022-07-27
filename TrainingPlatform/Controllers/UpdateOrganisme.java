package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Classes.Organisme;
import helpers.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateOrganisme implements Initializable {

	@FXML
	private Button exitbutton;

	@FXML
	private TextField libellefiled;
	@FXML
	private Label AlertMessage;
	@FXML
	private Label savedlabel;

	private boolean update;
	int idfiled;
	String query = null;
	String query1 = null;
	Connection connection = null;
	ResultSet resultSet = null;
	PreparedStatement preparedStatement, preparedStatement1;
	Organisme organisme = null;

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
			savedlabel.setText("Organisme successfully added ");
			clean();

		}

	}

	private void insert() {

		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, libellefiled.getText());
			preparedStatement.execute();
			// Connection connectDB = DataBase.connecterBase();
		} catch (SQLException ex) {
			Logger.getLogger(UpdateDomaine.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void getQuery() {

		if (update == false) {

			query = "INSERT INTO `organisme`(`Libelle`) VALUES (?)";

		} else {
			query = "UPDATE `organisme` SET " + "`Libelle`=? WHERE id = '" + idfiled + "'";
		}

	}

	public void setUpdate(boolean b) {
		// TODO Auto-generated method stub

		this.update = b;

	}

	public void setTextField(int id, String libelle) {
		// TODO Auto-generated method stub
		idfiled = id;
		libellefiled.setText(libelle);

	}

	@FXML
	private void clean() {

		libellefiled.setText(null);

	}

	public void cancelButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) exitbutton.getScene().getWindow();
		stage.close();

	}
}
