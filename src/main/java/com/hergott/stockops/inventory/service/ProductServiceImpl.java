package com.hergott.stockops.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hergott.stockops.inventory.dao.ProductDAOImpl;
import com.hergott.stockops.inventory.model.Product;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDAOImpl productDAOImpl;
	
	@Override
	public Product createProduct(Product product) {
		return productDAOImpl.createProduct(product);
	}

	@Override
	public Product getProductByName(String name) {
		return productDAOImpl.getProductByName(name);
	}

	@Override
	public Product getProductById(int id) {
		return productDAOImpl.getProductById(id);
	}
	
	@Override 
	public List<Product> getAllProducts() {
		return productDAOImpl.getAllProducts();
	}

	@Override
	public Product updateProduct(Product product) {
		return productDAOImpl.updateProduct(product);
	}

	@Override
	public boolean deleteProduct(Product product) {
		return productDAOImpl.deleteProduct(product);
	}
}
