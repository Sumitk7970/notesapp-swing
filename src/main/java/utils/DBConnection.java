package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	
	public DBConnection() {
		
		// create a table for storing notes 
		createTable();
		
	}
	
	private void createTable() {
		String query = "CREATE TABLE IF NOT EXISTS \"notes\" (\r\n"
				+ "	\"id\"	INTEGER NOT NULL,\r\n"
				+ "	\"title\"	TEXT NOT NULL,\r\n"
				+ "	\"text\"	TEXT NOT NULL,\r\n"
				+ "	\"color\"	INTEGER NOT NULL,\r\n"
				+ "	\"date_modified\"	INTEGER NOT NULL,\r\n"
				+ "	\"category\"	INTEGER NOT NULL\r\n"
				+ ");";
		Connection con = connect();
		try {
			Statement st = con.createStatement();
			st.execute(query);
			
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// method to create connection to the database
	public static Connection connect() {
		
		Connection con = null;
		String url = "jdbc:sqlite:database.db";
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(url);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;	
	}
	
	

}
