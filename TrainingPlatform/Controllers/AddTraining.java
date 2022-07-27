package Controllers;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddTraining implements Initializable {

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
	Checkcombo rr;
	ObservableList<Training> trainerList = FXCollections.observableArrayList();
	ObservableList<String> list = FXCollections.observableArrayList();
	ObservableList<String> list1 = FXCollections.observableArrayList();

	private int ss;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ComboBox();
		ComboBox1();

	}

	public void cancelButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) exitbutton.getScene().getWindow();
		stage.close();

	}

	private void save() {

		connection = DataBase.connecterBase();
		String name = titleield.getText();
		String periode = Périodefield.getText();
		String numtel = yearfield.getText();
		String organisme = comboboxdomaine.getSelectionModel().getSelectedItem().toString();
		String formateur = comboboxtrainer.getSelectionModel().getSelectedItem().toString();
		String email = monthfield.getText();
		if (name.isBlank() || email.isBlank() || numtel.isEmpty() || organisme.isEmpty() || formateur.isBlank()
				|| periode.isBlank()) {
			AlertMessage.setText("Invalide ! Try again ");
		} else {
			getQuery();
			insert();
//			select();
//			insert2();
			AlertMessage.setText("");
			savedlabel.setText("Training successfully Updated ");
//			clean();

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
			preparedStatement.execute();
//			System.out.println(titleield.getText());
//			System.out.println(comboboxdomaine.getSelectionModel().getSelectedItem().toString());
//			System.out.println(Périodefield.getText());
			// Connection connectDB = DataBase.connecterBase();
		} catch (SQLException ex) {
			Logger.getLogger(AddTraining.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

//	private void select() {
//		try {
//			connection = DataBase.connecterBase();
//			query1 = "SELECT MAX(Code_formation) FROM `formation`";
//			preparedStatement = connection.prepareStatement(query1);
//			resultSet = preparedStatement.executeQuery();
//
//			while (resultSet.next()) {
//				ss = resultSet.getInt(1);
//
//			}
//
//			resultSet.close();
//			comboboxdomaine.setItems(list);
//
//		} catch (SQLException ex) {
//			Logger.getLogger(AddTraining.class.getName()).log(Level.SEVERE, null, ex);
//		}
//
//	}

//	private void insert2() {
//		query = "INSERT INTO `per`( `id_formation`, `id_participant`) VALUES (?,?)";
//		try {
//			for (int i = 0; i < rr.list2.size(); i++) {
//				preparedStatement = connection.prepareStatement(query);
//				preparedStatement.setInt(1, ss);
//				preparedStatement.setInt(2, rr.list2.get(i));
//				preparedStatement.execute();
//
//			}
//
//		} catch (Exception e) {
//
//		}
//	}

	@FXML
	private void clean() {
		titleield.setText(null);
		Périodefield.setText(null);
		yearfield.setText(null);
		monthfield.setText(null);
		participantfield.setText(null);
	}

	private void getQuery() {
		query = "INSERT INTO `formation`( `Intitulé`, `Domaine`, `Nombre_jours`, `Annee`, `mois`, `Formateur`) VALUES (?,?,?,?,?,?)";

	}

	public void setUpdate(boolean b) {
		// TODO Auto-generated method stub

		this.update = b;

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
			Logger.getLogger(AddTraining.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@FXML
	void ComboBox1() {
		try {
			query1 = "SELECT Nom FROM `formateur`";
			preparedStatement = connection.prepareStatement(query1);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				name = resultSet.getString("Nom");
				list1.add(name);
				comboboxtrainer.setItems(list);

			}
			connection.close();
			resultSet.close();
			comboboxtrainer.setItems(list1);

		} catch (SQLException ex) {
			Logger.getLogger(AddTraining.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@FXML
	private void buttonAction(ActionEvent e) {
		try {
			save();
			Parent parent = FXMLLoader.load(getClass().getResource("/Views/checkcombo.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.getIcons().add(new Image("/Classes/icons/icons8_bulleted_list_40px.png"));
			stage.initStyle(StageStyle.UTILITY);
			stage.show();
		} catch (IOException ex) {
			Logger.getLogger(AddTraining.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
}
