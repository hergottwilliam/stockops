package com.hergott.stockops.inventory.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hergott.stockops.inventory.registration.RegistrationRequest;
import com.hergott.stockops.inventory.service.RegistrationService;

import jakarta.servlet.Registration;

@RestController
@RequestMapping(path = "api/registration")
public class UserRegistrationController {
	private RegistrationService registrationService;
	
	
	public UserRegistrationController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}



	public String register(@RequestBody RegistrationRequest request) {
		return registrationService.register(request);
	}

}
