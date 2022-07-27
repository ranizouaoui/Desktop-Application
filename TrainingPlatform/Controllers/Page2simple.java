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

import Classes.Participant;
import helpers.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Page2simple implements Initializable {
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
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Participant participant = null;
	int index = -1;
	ObservableList<Participant> StudentList = FXCollections.observableArrayList();
	ObservableList<Participant> Datalist;

	@FXML
	private Label savedlabel;
	@FXML
	private Button exitbutton;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		loadDate();
		search_participant();
	}

	@FXML
	private void close(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	void refreshTable() {
		try {
			StudentList.clear();

			query = "SELECT * FROM `participant`";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				StudentList.add(new Participant(resultSet.getInt("Matricule_participant"), resultSet.getString("nom"),
						resultSet.getString("prenom"), resultSet.getString("birth"), resultSet.getInt("id_profile")));

				studentsTable.setItems(StudentList);

			}

		} catch (SQLException ex) {
			Logger.getLogger(Page2simple.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@FXML
	private void getAddView(MouseEvent event) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/Views/AddStudent.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.getIcons().add(new Image("/Classes/icons/icons8_bulleted_list_40px.png"));
			stage.initStyle(StageStyle.UTILITY);
			stage.show();
		} catch (IOException ex) {
			Logger.getLogger(Page2simple.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void loadDate() {

		connection = DataBase.connecterBase();
		refreshTable();

		idCol.setCellValueFactory(new PropertyValueFactory<>("Matricule_participant"));
		nameCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		birthCol.setCellValueFactory(new PropertyValueFactory<>("birth"));
		profileCol.setCellValueFactory(new PropertyValueFactory<>("profile"));
		// add cell of button edit

		studentsTable.setItems(StudentList);

	}

	@FXML
	private void DeleteFromTable(MouseEvent event) {
		try {
			participant = studentsTable.getSelectionModel().getSelectedItem();
			query = "DELETE FROM `participant` WHERE Matricule_participant  =" + participant.getMatricule_participant();
			connection = DataBase.connecterBase();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
			refreshTable();

		} catch (SQLException ex) {
			Logger.getLogger(Page2simple.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@FXML
	private void getEditView(MouseEvent event) {
		participant = studentsTable.getSelectionModel().getSelectedItem();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Views/UpdateParticipant.fxml"));
		try {
			loader.load();
		} catch (IOException ex) {
			Logger.getLogger(Page2simple.class.getName()).log(Level.SEVERE, null, ex);
		}

		UpdateParticipant updateParticipant = loader.getController();
		updateParticipant.setUpdate(true);
		updateParticipant.setTextField(participant.getMatricule_participant(), participant.getNom(),
				participant.getPrenom());
		Parent parent = loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(parent));
		stage.initStyle(StageStyle.UTILITY);
		stage.show();

	}

	@FXML
	void search_participant() {
		loadDate();
		FilteredList<Participant> filteredData = new FilteredList<>(StudentList, b -> true);
		tx_recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(participant -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (participant.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (participant.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (participant.getBirth().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else
					return false;
			});
		});
		SortedList<Participant> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(studentsTable.comparatorProperty());
		studentsTable.setItems(sortedData);
	}

}