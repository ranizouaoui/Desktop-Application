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

import Classes.Trainer;
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

public class Page1simple implements Initializable {
	@FXML
	private TableView<Trainer> studentsTable;
	@FXML
	private TableColumn<Trainer, String> idCol;
	@FXML
	private TableColumn<Trainer, String> nameCol;
	@FXML
	private TableColumn<Trainer, String> birthCol;
	@FXML
	private TableColumn<Trainer, String> prenomCol;
	@FXML
	private TableColumn<Trainer, String> profileCol;
	@FXML
	private TableColumn<Trainer, String> numbercol;
	@FXML
	private TextField tx_recherche;
	String query = null;
	String query1 = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Trainer trainer = null;
	int index = -1;
	ObservableList<Trainer> StudentList = FXCollections.observableArrayList();

	@FXML
	private Label savedlabel;
	@FXML
	private Button exitbutton;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		loadDate();
		search_trainer();
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

			query = "SELECT * FROM `formateur`";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				StudentList.add(new Trainer(resultSet.getInt("Code_formateur"), resultSet.getString("Nom"),
						resultSet.getString("Prenom"), resultSet.getString("Organisme"), resultSet.getString("Email"),
						resultSet.getString("N_telephone")));

				studentsTable.setItems(StudentList);

			}

		} catch (SQLException ex) {
			Logger.getLogger(Page1simple.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@FXML
	private void getAddView(MouseEvent event) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/Views/Addtrainer.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.getIcons().add(new Image("/Classes/icons/icons8_bulleted_list_40px.png"));
			stage.initStyle(StageStyle.UTILITY);
			stage.show();
		} catch (IOException ex) {
			Logger.getLogger(Page1simple.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void loadDate() {

		connection = DataBase.connecterBase();
		refreshTable();

		idCol.setCellValueFactory(new PropertyValueFactory<>("Code_formateur"));
		nameCol.setCellValueFactory(new PropertyValueFactory<>("Nom"));
		prenomCol.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
		birthCol.setCellValueFactory(new PropertyValueFactory<>("Organisme"));
		profileCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
		numbercol.setCellValueFactory(new PropertyValueFactory<>("Ntelephone"));
		// add cell of button edit

		studentsTable.setItems(StudentList);

	}

	@FXML
	private void DeleteFromTable() {
		try {
			trainer = studentsTable.getSelectionModel().getSelectedItem();
			query = "DELETE FROM `formateur` WHERE Code_formateur  =" + trainer.getCode_formateur();
			connection = DataBase.connecterBase();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
			refreshTable();

		} catch (SQLException ex) {
			Logger.getLogger(Page1simple.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@FXML
	private void getEditView(MouseEvent event) {
		trainer = studentsTable.getSelectionModel().getSelectedItem();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Views/UpdateTrainer.fxml"));
		try {
			loader.load();
		} catch (IOException ex) {
			Logger.getLogger(Page1simple.class.getName()).log(Level.SEVERE, null, ex);
		}

		UpdateTrainer updateTrainer = loader.getController();
		updateTrainer.setUpdate(true);
		updateTrainer.setTextField(trainer.getCode_formateur(), trainer.getNom(), trainer.getPrenom(),
				trainer.getEmail(), trainer.getNtelephone());
		Parent parent = loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(parent));
		stage.initStyle(StageStyle.UTILITY);
		stage.show();

	}

	@FXML
	void search_trainer() {
		loadDate();
		FilteredList<Trainer> filteredData = new FilteredList<>(StudentList, b -> true);
		tx_recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(trainer -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (trainer.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (trainer.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (trainer.getNtelephone().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (trainer.getEmail().indexOf(lowerCaseFilter) != -1) {
					return true;
				}

				else
					return false;
			});
		});
		SortedList<Trainer> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(studentsTable.comparatorProperty());
		studentsTable.setItems(sortedData);
	}
}