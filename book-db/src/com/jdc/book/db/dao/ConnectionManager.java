package com.jdc.book.db.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionManager {
	
	private static final String SET_FK = "SET FOREIGN_KEY_CHECKS=%d";
	private static final String TRUNCATE = "truncate table %s";
	private static String USER;
	private static String URL;
	private static String PASS;
	
	private static Connection connection;
	
	static {
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("db.properties"));
			USER = prop.getProperty("user");
			URL = prop.getProperty("url");
			PASS = prop.getProperty("pass");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void begin() throws SQLException {
		connection = DriverManager.getConnection(URL, USER, PASS);
		connection.setAutoCommit(false);
	}
	
	public static void commit() throws SQLException {
		if(null != connection && !connection.isClosed()) {
			connection.commit();
			connection.close();
		}
	}
	
	public static void rollback() throws SQLException {
		if(null != connection && !connection.isClosed()) {
			connection.rollback();
			connection.close();
		}
	}

	public static Connection getConnection() throws SQLException {
		return connection;
	}
	
	public static Connection generate() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}
	
	
	public static void truncate(String ... tables) {
		
		try (Connection conn = generate();
				Statement stmt = conn.createStatement()) {
			// fk check off
			stmt.execute(String.format(SET_FK, 0));
			// truncate
			for(String table : tables) {
				stmt.execute(String.format(TRUNCATE, table));
			}
			// fk check on
			stmt.execute(String.format(SET_FK, 1));
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
