package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.BusinessRule;
import domain.Column;
import domain.TargetDatabase;

public class RepositoryDAOImpl implements RepositoryDAO {
	private Connection connection;
	private final String url = "jdbc:oracle:thin:@ondora01.hu.nl:8521:cursus01";
	private final String username = "tho6_2014_2c_team3";
	private final String password = "tho6_2014_2c_team3";

	public RepositoryDAOImpl() throws SQLException {
	}

	public ArrayList<BusinessRule> getAllUngeneratedBusinessRules() throws SQLException {
		ArrayList<BusinessRule> allBusinessRules = new ArrayList<BusinessRule>();
		Statement stmt = connection.createStatement();		
		ResultSet resultSet = stmt
				.executeQuery("SELECT BUSINESSRULE.BR_ID,BUSINESSRULE.ERROR_MESSAGE,BUSINESSRULE.OPERATOR_TYPE_OT_ID,BUSINESSRULETYPE.NAME_CODE,BUSINESSRULE.TABLE_TA,BUSINESSRULE.COLUMN_TA,DATABASE_TYPE.TRIGGER_TEMPLATE"
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
						+ " WHERE STATUS = 0 AND (BUSINESSRULETYPE_BRT_ID != 4 OR BUSINESSRULETYPE_BRT_ID != 6 OR BUSINESSRULETYPE_BRT_ID != 8 OR BUSINESSRULETYPE_BRT_ID != 9)");
	
		
		while(resultSet.next()){
			System.out.println("test");
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
			
			String template = getTemplate(targetAppId);
			String operatorTemplate = getOperatorTemplate(ruleTypeId);
			ArrayList<Column> columns = getColumns(ruleId);
			ArrayList<String> vals = getValues(ruleId);
			ArrayList<String> triggers = getTriggers(ruleId);

			BusinessRule b = new BusinessRule(ruleId, ruleTypeId, nameCode,
					operatorName, tabTa, colTa, errorMessage, template, operatorTemplate, targetAppId, triggers, vals, columns, tdb);
			allBusinessRules.add(b);
		}
		stmt.close();
		return allBusinessRules;
	}

	private String getTemplate(int targetAppId) throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT ta_id, DATABASE_TYPE.TRIGGER_TEMPLATE"
				+ " FROM database_type"
				+ " INNER JOIN targetapplication"
				+ " ON targetapplication.database_type_dt_id = Database_Type.Dt_Id"
				+ " WHERE targetapplication.ta_id =" + targetAppId);
		String template = "";
		while(resultSet.next()){
			template = resultSet.getString("TRIGGER_TEMPLATE");
		}
		stmt.close();
		return template;
	}

	private String getOperatorTemplate(int brt) throws SQLException{
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT BUSINESSRULETYPE.TEMPLATE FROM BUSINESSRULETYPE WHERE BRT_ID = " + brt);
		String template = "";
		while(resultSet.next()){
			template = resultSet.getString("TEMPLATE");
		}
		stmt.close();
		return template;
	}
	
	private ArrayList<String> getTriggers(int br) throws SQLException{
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
	
	private String getOperator(int operatorId) throws SQLException{
		Statement stmt = connection.createStatement();
		ResultSet operatorSet = stmt.executeQuery("SELECT OPERATOR FROM OPERATOR_TYPE WHERE OT_ID = " + operatorId);			
		String operatorName = "";
		while(operatorSet.next()){
			operatorName = operatorSet.getString("OPERATOR");
		}
		stmt.close();
		return operatorName;
	}
	
	private ArrayList<String> getValues(int br) throws SQLException{
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT VALUE, TYPE FROM LITERAL_VALUE WHERE BUSINESSRULE_BR_ID = " + br);
		ArrayList<String> values = new ArrayList<String>();
		
		String v = "";
		while(resultSet.next()){
			if(resultSet.getString("TYPE").equals("varchar2")){
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
	
	private ArrayList<Column> getColumns(int br) throws SQLException{
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT NAME, TABLE_NAME, UNIQUE_VALUE, UNIQUE_VALUE_NAME, COLUMN_TYPE FROM COLUMN_VALUE WHERE BUSINESSRULE_BR_ID = " + br);
		ArrayList<Column> columns = new ArrayList<Column>();
		while(resultSet.next()){
			String name = resultSet.getString("NAME");
			String table = resultSet.getString("TABLE_NAME");
			String uniqueValue = resultSet.getString("UNIQUE_VALUE");
			String uniqueValueName = resultSet.getString("UNIQUE_VALUE_NAME");
			String type = resultSet.getString("COLUMN_TYPE");
			Column column = new Column(name, table, uniqueValue, uniqueValueName, type);
			columns.add(column);
		}
		stmt.close();
		return columns;
	}

	public void setGeneratedBusinessRules(String businessRule, int businessRuleId) throws SQLException{
		PreparedStatement stmt = connection.prepareStatement("UPDATE BUSINESSRULE SET GENERATED_CODE=? WHERE BR_ID = ?");
		stmt.setString(1, businessRule);
		stmt.setInt(2, businessRuleId);
		stmt.execute();
		stmt.close();
	}
	
	public void setStatus(int businessRuleId, int Status) throws SQLException{
		PreparedStatement stmt = connection.prepareStatement("UPDATE BUSINESSRULE SET STATUS=? WHERE BR_ID = ?");
		stmt.setInt(1, Status);
		stmt.setInt(2, businessRuleId);
		stmt.execute();
		stmt.close();
	}

	public void connectToRepository() {
		try {
			Class.forName ("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public void closeConnection() throws SQLException{
		connection.close();
	}
}