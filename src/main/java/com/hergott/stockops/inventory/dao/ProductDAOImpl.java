package com.hergott.stockops.inventory.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hergott.stockops.inventory.model.Product;

import java.sql.Date;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product createProduct(Product product) {
        String insertQuery = "INSERT INTO Inventory (name, stock, price, description, reorder_level, last_updated) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            int rowsAffected = jdbcTemplate.update(insertQuery,
                    product.getProductName(),
                    product.getStock(),
                    product.getPrice(),
                    product.getDescription(),
                    product.getReorderLevel(),
                    product.getLastUpdated());

            if (rowsAffected > 0) {
                Integer generatedId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
                if (generatedId != null) {
                    product.setId(generatedId);
                    return product;
                }
            }
        } catch (Exception e) {
            System.out.println("Error creating product");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Product getProductByName(String name) {
        String selectQuery = "SELECT * FROM Inventory WHERE name = ?";

        try {
            return jdbcTemplate.queryForObject(selectQuery, (resultSet, rowNum) ->
                    mapResultSetToProduct(resultSet), name);
        } catch (Exception e) {
            System.out.println("Error getting product by name");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Product getProductById(int id) {
        String selectQuery = "SELECT * FROM Inventory WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(selectQuery, (resultSet, rowNum) ->
                    mapResultSetToProduct(resultSet), id);
        } catch (Exception e) {
            System.out.println("Error getting product by ID");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        String selectQuery = "SELECT * FROM Inventory";

        try {
            return jdbcTemplate.query(selectQuery, (resultSet, rowNum) ->
                    mapResultSetToProduct(resultSet));
        } catch (Exception e) {
            System.out.println("Error getting all products");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Product updateProduct(Product product) {
        String updateQuery = "UPDATE Inventory SET name = ?, stock = ?, price = ?, description = ?, reorder_level = ?, last_updated = ? WHERE id = ?";

        try {
            int rowsAffected = jdbcTemplate.update(updateQuery,
                    product.getProductName(),
                    product.getStock(),
                    product.getPrice(),
                    product.getDescription(),
                    product.getReorderLevel(),
                    product.getLastUpdated(),
                    product.getId());

            if (rowsAffected > 0) {
                return product;
            }
        } catch (Exception e) {
            System.out.println("Error updating product");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean deleteProduct(Product product) {
        String deleteQuery = "DELETE FROM Inventory WHERE id = ?";

        try {
            int rowsAffected = jdbcTemplate.update(deleteQuery, product.getId());

            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error deleting product");
            e.printStackTrace();
        }

        return false;
    }

    private Product mapResultSetToProduct(java.sql.ResultSet resultSet) throws java.sql.SQLException {
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
