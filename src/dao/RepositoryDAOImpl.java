package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Column;
import domain.Component;
import domain.Operator;
import domain.TargetDatabase;
import domain.Value;
import domain.businessrule.BusinessRule;
import domain.businessrule.RangeRule;

public class RepositoryDAOImpl implements RepositoryDAO {
	private Connection connection;
	private final String url = "jdbc:oracle:thin:@ondora01.hu.nl:8521:cursus01";
	private final String username = "tho6_2014_2c_team3";
	private final String password = "tho6_2014_2c_team3";

	public RepositoryDAOImpl() throws SQLException {
		connectToRepository();
	}
	
	

	private void connectToRepository() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		if (connection != null) {
			this.connection = connection;
		} else {
			//TODO throw no connection exception
		}
	}
}
