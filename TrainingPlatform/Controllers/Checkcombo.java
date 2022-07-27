package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import helpers.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Checkcombo implements Initializable {
	@FXML
	private Button savebutton;
	@FXML
	private ComboBox<String> combobox;
	@FXML
	private Label nb;
	@FXML
	private Label AlertMessage;
	String query = null;
	Connection connection = null;
	ResultSet resultSet = null;
	PreparedStatement preparedStatement;
	private String name;
	public int nbpart;
	private ObservableList<String> list = FXCollections.observableArrayList();
	private ObservableList<Integer> listee = FXCollections.observableArrayList();
//	public ObservableList<Integer> lis = FXCollections.observableArrayList();
	private int nbr, id_Training;
	private int a;
	private String query1;
	private int ss;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ComboBox();
	}

	@FXML
	void ComboBox() {
		try {
			connection = DataBase.connecterBase();
			query = "SELECT `Matricule_participant`, `nom` FROM `participant`";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				name = resultSet.getString("nom");
				nbr = resultSet.getInt("Matricule_participant");
				list.add(name);
				listee.add(nbr);
				combobox.setItems(list);

			}
			connection.close();
			resultSet.close();
			combobox.setItems(list);

		} catch (SQLException ex) {
			Logger.getLogger(Checkcombo.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@FXML
	void AddToTraining() {
		a = list.indexOf(combobox.getSelectionModel().getSelectedItem().toString());
//		list2.add(list1.get(a));
		// System.out.println(list2);
		InsertIntoPerTable(GetCurrentID(), listee.get(a));
		nb.setText("The number of participants is : " + ++nbpart);
		list.remove(combobox.getSelectionModel().getSelectedItem().toString());
		listee.remove(listee.get(a));
	}

	// UPDATE `formation` SET `Nombre_participants`= 5 WHERE Code_formation=11;
	@FXML
	void Save() {
		if (nbpart >= 4) {
			UpdateNombreParticipant();
			Stage stage = (Stage) savebutton.getScene().getWindow();
			stage.close();

		} else {

			nb.setText("The number of participants is insufficient ! ");
		}

	}

	int GetCurrentID() {
		try {
			connection = DataBase.connecterBase();
			query1 = "SELECT MAX(Code_formation) FROM `formation`";
			preparedStatement = connection.prepareStatement(query1);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ss = resultSet.getInt(1);

			}

			resultSet.close();

		} catch (SQLException ex) {
			Logger.getLogger(Checkcombo.class.getName()).log(Level.SEVERE, null, ex);
		}

		return ss;

	}

	private void InsertIntoPerTable(int x, int y) {
		query = "INSERT INTO `per`( `id_formation`, `id_participant`) VALUES (?,?)";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, x);
			preparedStatement.setInt(2, y);
			preparedStatement.execute();

		} catch (Exception e) {

		}
	}

	private void UpdateNombreParticipant() {
		query = "UPDATE `formation` SET `Nombre_participants`=" + nbpart + " WHERE Code_formation=" + GetCurrentID();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();

		} catch (Exception e) {

		}

	}

}
