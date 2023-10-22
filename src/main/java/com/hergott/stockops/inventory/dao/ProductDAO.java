package com.hergott.stockops.inventory.dao;

import java.util.List;

import com.hergott.stockops.inventory.model.Product;

public interface ProductDAO {
	Product createProduct(Product product);
	Product getProductByName(String name);
	List<Product> getAllProducts();
	Product getProductById(int id);
	Product updateProduct(Product product);
	boolean deleteProduct(Product product);
	
}
