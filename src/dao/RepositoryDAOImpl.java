package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.BusinessRule;
import domain.TargetDatabase;

public class RepositoryDAOImpl implements RepositoryDAO {
	private Connection connection;
	private final String url = "jdbc:oracle:thin:@ondora01.hu.nl:8521:cursus01";
	private final String username = "tho6_2014_2c_team3";
	private final String password = "tho6_2014_2c_team3";
	private ArrayList<BusinessRule> allBusinessRules = new ArrayList<BusinessRule>();

	public RepositoryDAOImpl() throws SQLException {
		connectToRepository();
	}

	public ArrayList<BusinessRule> getAllUngeneratedBusinessRules() throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt
				.executeQuery("SELECT BUSINESSRULE.BR_ID,BUSINESSRULE.ERROR_MESSAGE,OPERATOR_TYPE.NAME,BUSINESSRULE.OPERATOR_TYPE_OT_ID,BUSINESSRULE.NAME_CODE,BUSINESSRULE.TABLE_TA,BUSINESSRULE.COLUMN_TA,DATABASE_TYPE.TRIGGER_TEMPLATE"
						+ ",DB_USERNAME,DB_PASSWORD,HOST,PORT,SSID,TYPE, BUSINESSRULE.TARGETAPPLICATION_TA_ID, BUSINESSRULE.BUSINESSRULETYPE_BRT_ID"
						+ " FROM BUSINESSRULE"
						+ " INNER JOIN OPERATOR_TYPE"
						+ " ON BUSINESSRULE.OPERATOR_TYPE_OT_ID = OPERATOR_TYPE.OT_ID"
						+ " INNER JOIN TARGETAPPLICATION"
						+ " ON TARGETAPPLICATION.TA_ID=BUSINESSRULE.TARGETAPPLICATION_TA_ID"
						+ " INNER JOIN DATABASE_TYPE"
						+ " ON DATABASE_TYPE.DT_ID=TARGETAPPLICATION.DATABASE_TYPE_DT_ID"
						+ " INNER JOIN BUSINESSRULETYPE"
						+ " ON BUSINESSRULE.BUSINESSRULETYPE_BRT_ID=BUSINESSRULETYPE.BRT_ID"
						+ " WHERE STATUS = 0 OR STATUS = 2");

		while (resultSet.next()) {
			int ruleId = resultSet.getInt("BR_ID");
			String nameCode = resultSet.getString("NAME_CODE");
			String colTa = resultSet.getString("COLUMN_TA");
			String tabTa = resultSet.getString("TABLE_TA");
			String errorMessage = resultSet.getString("ERROR_MESSAGE");
			int operator = resultSet.getInt("OPERATOR_TYPE_OT_ID");
			int targetAppId = resultSet.getInt("TARGETAPPLICATION_TA_ID");
			int ruleTypeId = resultSet.getInt("BUSINESSRULETYPE_BRT_ID");
			String template = getTemplate(ruleTypeId);

			BusinessRule b = new BusinessRule(ruleId, ruleTypeId, nameCode,
					operator, tabTa, colTa, errorMessage, template, targetAppId);
			allBusinessRules.add(b);
		}
		return allBusinessRules;
	}
	
	public String getTemplate(int brt) throws SQLException{
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT BUSINESSRULETYPE.TEMPLATE FROM BUSINESSRULETYPE WHERE BRT_ID = " + brt);
		String template = "";
		while(resultSet.next()){
			template = resultSet.getString("TEMPLATE");
		}
		return template;
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
			// TODO throw no connection exception
		}
	}
}