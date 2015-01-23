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
				.executeQuery("SELECT BUSINESSRULE.BR_ID,BUSINESSRULE.ERROR_MESSAGE,BUSINESSRULE.OPERATOR_TYPE_OT_ID,BUSINESSRULE.NAME_CODE,BUSINESSRULE.TABLE_TA,BUSINESSRULE.COLUMN_TA,DATABASE_TYPE.TRIGGER_TEMPLATE"
						+ ",DB_USERNAME,DB_PASSWORD,HOST,PORT,SSID,TYPE, BUSINESSRULE.TARGETAPPLICATION_TA_ID, BUSINESSRULE.BUSINESSRULETYPE_BRT_ID"
						+ " FROM BUSINESSRULE"
						+ " INNER JOIN OPERATOR_TYPE"
						+ " ON BUSINESSRULE.OPERATOR_TYPE_OT_ID = OPERATOR_TYPE.OT_ID"
						+ " INNER JOIN TARGETAPPLICATION"
						+ " ON TARGETAPPLICATION.TA_ID=BUSINESSRULE.TARGETAPPLICATION_TA_ID"
						+ " INNER JOIN DATABASE_TYPE"
						+ " ON DATABASE_TYPE.DT_ID = TARGETAPPLICATION.DATABASE_TYPE_DT_ID"
						+ " INNER JOIN BUSINESSRULETYPE"
						+ " ON BUSINESSRULE.BUSINESSRULETYPE_BRT_ID = BUSINESSRULETYPE.BRT_ID"
						+ " WHERE STATUS = 0 OR STATUS = 2");

		while (resultSet.next()) {
			int ruleId = resultSet.getInt("BR_ID");
			String nameCode = resultSet.getString("NAME_CODE");
			String colTa = resultSet.getString("COLUMN_TA");
			String tabTa = resultSet.getString("TABLE_TA");
			String errorMessage = resultSet.getString("ERROR_MESSAGE");
			int operatorId = resultSet.getInt("OPERATOR_TYPE_OT_ID");
			ResultSet operatorSet = stmt.executeQuery("SELECT NAME FROM OPERATOR_TYPE WHERE OT_ID = " + operatorId);
			
			String operatorName = "";
			while(operatorSet.next()){
				operatorName = operatorSet.getString("NAME");
			}
			
			
			int targetAppId = resultSet.getInt("TARGETAPPLICATION_TA_ID");
			int ruleTypeId = resultSet.getInt("BUSINESSRULETYPE_BRT_ID");
			String template = getTemplate(ruleTypeId);
			ArrayList<String> triggers = getTriggers(ruleId);

			BusinessRule b = new BusinessRule(ruleId, ruleTypeId, nameCode,
					operatorName, tabTa, colTa, errorMessage, template, targetAppId, triggers);
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
	
	public ArrayList<String> getTriggers(int br) throws SQLException{
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT TRIGGER_TYPE FROM TRIGGER_EVENT WHERE BUSINESSRULE_BR_ID = " + br);
		ArrayList<String> triggers = new ArrayList<String>();
		while(resultSet.next()){
			String t = resultSet.getString("TRIGGER_TYPE");
			triggers.add(t);
		}
		return triggers;
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