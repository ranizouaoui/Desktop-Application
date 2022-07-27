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

import Classes.Domaine;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DomaineGestion implements Initializable {
	@FXML
	private TableColumn<Domaine, Integer> DomaineCol;

	@FXML
	private TableView<Domaine> domainesTable;

	@FXML
	private TableColumn<Domaine, String> idCol;
	String query = null;
	String query1 = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Domaine domaine = null;
	int index = -1;
	ObservableList<Domaine> DomaineList = FXCollections.observableArrayList();

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
	private void getAddDomaine(MouseEvent event) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/Views/AddDomaine.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.UTILITY);
			stage.show();
		} catch (IOException ex) {
			Logger.getLogger(Page2.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@FXML
	void refreshTable() {
		try {
			DomaineList.clear();

			query = "SELECT * FROM `domaine`";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				DomaineList.add(new Domaine(resultSet.getInt("Code_domaine"), resultSet.getString("Libelle")));

				domainesTable.setItems(DomaineList);

			}

		} catch (SQLException ex) {
			Logger.getLogger(Page2.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void loadDate() {

		connection = DataBase.connecterBase();
		refreshTable();

		idCol.setCellValueFactory(new PropertyValueFactory<>("Code_domaine"));
		DomaineCol.setCellValueFactory(new PropertyValueFactory<>("Libelle"));

		domainesTable.setItems(DomaineList);

	}

	@FXML
	private void DeleteFromTable(ActionEvent event) {
		try {
			domaine = domainesTable.getSelectionModel().getSelectedItem();
			query = "DELETE FROM `domaine` WHERE Code_domaine  =" + domaine.getCode_domaine();
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
		domaine = domainesTable.getSelectionModel().getSelectedItem();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Views/UpdateDomaine.fxml"));
		try {
			loader.load();
		} catch (IOException ex) {
			Logger.getLogger(Page2simple.class.getName()).log(Level.SEVERE, null, ex);
		}

		UpdateDomaine updateDomaine = loader.getController();
		updateDomaine.setUpdate(true);
		updateDomaine.setTextField(domaine.getCode_domaine(), domaine.getLibelle());
		Parent parent = loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(parent));
		stage.initStyle(StageStyle.UTILITY);
		stage.show();

	}
}
