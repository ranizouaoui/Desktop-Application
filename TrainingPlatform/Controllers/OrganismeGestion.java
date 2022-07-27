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

import Classes.Organisme;
import helpers.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OrganismeGestion implements Initializable {
	@FXML
	private TableColumn<Organisme, Integer> idCol;
	@FXML
	private TableColumn<Organisme, String> OrganismeCol;

	@FXML
	private TableView<Organisme> organismesTable;

	String query = null;
	String query1 = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Organisme organisme = null;
	int index = -1;
	ObservableList<Organisme> OrganismeList = FXCollections.observableArrayList();

	@FXML
	private Label savedlabel;
	@FXML
	private Button exitbutton;

	@FXML

	private void close(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	private void getAddOrganisme(MouseEvent event) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/Views/AddOrganisme.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.getIcons().add(new Image(
					"C:\\Users\\Anssem\\Downloads\\TrainingPlatform (1)\\TrainingPlatform\\Classes\\icons/icons8_bulleted_list_40px.png"));
			stage.initStyle(StageStyle.UTILITY);
			stage.show();
		} catch (IOException ex) {
			Logger.getLogger(Page2.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@FXML
	void refreshTable() {
		try {
			OrganismeList.clear();

			query = "SELECT * FROM `organisme`";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				OrganismeList.add(new Organisme(resultSet.getInt(1), resultSet.getString(2)));
				organismesTable.setItems(OrganismeList);
			}

		} catch (SQLException ex) {
			Logger.getLogger(Page2.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void loadDate() {

		connection = DataBase.connecterBase();
		refreshTable();

		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		OrganismeCol.setCellValueFactory(new PropertyValueFactory<>("Libelle"));

		organismesTable.setItems(OrganismeList);

	}

	@FXML
	private void DeleteFromTable(ActionEvent event) {
		try {
			organisme = organismesTable.getSelectionModel().getSelectedItem();
			query = "DELETE FROM `organisme` WHERE id  =" + organisme.getId();
			connection = DataBase.connecterBase();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
			refreshTable();

		} catch (SQLException ex) {
			Logger.getLogger(Page2simple.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		loadDate();
	}

	@FXML
	private void getEditView(MouseEvent event) {
		organisme = organismesTable.getSelectionModel().getSelectedItem();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Views/UpdateOrganisme.fxml"));
		try {
			loader.load();
		} catch (IOException ex) {
			Logger.getLogger(Page2simple.class.getName()).log(Level.SEVERE, null, ex);
		}

		UpdateOrganisme updateOrganisme = loader.getController();
		updateOrganisme.setUpdate(true);
		updateOrganisme.setTextField(organisme.getId(), organisme.getLibelle());
		Parent parent = loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(parent));
		stage.initStyle(StageStyle.UTILITY);
		stage.show();

	}
}
