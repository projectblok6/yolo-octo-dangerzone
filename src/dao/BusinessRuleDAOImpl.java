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
import domain.Value;
import domain.businessrule.BusinessRule;
import domain.businessrule.RangeRule;

public class BusinessRuleDAOImpl implements BusinessRuleDAO {
	private Connection connection;
	private final String url = "jdbc:oracle:thin:@ondora01.hu.nl:8521:cursus01";
	private final String username = "tho6_2014_2c_team3";
	private final String password = "tho6_2014_2c_team3";

	public BusinessRuleDAOImpl() throws SQLException {
		connectToRepository();
	}

	public ArrayList<BusinessRule> getEmptyUngeneratedBusinessRules()
			throws SQLException {
		ArrayList<BusinessRule> emptyBusinessRules = new ArrayList<BusinessRule>();
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt
				.executeQuery("SELECT Businessrule.BR_ID,Businessruletype.Name FROM Businessrule INNER JOIN Businessruletype ON Businessruletype.Brt_Id = Businessrule.Businessruletype_Brt_Id WHERE Businessrule.Status = 0 OR Businessrule.Status = 2");
		while (resultSet.next()) {
			int businessRuleId = resultSet.getInt("BR_ID");
			String businessRuleTypeName = resultSet.getString("Name");
			BusinessRule emptyBusinessRule = getBusinessRuleObject(businessRuleTypeName);
			emptyBusinessRule.setRuleId(businessRuleId);
			emptyBusinessRules.add(emptyBusinessRule);
		}
		return emptyBusinessRules;
	}

	private BusinessRule getBusinessRuleObject(String name) {
		if (name.contains("Range rule")) {
			return new RangeRule();
		}
		return null;
		// no businessRule object returned throw exception
	}

	public ArrayList<BusinessRule> getFilledBusinessRules(
			ArrayList<BusinessRule> emptyBusinessRules) throws SQLException {
		// creating prepared statements
		PreparedStatement ruleStatement = connection
				.prepareStatement("SELECT BUSINESSRULE.ERROR_MESSAGE,OPERATOR_TYPE.NAME,Businessrule.Name_Code,Businessrule.Table_Ta,Businessrule.Column_Ta"
						+ " FROM BUSINESSRULE"
						+ " INNER JOIN OPERATOR_TYPE"
						+ " ON BUSINESSRULE.OPERATOR_TYPE_OT_ID=OPERATOR_TYPE.OT_ID"
						+ " WHERE BR_ID = ?");
		PreparedStatement columnStatement = connection
				.prepareStatement("SELECT CO_ID,NAME,TABLE_NAME,UNIQUE_VALUE_NAME,UNIQUE_VALUE,BUSINESSRULE_BR_ID"
						+ " FROM COLUMN_VALUE"
						+ " WHERE BUSINESSRULE_BR_ID = ?");
		PreparedStatement valueStatement = connection
				.prepareStatement("SELECT VALUE"
						+ " FROM LITERAL_VALUE"
						+ " WHERE BUSINESSRULE_BR_ID = ?");
		PreparedStatement triggerStatement = connection
				.prepareStatement("SELECT trigger_type"
						+ " FROM trigger_event"
						+ " WHERE BUSINESSRULE_BR_ID = ?");

		// for each empty businessRule
		for (BusinessRule br : emptyBusinessRules) {
			int businessRuleId = br.getRuleId();

			// setting businessRuleId in statement.
			ruleStatement.setInt(1, businessRuleId);
			columnStatement.setInt(1, businessRuleId);
			valueStatement.setInt(1, businessRuleId);
			triggerStatement.setInt(1, businessRuleId);

			// executing queries
			ResultSet ruleResultSet = ruleStatement.executeQuery();
			ResultSet columnResultSet = columnStatement.executeQuery();
			ResultSet valueResultSet = valueStatement.executeQuery();
			ResultSet triggerResultSet = triggerStatement.executeQuery();

			// creating Components:
			ArrayList<Component> deComponents = new ArrayList<Component>();
			while (columnResultSet.next()) {
				System.out.println("test");
				Column c = new Column(columnResultSet.getString("TABLE_NAME")
						+ "." + columnResultSet.getString("NAME"));
				deComponents.add(c);
			}
			while (valueResultSet.next()) {
				Value v = new Value(valueResultSet.getString("VALUE"));
				deComponents.add(v);
			}
			br.setComponents(deComponents);
			
			//creating trigger events
			ArrayList<String> deTriggerEvents = new ArrayList<String>();
			while (triggerResultSet.next()) {
				deTriggerEvents.add(triggerResultSet.getString("trigger_type"));
			}
			br.setTriggerEvents(deTriggerEvents);
		
			// setting other data in businessRule
			ruleResultSet.next();
			br.setErrorMessage(ruleResultSet.getString("ERROR_MESSAGE"));
			br.setRestrictedColumn(ruleResultSet.getString("Table_Ta")
					+ "." + ruleResultSet.getString("Column_Ta"));
			br.setOperator(new Operator(ruleResultSet.getString("NAME"),
					ruleResultSet.getString("NAME")));
			
			//creating TargetDatabase Data
			
			
		}
		return emptyBusinessRules;
	}

	private void connectToRepository() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;
		}
		System.out.println("Oracle JDBC Driver Registered!");
		Connection connection = null;
		try {

			connection = DriverManager.getConnection(url, username, password);

		} catch (SQLException e1) {
			System.out.println("Connection Failed! Check output console");
			e1.printStackTrace();
		}

		if (connection != null) {
			this.connection = connection;
			System.out.println("You made it, take control your database now!");
		} else {
			// throw no connection exception
			System.out.println("Failed to make connection!");
		}

	}

}
