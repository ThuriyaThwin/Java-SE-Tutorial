package com.jdc.cinema.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
	
	private static final String URL = "jdbc:mysql://localhost:3306/cinema_db";
	private static final String USER = "root";
	private static final String PASS = "admin";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}
	
	public static void clearTable(String ... tables) {
		
		String sql = "truncate table %s";
		String checkOff = "set FOREIGN_KEY_CHECKS = 0";
		String checkOn = "set FOREIGN_KEY_CHECKS = 1";
		
		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement()) {
			
			stmt.execute(checkOff);
			
			for(String s : tables) {
				stmt.execute(String.format(sql, s));
			}
			
			stmt.execute(checkOn);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
