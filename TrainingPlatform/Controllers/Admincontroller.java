package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Admincontroller implements Initializable {
	@FXML
	private Button exitbutton;
	@FXML
	private BorderPane borderPane;

	@FXML
	private List<Button> menus;

	public void cancelButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) exitbutton.getScene().getWindow();
		stage.close();

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	@FXML
	private void loadFXML(String fileName) {
		Parent parent;
		try {
			parent = FXMLLoader.load(getClass().getResource("/views/" + fileName + ".fxml"));
			borderPane.setCenter(parent);

		} catch (IOException ex) {
			Logger.getLogger(Admincontroller.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@FXML
	private void clear() {
		borderPane.setCenter(null);
	}

	@FXML
	private void loadPage01View(ActionEvent e) {
		loadFXML("Profil");
		// changeButtonBackground(e);
	}

	@FXML
	private void loadPage02View(ActionEvent e) {
		loadFXML("page2");
		// changeButtonBackground(e);
	}
	
	@FXML
	private void loadPage03View(ActionEvent e) {
		loadFXML("Domaine");
		// changeButtonBackground(e);
	}
	@FXML
	private void loadPage04View(ActionEvent e) {
		loadFXML("Organisme");
		// changeButtonBackground(e);
	}
	@FXML
	private void loadPage05View(ActionEvent e) {
		loadFXML("Charts");
		// changeButtonBackground(e);
	}

}
