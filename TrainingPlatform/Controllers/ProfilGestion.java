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

import Classes.Profil;
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

public class ProfilGestion implements Initializable {
	@FXML
	private TableColumn<Profil, Integer> idCol;
	@FXML
	private TableColumn<Profil, String> ProfilCol;

	@FXML
	private TableView<Profil> profilsTable;

	String query = null;
	String query1 = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Profil profil = null;
	int index = -1;
	ObservableList<Profil> ProfilList = FXCollections.observableArrayList();

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
	private void getAddProfil(MouseEvent event) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/Views/AddProfil.fxml"));
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
			ProfilList.clear();

			query = "SELECT * FROM `profil`";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ProfilList.add(new Profil(resultSet.getInt("Code_profil"), resultSet.getString("Libelle")));

				profilsTable.setItems(ProfilList);

			}

		} catch (SQLException ex) {
			Logger.getLogger(Page2.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void loadDate() {

		connection = DataBase.connecterBase();
		refreshTable();

		idCol.setCellValueFactory(new PropertyValueFactory<>("Code_profil"));
		ProfilCol.setCellValueFactory(new PropertyValueFactory<>("Libelle"));

		profilsTable.setItems(ProfilList);

	}

	@FXML
	private void DeleteFromTable(ActionEvent event) {
		try {
			profil = profilsTable.getSelectionModel().getSelectedItem();
			query = "DELETE FROM `profil` WHERE Code_profil  =" + profil.getCode_profil();
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
		profil = profilsTable.getSelectionModel().getSelectedItem();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Views/UpdateProfil.fxml"));
		try {
			loader.load();
		} catch (IOException ex) {
			Logger.getLogger(Page2simple.class.getName()).log(Level.SEVERE, null, ex);
		}

		UpdateProfil updateProfil = loader.getController();
		updateProfil.setUpdate(true);
		updateProfil.setTextField(profil.getCode_profil(), profil.getLibelle());
		Parent parent = loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(parent));
		stage.initStyle(StageStyle.UTILITY);
		stage.show();

	}

}
