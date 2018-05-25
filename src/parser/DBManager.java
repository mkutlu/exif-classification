package parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class DBManager {
	String dbURL = "jdbc:mysql://localhost:3306/flickr";

	public Connection openConnection(String userName, String password) {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(dbURL, userName, password);
			connection.setAutoCommit(false);
			System.out.println("Opened database successfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return connection;
	}


	public int getColumnNumber(Connection connection) {
		int columnCount = 0;
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM header");
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			columnCount = rsmd.getColumnCount();
			ps.close();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return columnCount-1;
	}
	public void addColumn(Connection connection,String name) {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			System.out.println(name);
			String query = "ALTER TABLE header ADD "+name+" text";
			stmt.execute(query);
			stmt.close();
			connection.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addNewRow(Connection connection,String val) {
		try {
			String query = "INSERT INTO header (photoid) VALUES ('"+val+"');";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.execute();
			connection.commit();
			ps.close();
			connection.commit();
			System.out.println("Running query:" + query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void insertHeaderData(Connection connection,String column,String val,String photoid) {
		try {
			val = val.replace("\"", "");
			val = val.replace("'", "");
			String query = "UPDATE header SET "+column+"='"+val+"' where photoid="+photoid+";";
			//String query = "INSERT INTO header ("+column+ ") VALUES ("+val+") where ;";
			PreparedStatement ps = connection.prepareStatement(query);
			System.out.println("Running query:" + query);
			ps.executeUpdate();
			ps.close();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
