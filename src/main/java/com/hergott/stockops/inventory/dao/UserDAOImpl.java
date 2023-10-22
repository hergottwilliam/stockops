package com.hergott.stockops.inventory.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.hergott.stockops.DatabaseConnector;
import com.hergott.stockops.inventory.model.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@Override
	public User createUser(User user) {
		String insertQuery = "INSERT INTO Users (username, password, email) VALUES (?, ?, ?)";
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	
            try (Connection connection = DatabaseConnector.getConnection();
            		PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, 
            				PreparedStatement.RETURN_GENERATED_KEYS)) {
            	preparedStatement.setString(1, user.getUsername());
            	preparedStatement.setString(2, user.getPassword());
            	preparedStatement.setString(3, user.getEmail());
            	
            	int rowsAffected = preparedStatement.executeUpdate();
            	
            	if (rowsAffected > 0) {
            		try (ResultSet generatedKeySet = preparedStatement.getGeneratedKeys()) {
            			if (generatedKeySet.next()) {
            				int generatedId = generatedKeySet.getInt(1);
            				user.setId(generatedId); // sets user's ID to match the auto_incremented id created by mySQL
            				return user;
            			}
            		}
            	}
            	
            	System.out.println("Database connected successfully (createUser method).");
            } catch (SQLException e) {
            	System.out.println("Database connector failed (createUser method).");
            	e.printStackTrace();
            }        	
        	
        } catch (ClassNotFoundException e) {
			System.out.println("Failed to load MySQL JDBC driver (createUser method).");
			e.printStackTrace();
		}
        
		return null;
	}

	@Override
	public User getUserById(int id) {
		String selectQuery = "SELECT * FROM Users WHERE id = ?";
		
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	
            try (Connection connection = DatabaseConnector.getConnection();
            		PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            	preparedStatement.setInt(1, id);
            	try (ResultSet resultSet = preparedStatement.executeQuery()) {
            		if (resultSet.next()) {
            			User user = new User();
            			user.setId(resultSet.getInt("id"));
            			user.setUsername(resultSet.getString("username"));
            			user.setPassword(resultSet.getString("password"));
            			user.setEmail(resultSet.getString("email"));
            			return user;
            		}
            	}
            	
            	
            	System.out.println("Database connected successfully (getUserById method).");
            } catch (SQLException e) {
            	System.out.println("Database connector failed (getUserById method).");
            	e.printStackTrace();
            }        	
        	
        } catch (ClassNotFoundException e) {
			System.out.println("Failed to load MySQL JDBC driver (getUserById method).");
			e.printStackTrace();
		}
        		
		return null;
	}

	@Override
	public User updateUser(User user) {
		String updateQuery = "UPDATE Users SET username = ?, password = ?, email = ? WHERE id = ?";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try (Connection connection = DatabaseConnector.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
				preparedStatement.setString(1, user.getUsername());
				preparedStatement.setString(2, user.getPassword());
				preparedStatement.setString(3, user.getEmail());
				preparedStatement.setInt(4, user.getId());
				
				int rowsAffected = preparedStatement.executeUpdate();
				
				if (rowsAffected > 0) {
					return user; // updated user object
				}
				
			} catch (SQLException e) {
				System.out.println("Database connector failed (updateUser method");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load MySQL JDBC Driver (updateUser method");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean deleteUser(User user) {
		String deleteQuery = "DELETE FROM Users WHERE id = ?";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try (Connection connection = DatabaseConnector.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
				preparedStatement.setInt(1, user.getId());
				
				int rowsAffected = preparedStatement.executeUpdate();
				
				if (rowsAffected > 0) {
					return true; // delete operation was successful
				}
			} catch (SQLException e) {
				System.out.println("Database connector failed (deleteUser method)");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load MySQL JDBC driver (deleteUser method)");
			e.printStackTrace();
		}
		
		return false; // delete operation failed
	}
}
