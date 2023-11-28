package com.hergott.stockops.inventory.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "Hello, home";
	}
	
	@GetMapping("/secured")
	public String secured() {
		return "index.html";
	}
	
	@GetMapping("/error")
	public String error() {
		return "An error has occured";
	}
}
