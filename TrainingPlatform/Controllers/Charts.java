package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import helpers.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class Charts implements Initializable {
	@FXML
	private LineChart<?, ?> lineChart;
	private String name;
	int name1;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		chartLine();
	}

	private void chartLine() {

		XYChart.Series series = new XYChart.Series();
		series.setName("Sales");
		try

		{
			Connection connectDB = DataBase.connecterBase();
			String query = "SELECT `nom`, `Matricule_participant` FROM `participant`";
			Statement statement = connectDB.createStatement();
			ResultSet queryResult = statement.executeQuery(query);

			while (queryResult.next()) {
				name = queryResult.getString("nom");
				name1 = queryResult.getInt("Matricule_participant");
				series.getData().add(new XYChart.Data(name, name1));
			}

		} catch (Exception e) {
			e.fillInStackTrace();
		}
		lineChart.getData().add(series);
	}
}
