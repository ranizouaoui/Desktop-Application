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

import Classes.User;
import helpers.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Page2 implements Initializable {
	// private static final String NULL = null;
	@FXML
	private TableView<User> usersTable;
	@FXML
	private TableColumn<User, Integer> idCol;
	@FXML
	private TableColumn<User, String> loginCol;
	@FXML
	private TableColumn<User, String> passwordCol;
	@FXML
	private TableColumn<User, String> roleCol;
	@FXML
	private TableColumn<User, String> editCol;

	@FXML
	private TextField loginfiled;

	@FXML
	private PasswordField passwordfield;

	@FXML
	private TextField rolefiled;
	@FXML
	private TextField idfiled;
	@FXML
	private TextField tx_recherche;

	String query = null;
	String query1 = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	User user = null;
	int index = -1;
	ObservableList<User> UserList = FXCollections.observableArrayList();
	ObservableList<User> Datalist;
	@FXML
	private Label savedlabel;
	@FXML
	private Button exitbutton;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO

		loadDate();
		search_user();

	}

	@FXML
	private void close(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	void refreshTable() {
		try {
			UserList.clear();

			query = "SELECT * FROM `user`";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				UserList.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4)));

				usersTable.setItems(UserList);

			}

		} catch (SQLException ex) {
			Logger.getLogger(Page2.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@FXML
	private void getAddView(MouseEvent event) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/Views/AddUser.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.UTILITY);
			stage.show();
		} catch (IOException ex) {
			Logger.getLogger(Page2.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void loadDate() {

		connection = DataBase.connecterBase();
		refreshTable();

		idCol.setCellValueFactory(new PropertyValueFactory<>("Code_utilisateur"));
		loginCol.setCellValueFactory(new PropertyValueFactory<>("user_name"));
		passwordCol.setCellValueFactory(new PropertyValueFactory<>("user_password"));
		roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
		// add cell of button edit

		usersTable.setItems(UserList);

	}

	@FXML
	private void DeleteFromTable(ActionEvent event) {
		try {
			user = usersTable.getSelectionModel().getSelectedItem();
			query = "DELETE FROM `user` WHERE Code_utilisateur  =" + user.getCode_utilisateur();
			connection = DataBase.connecterBase();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
			// refreshTable();

		} catch (SQLException ex) {
			Logger.getLogger(Page2.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@FXML
	void getSelected(MouseEvent event) {
		index = usersTable.getSelectionModel().getSelectedIndex();
		if (index <= -1) {

			return;
		}
		idfiled.setText(idCol.getCellData(index).toString());
		loginfiled.setText(loginCol.getCellData(index).toString());
		passwordfield.setText(passwordCol.getCellData(index).toString());
		rolefiled.setText(roleCol.getCellData(index).toString());

	}

	@FXML
	private void getEditView(MouseEvent event) {
		user = usersTable.getSelectionModel().getSelectedItem();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Views/UpdateUser.fxml"));
		try {
			loader.load();
		} catch (IOException ex) {
			Logger.getLogger(Page2simple.class.getName()).log(Level.SEVERE, null, ex);
		}

		UpdateUser updateUser = loader.getController();
		updateUser.setUpdate(true);
		updateUser.setTextField(user.getCode_utilisateur(), user.getUser_name(), user.getUser_password(),
				user.getRole());
		Parent parent = loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(parent));
		stage.initStyle(StageStyle.UTILITY);
		stage.show();

	}

	@FXML
	void search_user() {
		loadDate();
		FilteredList<User> filteredData = new FilteredList<>(UserList, b -> true);
		tx_recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(user -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (user.getUser_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (user.getUser_password().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(user.getRole()).indexOf(lowerCaseFilter) != -1)
					return true;
				else
					return false;
			});
		});
		SortedList<User> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(usersTable.comparatorProperty());
		usersTable.setItems(sortedData);
	}

}
