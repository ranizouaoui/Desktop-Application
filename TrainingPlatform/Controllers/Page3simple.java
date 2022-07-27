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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Page3simple implements Initializable {
	@FXML
	private TableView<Training> studentsTable;
	@FXML
	private TableColumn<Training, String> idCol;
	@FXML
	private TableColumn<Training, String> titleCol;
	@FXML
	private TableColumn<Training, String> domainCol;
	@FXML
	private TableColumn<Training, String> periodeCol;
	@FXML
	private TableColumn<Training, String> anneeCol;
	@FXML
	private TableColumn<Training, String> moiscol;
	@FXML
	private TableColumn<Training, String> trainercol;
	@FXML
	private TableColumn<Training, String> participantnumcol;
	@FXML
	private TextField tx_recherche;
	String query = null;
	String query1 = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Training trainer = null;
	int index = -1;
	ObservableList<Training> StudentList = FXCollections.observableArrayList();

	@FXML
	private Label savedlabel;
	@FXML
	private Button exitbutton;
	public int x;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		loadDate();
//		search_training();
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

			query = "SELECT * FROM `formation`";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				StudentList.add(new Training(resultSet.getInt("Code_formation"), resultSet.getString("Intitulé"),
						resultSet.getString("Domaine"), resultSet.getString("Nombre_jours"),
						resultSet.getString("Annee"), resultSet.getString("mois"), resultSet.getString("Formateur"),
						resultSet.getString("Nombre_participants")));

				studentsTable.setItems(StudentList);

			}

		} catch (SQLException ex) {
			Logger.getLogger(Page3simple.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@FXML
	private void getAddView(MouseEvent event) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/Views/Addtraining.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.getIcons().add(new Image("/Classes/icons/icons8_bulleted_list_40px.png"));
			stage.initStyle(StageStyle.UTILITY);
			stage.show();
		} catch (IOException ex) {
			Logger.getLogger(Page3simple.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void loadDate() {

		connection = DataBase.connecterBase();
		refreshTable();

		idCol.setCellValueFactory(new PropertyValueFactory<>("Code_formation"));
		titleCol.setCellValueFactory(new PropertyValueFactory<>("Intitulé"));
		domainCol.setCellValueFactory(new PropertyValueFactory<>("Domaine"));
		periodeCol.setCellValueFactory(new PropertyValueFactory<>("Nombre_jours"));
		anneeCol.setCellValueFactory(new PropertyValueFactory<>("Annee"));
		moiscol.setCellValueFactory(new PropertyValueFactory<>("mois"));
		trainercol.setCellValueFactory(new PropertyValueFactory<>("Formateur"));
		participantnumcol.setCellValueFactory(new PropertyValueFactory<>("Nombre_participants"));
		// add cell of button edit
		studentsTable.setItems(StudentList);

	}

	@FXML
	private void DeleteFromTable() {
		try {
			trainer = studentsTable.getSelectionModel().getSelectedItem();
			query = "DELETE FROM `formation` WHERE Code_formation  =" + trainer.getCode_formation();
			connection = DataBase.connecterBase();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
			refreshTable();

		} catch (SQLException ex) {
			Logger.getLogger(Page3simple.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@FXML
	private void getEditView(MouseEvent event) {
		trainer = studentsTable.getSelectionModel().getSelectedItem();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Views/UpdateTraining.fxml"));
		try {
			loader.load();
		} catch (IOException ex) {
			Logger.getLogger(Page3simple.class.getName()).log(Level.SEVERE, null, ex);
		}

		UpdateTraining updateTraining = loader.getController();
		updateTraining.setUpdate(true);
		updateTraining.setTextField(trainer.getCode_formation(), trainer.getIntitulé(), trainer.getDomaine(),
				trainer.getNombre_jours(), trainer.getMois(), trainer.getAnnee(), trainer.getFormateur(),
				trainer.getNombre_participants());
		Parent parent = loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(parent));
		stage.initStyle(StageStyle.UTILITY);
		stage.show();

	}

	@FXML
	private void GetParticipantsList(MouseEvent event) {
		trainer = studentsTable.getSelectionModel().getSelectedItem();
		x = trainer.getCode_formation();
		Dao a = new Dao();
		a.setX(x);
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/Views/ParticipantList.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.getIcons().add(new Image("/Classes/icons/icons8_bulleted_list_40px.png"));
			stage.initStyle(StageStyle.UTILITY);
			stage.show();
		} catch (IOException ex) {
			Logger.getLogger(Page3simple.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@FXML
	void search_trainer() {
		loadDate();
		FilteredList<Training> filteredData = new FilteredList<>(StudentList, b -> true);
		tx_recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(trainer -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (trainer.getIntitulé().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (trainer.getDomaine().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (trainer.getFormateur().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (trainer.getNombre_participants().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (trainer.getMois().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (trainer.getAnnee().indexOf(lowerCaseFilter) != -1) {
					return true;
				}

				else
					return false;
			});
		});
		SortedList<Training> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(studentsTable.comparatorProperty());
		studentsTable.setItems(sortedData);
	}
}