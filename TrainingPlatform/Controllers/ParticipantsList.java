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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ParticipantsList implements Initializable {
	@FXML
	private TableView<Participant> studentsTable;
	@FXML
	private TableColumn<Participant, String> idCol;
	@FXML
	private TableColumn<Participant, String> nameCol;
	@FXML
	private TableColumn<Participant, String> birthCol;
	@FXML
	private TableColumn<Participant, String> prenomCol;
	@FXML
	private TableColumn<Participant, String> profileCol;
	@FXML
	private TextField tx_recherche;
	@FXML
	private ComboBox<String> combobox;
	String query = null;
	String query1 = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null, preparedStatement1 = null;
	ResultSet resultSet = null, resultSet1 = null;
	Participant participant = null;
	int index = -1;
	ObservableList<Participant> StudentList = FXCollections.observableArrayList();
	ObservableList<Participant> Datalist;
	@FXML
	private Label savedlabel;
	@FXML
	private Button exitbutton;
	private int ss;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
//		setIdTraining();
		loadDate();
	}

	@FXML
	private void close(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	public int setIdTraining() {
		Dao a = new Dao();
		// System.out.print(a.getX());
		return a.getX();

	}

	private void loadDate() {

		connection = DataBase.connecterBase();
		SelectIdparticipant(setIdTraining());
		idCol.setCellValueFactory(new PropertyValueFactory<>("Matricule_participant"));
		nameCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		birthCol.setCellValueFactory(new PropertyValueFactory<>("birth"));
		profileCol.setCellValueFactory(new PropertyValueFactory<>("profile"));
		// add cell of button edit

		studentsTable.setItems(StudentList);

	}

	void SelectIdparticipant(int id_formation) {
		try {
			StudentList.clear();

			query = "SELECT  `id_participant` FROM `per` WHERE id_formation=" + id_formation;
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ss = resultSet.getInt(1);
//				System.out.print(ss);
				AddtoTable(ss);

			}

		} catch (SQLException ex) {
			Logger.getLogger(ParticipantsList.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	void AddtoTable(int id_participant) {
		try {

			query1 = "SELECT * FROM `participant` WHERE Matricule_participant =" + id_participant;
			preparedStatement1 = connection.prepareStatement(query1);
			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				StudentList.add(new Participant(resultSet1.getInt("Matricule_participant"), resultSet1.getString("nom"),
						resultSet1.getString("prenom"), resultSet1.getString("birth"),
						resultSet1.getInt("id_profile")));
				System.out.print("fuu");
				studentsTable.setItems(StudentList);
			}

		} catch (SQLException ex) {
			Logger.getLogger(ParticipantsList.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}