package com.hergott.stockops.inventory.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hergott.stockops.DatabaseConnector;
import com.hergott.stockops.inventory.model.Product;

@Repository
public class ProductDAOImpl implements ProductDAO {
	
	@Override
	public Product createProduct(Product product) {
		String insertQuery = "INSERT INTO Inventory (name, stock, price, description, reorder_level, last_updated) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	
            try (Connection connection = DatabaseConnector.getConnection();
            		PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, 
            				PreparedStatement.RETURN_GENERATED_KEYS)) {
            	preparedStatement.setString(1, product.getProductName());
            	preparedStatement.setInt(2, product.getStock());
            	preparedStatement.setDouble(3, product.getPrice());
            	preparedStatement.setString(4, product.getDescription());
            	preparedStatement.setInt(5, product.getReorderLevel());
            	preparedStatement.setDate(6, product.getLastUpdated());
            	
            	int rowsAffected = preparedStatement.executeUpdate();
            	
            	if (rowsAffected > 0) {
            		try (ResultSet generatedKeySet = preparedStatement.getGeneratedKeys()) {
            			if (generatedKeySet.next()) {
            				int generatedId = generatedKeySet.getInt(1);
            				product.setId(generatedId); // sets product's ID to match the auto_incremented id created by mySQL
            				return product;
            			}
            		}
            	}
            	
            	System.out.println("Database connected successfully (createProduct method).");
            } catch (SQLException e) {
            	System.out.println("Database connector failed (createProduct method).");
            	e.printStackTrace();
            }        	
        	
        } catch (ClassNotFoundException e) {
			System.out.println("Failed to load MySQL JDBC driver (createProduct method).");
			e.printStackTrace();
		}
        
		return null;
	}

	@Override
	public Product getProductByName(String name) {
		String selectQuery = "SELECT * FROM Inventory WHERE name = ?";
		
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	
            try (Connection connection = DatabaseConnector.getConnection();
            		PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            	preparedStatement.setString(1, name);
            	try (ResultSet resultSet = preparedStatement.executeQuery()) {
            		if (resultSet.next()) {
            			Product product = new Product();
            			product.setId(resultSet.getInt("id"));
            			product.setProductName(resultSet.getString("name"));
            			product.setStock(resultSet.getInt("stock"));
            			product.setPrice(resultSet.getDouble("price"));
            			product.setDescription(resultSet.getString("description"));
            			product.setReorderLevel(resultSet.getInt("reorder_level"));
            			product.setLastUpdated(resultSet.getDate("last_updated"));
            			
            			return product;
            		}
            	}
            	
            	
            	System.out.println("Database connected successfully (getProductByName method).");
            } catch (SQLException e) {
            	System.out.println("Database connector failed (getProductByName method).");
            	e.printStackTrace();
            }        	
        	
        } catch (ClassNotFoundException e) {
			System.out.println("Failed to load MySQL JDBC driver (getProductByName method).");
			e.printStackTrace();
		}
        		
		return null;
	}

	@Override
	public Product getProductById(int id) {
		String selectQuery = "SELECT * FROM Inventory WHERE id = ?";
		
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	
            try (Connection connection = DatabaseConnector.getConnection();
            		PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            	preparedStatement.setInt(1, id);
            	try (ResultSet resultSet = preparedStatement.executeQuery()) {
            		if (resultSet.next()) {
            			Product product = new Product();
            			product.setId(resultSet.getInt("id"));
            			product.setProductName(resultSet.getString("name"));
            			product.setStock(resultSet.getInt("stock"));
            			product.setPrice(resultSet.getDouble("price"));
            			product.setDescription(resultSet.getString("description"));
            			product.setReorderLevel(resultSet.getInt("reorder_level"));
            			product.setLastUpdated(resultSet.getDate("last_updated"));
            			
            			return product;
            		}
            	}
            	
            	
            	System.out.println("Database connected successfully (getProductById method).");
            } catch (SQLException e) {
            	System.out.println("Database connector failed (getProductById method).");
            	e.printStackTrace();
            }        	
        	
        } catch (ClassNotFoundException e) {
			System.out.println("Failed to load MySQL JDBC driver (getProductById method).");
			e.printStackTrace();
		}
        		
		return null;
	}
	
	@Override
	public List<Product> getAllProducts() {
		List<Product> allProducts = new ArrayList<>();
		String selectQuery = "SELECT * FROM Inventory";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try (Connection connection = DatabaseConnector.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
					ResultSet resultSet = preparedStatement.executeQuery()) {
				
			while (resultSet.next()) {
				Product product = new Product();
				product.setId(resultSet.getInt("id"));
				product.setProductName(resultSet.getString("name"));
				product.setStock(resultSet.getInt("stock"));
				product.setPrice(resultSet.getDouble("price"));
				product.setDescription(resultSet.getString("description"));
				product.setReorderLevel(resultSet.getInt("reorder_level"));
				product.setLastUpdated(resultSet.getDate("last_updated"));
				allProducts.add(product);
			}
			
			return allProducts;
			} catch (SQLException e) {
				System.out.println("Database connector failed (getAllProducts method).");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load MySQL JDBC Driver (getAllProducts method).");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Product updateProduct(Product product) {
		String updateQuery = "UPDATE Inventory SET name = ?, stock = ?, price = ?, description = ?, "
				+ "reorder_level = ?, last_updated = ? WHERE id = ?";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try (Connection connection = DatabaseConnector.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
				preparedStatement.setString(1, product.getProductName());
				preparedStatement.setInt(2, product.getStock());
				preparedStatement.setDouble(3, product.getPrice());
				preparedStatement.setString(4, product.getDescription());
				preparedStatement.setInt(5, product.getReorderLevel());
				preparedStatement.setDate(6, product.getLastUpdated());
				preparedStatement.setInt(7, product.getId());
				
				int rowsAffected = preparedStatement.executeUpdate();
				
				if (rowsAffected > 0) {
					return product; // updated Product object
				}
				
			} catch (SQLException e) {
				System.out.println("Database connector failed (updateProduct method");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load MySQL JDBC Driver (updateProduct method");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean deleteProduct(Product product) {
		String deleteQuery = "DELETE FROM Inventory WHERE id = ?";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try (Connection connection = DatabaseConnector.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
				preparedStatement.setInt(1, product.getId());
				
				int rowsAffected = preparedStatement.executeUpdate();
				
				if (rowsAffected > 0) {
					return true; // delete operation was successful
				}
			} catch (SQLException e) {
				System.out.println("Database connector failed (deleteProduct method)");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load MySQL JDBC driver (deleteProduct method)");
			e.printStackTrace();
		}
		
		return false;
	}
}
