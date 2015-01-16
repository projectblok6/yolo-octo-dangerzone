package dataCreation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Column;
import domain.Component;
import domain.Value;
import domain.businessrule.BusinessRule;
import domain.businessrule.RangeRule;

public class BusinessRuleDAOImpl implements BusinessRuleDAO{
	ArrayList<Component> deComponents = new ArrayList<Component>();
	Statement stmt;
	int id = 0;
	
	public BusinessRuleDAOImpl() throws SQLException{
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

			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@ondoroa01.hu.nl:8080/apex", "tho6_2014_2c_team3",
					"tho6_2014_2c_team3");

		} catch (SQLException e1) {

			System.out.println("Connection Failed! Check output console");
			e1.printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		
		stmt = connection.createStatement();
	}
	
	@Override
	public void createBusinessRules() {
		ResultSet ruleTypeId = null;
		ResultSet ruleType = null;
		try {
			ruleTypeId = stmt.executeQuery("SELECT BUSINESSRULETYPE_BRT_ID FROM BUSINESSRULE WHERE BR_ID = " + id);
			ruleType = stmt.executeQuery("SELECT NAME FROM BUSINESSRULETYPE WHERE BRT_ID = " + ruleTypeId.getInt("BUSINESSRULETYPE_BRT_ID"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(ruleType.equals("Rangerule")){
			RangeRule r = new RangeRule()
		}
		
	}

	@Override
	public void deleteBusinessRule() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<BusinessRule> getAllBusinessRules() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<Component> getUngeneratedBusinessRules() throws SQLException{
	    ResultSet rs = stmt.executeQuery("SELECT BR_ID, NAME_CODE, COLUMN_TA, TABLE_TA, ERROR_MESSAGE, STATUS, OPERATOR_TYPE_OT_ID, TARGETAPPLICATION_TA_ID, BUSINESSRULETYPE_BRT_ID, GENERATED_CODE FROM BUSINESSRULE WHERE STATUS = 0");
	    
	    
	    
	    while (rs.next()) {
	    	id = rs.getInt("BR_ID");
	        String ruleName = rs.getString("NAME_CODE");
	        String column = rs.getString("COLUMN_TA");
	        String table = rs.getString("TABLE_TA");
	        String error = rs.getString("ERROR_MESSAGE");
	        int status = rs.getInt("STATUS");
	        int operatorType = rs.getInt("OPERATOR_TYPE_OT_ID");
	        int targerApplication = rs.getInt("TARGETAPPLICATION_TA_ID");
	        int businessRuleType = rs.getInt("BUSINESSRULETYPE_BRT_ID");
	        String code = rs.getString("GENERATED_CODE");
	        
	        ResultSet col = stmt.executeQuery("SELECT CO_ID, NAME, TABLE_NAME, UNIQUE_VALUE_NAME, UNIQUE_VALUE, BUSINESSRULE_BR_ID FROM COLUMN WHERE BUSINESSRULE_BR_ID = " + id);
		    ResultSet val = stmt.executeQuery("SELECT VALUE FROM LITERAL_VALUE WHERE BUSINESSRULE_BR_ID = " + id);
		    
		    while (col.next()){
		    	Column c = new Column(col.getString("TABLE_NAME") + "." + col.getString("NAME"));
		    	deComponents.add(c);
		    }
		    
		    while(val.next()){
		    	Value v = new Value(val.getString("LITERAL_VALUE"));
		    	deComponents.add(v);
		    }
	    }
	    
		return deComponents;
	}

}
