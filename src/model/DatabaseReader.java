package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseReader {

	public DatabaseReader(){
	}
	
	public void ReadDatabaseRepo() throws SQLException{
		
		
		String ruletype = null;
		
		
		System.out.println("-------- Oracle JDBC Connection Testing ------");
		 
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
			connection = DriverManager.getConnection("jdbc:oracle:thin:@ondora01.hu.nl:8521/cursus01.hu.nl", "tho6_2014_2c_team3","tho6_2014_2c_team3");
 
			
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return ;
 
		}
 
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} 
		else {
			System.out.println("Failed to make connection!");
		}

		
		
		// The SQL statement
			String sql ="select * from TEST";
	
	        //creating the statement of the String
			PreparedStatement statement = connection.prepareStatement(sql);
			//Statement statement = connection.createStatement();
			
			// execute the query statement
	        ResultSet result = statement.executeQuery();
			//statement.executeUpdate(sql);
	      
	       while(result.next()){
	    	   ruletype = result.getString("TEST1");
		      

	        }
	        
           
           System.out.println(ruletype);
	       
	        System.out.println("done");
	}
}
