package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Classes.Training;
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

public class UpdateTraining implements Initializable {

	@FXML
	private TextField titleield;

	@FXML
	private TextField Périodefield;

	@FXML
	private TextField yearfield;
	@FXML
	private TextField monthfield;
	@FXML
	private TextField participantfield;
	@FXML
	private ComboBox<String> comboboxdomaine;
	@FXML
	private ComboBox<String> comboboxtrainer;
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
	PreparedStatement preparedStatement1 = null;
	ResultSet resultSet = null;
	ResultSet resultSet1 = null;
	int index = -1;

	ObservableList<Training> trainerList = FXCollections.observableArrayList();
	ObservableList<String> list = FXCollections.observableArrayList();
	ObservableList<String> list1 = FXCollections.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ComboBox();
		ComboBox1();

	}

	public void cancelButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) exitbutton.getScene().getWindow();
		stage.close();

	}

	@FXML
	private void save(ActionEvent e) {

		connection = DataBase.connecterBase();
		String name = titleield.getText();
		String lastname = participantfield.getText();
		String periode = Périodefield.getText();
		String numtel = yearfield.getText();
		String organisme = comboboxdomaine.getSelectionModel().getSelectedItem().toString();
		String formateur = comboboxtrainer.getSelectionModel().getSelectedItem().toString();
		String email = monthfield.getText();
		if (name.isBlank() || lastname.isBlank() || email.isBlank() || numtel.isEmpty() || organisme.isEmpty()
				|| formateur.isBlank() || periode.isBlank()) {
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
			preparedStatement.setString(1, titleield.getText());
			preparedStatement.setString(2, comboboxdomaine.getSelectionModel().getSelectedItem().toString());
			preparedStatement.setString(3, Périodefield.getText());
			preparedStatement.setString(4, yearfield.getText());
			preparedStatement.setString(5, monthfield.getText());
			preparedStatement.setString(6, comboboxtrainer.getSelectionModel().getSelectedItem().toString());
			preparedStatement.setString(7, participantfield.getText());
			preparedStatement.execute();
			// Connection connectDB = DataBase.connecterBase();
		} catch (SQLException ex) {
			Logger.getLogger(UpdateTraining.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@FXML
	private void clean() {
		titleield.setText(null);
		Périodefield.setText(null);
		yearfield.setText(null);
		monthfield.setText(null);
		participantfield.setText(null);
	}

	private void getQuery() {

		if (update == false) {
			query = "INSERT INTO `formation`( `Intitulé`, `Domaine`, `Nombre_jours`, `Annee`, `mois`, `Formateur`, `Nombre_participants`) VALUES VALUES (?,?,?,?,?,?,?)";
		} else {
			query = "UPDATE `formation` SET " + "`Intitulé`=?," + "`Domaine`=?," + "`Nombre_jours`=?," + "`Annee`=?,"
					+ "`mois`=?," + "`Formateur`=?," + "`Nombre_participants`=? WHERE Code_formation = '" + idfiled
					+ "'";
		}

	}

	public void setUpdate(boolean b) {
		// TODO Auto-generated method stub

		this.update = b;

	}

	public void setTextField(int code_utilisateur, String user_name, String profile, String oy, String organisme,
			String email, String num, String num1) {
		// TODO Auto-generated method stub
		idfiled = code_utilisateur;
		titleield.setText(user_name);
		comboboxdomaine.setPromptText(profile);
		Périodefield.setText(oy);
		yearfield.setText(organisme);
		monthfield.setText(email);
		comboboxtrainer.setPromptText(num);
		participantfield.setText(num1);
	}

	@FXML
	void ComboBox() {
		try {
			connection = DataBase.connecterBase();
			query = "SELECT Libelle FROM `domaine`";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				name = resultSet.getString("libelle");
				list.add(name);
				comboboxdomaine.setItems(list);

			}

			resultSet.close();
			comboboxdomaine.setItems(list);

		} catch (SQLException ex) {
			Logger.getLogger(Page3simple.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@FXML
	void ComboBox1() {
		try {
			query1 = "SELECT Nom FROM `formateur`";
			preparedStatement = connection.prepareStatement(query1);
			resultSet = preparedStatement.executeQuery();
			System.out.println("fuck");
			while (resultSet.next()) {

				name = resultSet.getString("Nom");
				list1.add(name);
				comboboxtrainer.setItems(list);

			}
			connection.close();
			resultSet.close();
			comboboxtrainer.setItems(list1);

		} catch (SQLException ex) {
			Logger.getLogger(Page3simple.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
