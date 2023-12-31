package com.hergott.stockops.inventory.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Product {
	private int id; // primary key
	private String productName; // not null
	
	@JsonProperty("stock")
	private int stock; // not null
	
	@JsonProperty("price")
	private double price; // optional
	
	private String description; // optional
	private int reorderLevel; // optional, default 0
	private Date lastUpdated; // not null, auto generated
	
	public Product() {
		
	}
	
	public Product(String productName, int stock, double price,
			String description, int reorderLevel, Date lastUpdated) {
		this.productName = productName;
		this.stock = stock;
		this.price = price;
		this.description = description;
		this.reorderLevel = reorderLevel;
		this.lastUpdated = lastUpdated;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(int reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
