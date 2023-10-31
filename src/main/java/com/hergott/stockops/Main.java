package com.hergott.stockops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Your name
 */

@SpringBootApplication
@ComponentScan(basePackages = "com.hergott.stockops")
public class Main {
    public static void main(String... param) {
    	
    	ApplicationContext context = SpringApplication.run(Main.class, param);
    	
        System.out.println("StockOps application started...");
        
    }
}
