package com.hergott.stockops.inventory.service;

import java.util.List;

import com.hergott.stockops.inventory.model.Product;

public interface ProductService {
	Product createProduct(Product product);
	Product getProductByName(String name);
	Product getProductById(int id);
	List<Product> getAllProducts();
	Product updateProduct(Product product);
	boolean deleteProduct(Product product);
	
}
