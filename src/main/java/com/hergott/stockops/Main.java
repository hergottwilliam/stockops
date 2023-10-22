package com.hergott.stockops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Your name
 */

@SpringBootApplication
public class Main {
    public static void main(String... param) {
    	
    	SpringApplication.run(Main.class, param);
    	
        System.out.println("Starting StockOps application...");
        
    }
}