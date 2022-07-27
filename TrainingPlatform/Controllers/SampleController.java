package Controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import Classes.CryptoUtils;
import helpers.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SampleController {

	@FXML
	private Button cancelfield;
	@FXML
	private Label loginmessege;
	@FXML
	private TextField usernamefield;
	@FXML
	private PasswordField password;

	public void loginButtonOnAction(ActionEvent e) {
		if (usernamefield.getText().isBlank() == false && password.getText().isBlank() == false) {
			// loginmessege.setText(" You Try to login :) ");
			validateLogin();
		} else {
			loginmessege.setText("Please enter detaills  :) ");
		}
	}

	public void cancelButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) cancelfield.getScene().getWindow();
		stage.close();

	}

	double x = 0;
	double y = 0;

	public void validateLogin() {

		Connection connectDB = DataBase.connecterBase();
		String veriflogin = "SELECT COUNT(1) FROM `user` WHERE user_name = '" + usernamefield.getText()
				+ "' AND user_password = '" + CryptoUtils.Encrypt(password.getText()) + "'";
		String verifrole = "SELECT role FROM `user` WHERE user_name = '" + usernamefield.getText()
				+ "' AND user_password = '" + CryptoUtils.Encrypt(password.getText()) + "'";

		try {
			Statement statement = connectDB.createStatement();
			ResultSet queryResult = statement.executeQuery(veriflogin);
			while (queryResult.next()) {
				if (queryResult.getInt(1) == 1) {

					ResultSet queryResult1 = statement.executeQuery(verifrole);
					if (queryResult1.next()) {

						if (queryResult1.getString(1).equals("ADMIN")) {

							loadAdminpanel();

						} else {
							loadSimplepanel();
						}
					}

				} else {
					loginmessege.setText("Invalid ! Try again ");
				}
			}
		} catch (Exception e) {
			e.fillInStackTrace();
		}
	}

	@FXML
	private void loadAdminpanel() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/views/admindash.fxml"));
			Stage primaryStage = new Stage();
			Scene scene = new Scene(root);
			// primaryStage.initStyle(StageStyle.UNDECORATED);
			scene.getStylesheets().add(getClass().getResource("/Classes/application.css").toExternalForm());
			primaryStage.setTitle("Admin " + usernamefield.getText().toUpperCase());
			root.setOnMousePressed(evt -> {
				x = evt.getSceneX();
				y = evt.getSceneY();
			});
			root.setOnMouseDragged(evt -> {
				primaryStage.setX(evt.getSceneX() - x);
				primaryStage.setY(evt.getSceneY() - y);
			});
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("/Classes/icons/icons8_administrator_male_48px.png"));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void loadSimplepanel() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/views/simpleUserDash.fxml"));
			Stage primaryStage = new Stage();
			Scene scene = new Scene(root);
			// primaryStage.initStyle(StageStyle.UNDECORATED);
			scene.getStylesheets().add(getClass().getResource("/Classes/application.css").toExternalForm());
			primaryStage.setTitle("Simple user " + usernamefield.getText());
			root.setOnMousePressed(evt -> {
				x = evt.getSceneX();
				y = evt.getSceneY();
			});
			root.setOnMouseDragged(evt -> {
				primaryStage.setX(evt.getSceneX() - x);
				primaryStage.setY(evt.getSceneY() - y);
			});
			primaryStage.getIcons().add(new Image("/Classes/icons/icons8_administrator_male_48px.png"));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
