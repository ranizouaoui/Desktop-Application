package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Classes.Trainer;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateTrainer implements Initializable {

	@FXML
	private TextField namefield;

	@FXML
	private TextField lastnamefield;

	@FXML
	private TextField emailfield;
	@FXML
	private TextField numfield;

	@FXML
	private ComboBox<String> combobox;

	@FXML
	private Label AlertMessage;
	@FXML
	private Label savedlabel;
	@FXML
	private Button exitbutton;

	private boolean update;
	int idfiled;
	private String name;
	String query = null;
	String query1 = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	User user = null;
	int index = -1;

	ObservableList<Trainer> trainerList = FXCollections.observableArrayList();
	ObservableList<String> list = FXCollections.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ComboBox();

	}

	public void cancelButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) exitbutton.getScene().getWindow();
		stage.close();

	}

	@FXML
	private void save(ActionEvent e) {

		connection = DataBase.connecterBase();
		String name = namefield.getText();
		String lastname = lastnamefield.getText();
		String email = emailfield.getText();
		String numtel = numfield.getText();
		String organisme = combobox.getSelectionModel().getSelectedItem().toString();
		if (name.isBlank() || lastname.isBlank() || email.isBlank() || numtel.isEmpty() || organisme.isEmpty()) {
			AlertMessage.setText("Invalide ! Try again ");
		} else {
			getQuery();
			insert();
			AlertMessage.setText("");
			savedlabel.setText("Trainer successfully Updated ");
			clean();

		}

	}

	private void insert() {

		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, namefield.getText());
			preparedStatement.setString(2, lastnamefield.getText());
			preparedStatement.setString(3, combobox.getSelectionModel().getSelectedItem().toString());
			preparedStatement.setString(4, emailfield.getText());
			preparedStatement.setString(5, numfield.getText());
			preparedStatement.execute();
			// Connection connectDB = DataBase.connecterBase();
		} catch (SQLException ex) {
			Logger.getLogger(UpdateTrainer.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@FXML
	private void clean() {
		namefield.setText(null);
		lastnamefield.setText(null);
		combobox.setAccessibleText(null);
		emailfield.setText(null);
		numfield.setText(null);
	}

	private void getQuery() {

		if (update == false) {
			query = "INSERT INTO `participant`( `nom`, `prenom`, `birth`, `profile`) VALUES (?,?,?,?)";
		} else {
			query = "UPDATE `formateur` SET " + "`Nom`=?," + "`Prenom`=?," + "`Organisme`=?," + "`Email`=?,"
					+ "`Ntelephone`=? WHERE Matricule_participant = '" + idfiled + "'";
		}

	}

	public void setUpdate(boolean b) {
		// TODO Auto-generated method stub

		this.update = b;

	}

	public void setTextField(int code_utilisateur, String user_name, String prenom, String email, String num) {
		// TODO Auto-generated method stub
		idfiled = code_utilisateur;
		namefield.setText(user_name);
		lastnamefield.setText(prenom);
		// combobox.setPromptText(organisme);
		emailfield.setText(email);
		numfield.setText(num);

	}

	@FXML
	void ComboBox() {
		try {
			connection = DataBase.connecterBase();
			query = "SELECT Libelle FROM `profil`";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				name = resultSet.getString("Libelle");
				list.add(name);
				combobox.setItems(list);

			}
			connection.close();
			resultSet.close();
			combobox.setItems(list);

		} catch (SQLException ex) {
			Logger.getLogger(Page1simple.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
