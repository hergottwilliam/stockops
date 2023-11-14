package com.hergott.stockops.inventory.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	public static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/stockops_db";
		String username = "stockopsuser";
		String password = "cheddar&CHEESE69";
		
		return DriverManager.getConnection(url, username, password);
	}
}
