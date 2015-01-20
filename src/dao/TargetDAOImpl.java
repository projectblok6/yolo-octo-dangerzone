package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TargetDAOImpl implements TargetDAO {
	private Connection connection;
	public void connectToTarget(String url, String username, String password, String databaseType) {
		Connection connection = null;
		try {
			System.out.println(url);
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		if (connection != null) {
			this.connection = connection;
		} else {
			// throw no connection exception
		}
	}
	@Override
	public void executeGeneratedRule(String code) {
		try {
			System.out.println("Test");
			Statement stmt = connection.createStatement();
			stmt.execute(code);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
