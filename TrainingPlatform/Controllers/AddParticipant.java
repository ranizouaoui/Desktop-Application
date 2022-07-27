package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Classes.Participant;
import helpers.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddParticipant implements Initializable {

	@FXML
	private TextField namefield;
	@FXML
	private TextField lastnamefield;
	@FXML
	private DatePicker datefield;
	@FXML
	private PasswordField passwordfield;
	@FXML
	private Label AlertMessage;
	@FXML
	private Label savedlabel;
	@FXML
	private ComboBox<String> combobox;
	@FXML
	private Button exitbutton;
	String query2 = null;
	String query1 = null;
	Connection connection = null;
	ResultSet resultSet = null;
	PreparedStatement preparedStatement, preparedStatement1;
	Participant participant = null;
	int studentId;
	private String query;
	ObservableList<String> list = FXCollections.observableArrayList();
	private String name;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// ComboBox();
		ComboBox();
	}

	@FXML
	private void save(ActionEvent e) {

		connection = DataBase.connecterBase();
		String name = namefield.getText();
		String lastname = lastnamefield.getText();
		// String password = passwordfield.getText();
		String birth = String.valueOf(datefield.getValue());
		// String profile = combobox.getSelectionModel().getSelectedItem().toString();
		if (name.isBlank() || birth.isBlank() || lastname.isBlank()) {
			AlertMessage.setText("Invalide ! Try again ");
		} else {
			getQuery();
			insert();
			AlertMessage.setText("");
			savedlabel.setText("Participant successfully added ");
			clean();

		}

	}

	@FXML
	private void clean() {
		datefield.setValue(null);
		namefield.setText(null);
		lastnamefield.setText(null);

	}

	private void getQuery() {

		query2 = "INSERT INTO `participant`( `nom`, `prenom`, `birth`, `id_profile`) VALUES (?,?,?,?)";

	}

	private Integer getIdProfil(String libelle) throws SQLException {
		String query = "SELECT `Code_profil` FROM `profil` WHERE Libelle =?";
		preparedStatement1 = connection.prepareStatement(query);
		preparedStatement1.setString(1, libelle);
		ResultSet resultSet1 = preparedStatement1.executeQuery();
		if (resultSet1.next())
			return resultSet1.getInt("Code_profil");

		return null;
	}

	private void insert() {

		try {

			preparedStatement = connection.prepareStatement(query2);
			preparedStatement.setString(1, namefield.getText());
			preparedStatement.setString(2, lastnamefield.getText());
			// preparedStatement.setString(3, "PARTICIPANT");
			preparedStatement.setString(3, String.valueOf(datefield.getValue()));
			// preparedStatement.setString(4,
			// combobox.getSelectionModel().getSelectedItem().toString());
			preparedStatement.setInt(4, getIdProfil(combobox.getSelectionModel().getSelectedItem().toString()));
			preparedStatement.execute();
			// Connection connectDB = DataBase.connecterBase();

		} catch (SQLException ex) {
			Logger.getLogger(AddParticipant.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void cancelButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) exitbutton.getScene().getWindow();
		stage.close();

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
			Logger.getLogger(Page2simple.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
