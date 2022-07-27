package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import helpers.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SimpleUserDash implements Initializable {
	@FXML
	private Button exitbutton;
	@FXML
	private BorderPane borderPane;
	@FXML
	private Label label1;
	@FXML
	private Label label2;
	@FXML
	private Label label3;
	@FXML
	private List<Button> menus;
	@FXML
	private PieChart pieChart;
	public int x, y, z;

	public void cancelButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) exitbutton.getScene().getWindow();
		stage.close();

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		loadValues();
		loadChart();

	}

	private void loadChart() {
		PieChart.Data slice1 = new PieChart.Data("Trainings", x);
		PieChart.Data slice2 = new PieChart.Data("Trainers", y);
		PieChart.Data slice3 = new PieChart.Data("Participants", z);

		pieChart.getData().add(slice1);
		pieChart.getData().add(slice2);
		pieChart.getData().add(slice3);

	}

	private void loadValues() {

		Connection connectDB = DataBase.connecterBase();
		String query = "SELECT COUNT(*) FROM formation";
		String query1 = "SELECT COUNT(*) FROM formateur";
		String query2 = "SELECT COUNT(*) FROM participant";
		try {
			Statement statement = connectDB.createStatement();
			Statement statement1 = connectDB.createStatement();
			Statement statement2 = connectDB.createStatement();
			ResultSet queryResult = statement.executeQuery(query);
			ResultSet queryResult1 = statement1.executeQuery(query1);
			ResultSet queryResult2 = statement2.executeQuery(query2);

			if (queryResult.next()) {
				label1.setText(queryResult.getString(1));
				x = queryResult.getInt(1);

			}
			if (queryResult1.next()) {
				label2.setText(queryResult1.getString(1));
				y = queryResult1.getInt(1);
			}
			if (queryResult2.next()) {

				label3.setText(queryResult2.getString(1));
				z = queryResult2.getInt(1);
			}

		} catch (Exception e) {
			e.fillInStackTrace();
		}

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
		loadFXML("Page3simple");
	}

	@FXML
	private void loadPage02View(ActionEvent e) {
		loadFXML("Page2simple");
	}

	@FXML
	private void loadPage03View(ActionEvent e) {
		loadFXML("PageTraining");
	}

	@FXML
	private void loadPage05View(ActionEvent e) {
		loadFXML("PageInscription");
	}

	@FXML
	private void loadPage04View(ActionEvent e) {
		loadFXML("Charts");
	}

}