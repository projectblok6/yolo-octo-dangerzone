package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Main {

	public static void main(String[] argv) throws SQLException {
		 
		DatabaseReader dr = new DatabaseReader();
		dr.ReadDatabaseRepo();

	}

}
