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

	public RepositoryDAOImpl() throws SQLException {
		connectToRepository();
	}

	public ArrayList<BusinessRule> getAllUngeneratedBusinessRules() throws SQLException {
		ArrayList<BusinessRule> allBusinessRules = new ArrayList<BusinessRule>();
		Statement stmt = connection.createStatement();		
		ResultSet resultSet = stmt
				.executeQuery("SELECT BUSINESSRULE.BR_ID,BUSINESSRULE.ERROR_MESSAGE,BUSINESSRULE.OPERATOR_TYPE_OT_ID,BUSINESSRULE.NAME_CODE,BUSINESSRULE.TABLE_TA,BUSINESSRULE.COLUMN_TA,DATABASE_TYPE.TRIGGER_TEMPLATE"
						+ ",DB_USERNAME,DB_PASSWORD,HOST,PORT,SSID,TYPE,TARGETAPPLICATION_TA_ID, BUSINESSRULE.BUSINESSRULETYPE_BRT_ID"
						+ " FROM BUSINESSRULE"
						+ " INNER JOIN OPERATOR_TYPE"
						+ " ON BUSINESSRULE.OPERATOR_TYPE_OT_ID = OPERATOR_TYPE.OT_ID"
						+ " INNER JOIN TARGETAPPLICATION"
						+ " ON TARGETAPPLICATION.TA_ID=BUSINESSRULE.TARGETAPPLICATION_TA_ID"
						+ " INNER JOIN DATABASE_TYPE"
						+ " ON DATABASE_TYPE.DT_ID = TARGETAPPLICATION.DATABASE_TYPE_DT_ID"
						+ " INNER JOIN BUSINESSRULETYPE"
						+ " ON BUSINESSRULE.BUSINESSRULETYPE_BRT_ID = BUSINESSRULETYPE.BRT_ID"
						+ " WHERE STATUS = 0");
	
		
		while(resultSet.next()){
			int ruleId = resultSet.getInt("BR_ID");
			String nameCode = resultSet.getString("NAME_CODE");
			String colTa = resultSet.getString("COLUMN_TA");
			String tabTa = resultSet.getString("TABLE_TA");
			String errorMessage = resultSet.getString("ERROR_MESSAGE");
			int operatorId = resultSet.getInt("OPERATOR_TYPE_OT_ID");
			int targetAppId = resultSet.getInt("TARGETAPPLICATION_TA_ID");
			int ruleTypeId = resultSet.getInt("BUSINESSRULETYPE_BRT_ID");
			String dbUser = resultSet.getString("DB_USERNAME");
			String dbPass = resultSet.getString("DB_PASSWORD");
			String host = resultSet.getString("HOST");
			String port = resultSet.getString("PORT");
			String ssid = resultSet.getString("SSID");
			String type = resultSet.getString("TYPE");
			String operatorName = getOperator(operatorId);
			TargetDatabase tdb = new TargetDatabase(dbUser, dbPass, host, port, ssid, type);
			
			String template = getTemplate(ruleTypeId);
			ArrayList<String> vals = getValues(ruleId);
			ArrayList<String> triggers = getTriggers(ruleId);

			BusinessRule b = new BusinessRule(ruleId, ruleTypeId, nameCode,
					operatorName, tabTa, colTa, errorMessage, template, targetAppId, triggers, vals, tdb);
			allBusinessRules.add(b);
		}
		stmt.close();
		return allBusinessRules;
	}
	
	public String getTemplate(int brt) throws SQLException{
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT BUSINESSRULETYPE.TEMPLATE FROM BUSINESSRULETYPE WHERE BRT_ID = " + brt);
		String template = "";
		while(resultSet.next()){
			template = resultSet.getString("TEMPLATE");
		}
		stmt.close();
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
		stmt.close();
		return triggers;
	}
	
	public String getOperator(int operatorId) throws SQLException{
		Statement stmt = connection.createStatement();
		ResultSet operatorSet = stmt.executeQuery("SELECT OPERATOR FROM OPERATOR_TYPE WHERE OT_ID = " + operatorId);			
		String operatorName = "";
		while(operatorSet.next()){
			operatorName = operatorSet.getString("OPERATOR");
		}
		stmt.close();
		return operatorName;
	}
	
	public ArrayList<String> getValues(int br) throws SQLException{
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT VALUE, TYPE FROM LITERAL_VALUE WHERE BUSINESSRULE_BR_ID = " + br);
		ArrayList<String> values = new ArrayList<String>();
		
		String v = "";
		while(resultSet.next()){
			if(resultSet.getString("TYPE").equals("varchar")){
				v = resultSet.getString("VALUE");
				v = "'" + v + "'";
				values.add(v);
			} else{
				v = resultSet.getString("VALUE");
				values.add(v);
			}
		}
		stmt.close();
		return values;
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