package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
	public static Connection connecterBase() {
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/application";
		String user = "root";
		String password = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			System.out.println("connexion établie.");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("erreur de connexion !");
		}
		return con;
	}

}