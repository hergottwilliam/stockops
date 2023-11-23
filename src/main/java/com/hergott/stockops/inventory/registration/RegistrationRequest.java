package com.hergott.stockops.inventory.registration;

import java.util.Objects;

public class RegistrationRequest {
	private final String username;
	private final String password;
	private final String email;
	
	
	public RegistrationRequest(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}


	@Override
	public int hashCode() {
		return Objects.hash(email, password, username);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistrationRequest other = (RegistrationRequest) obj;
		return Objects.equals(email, other.email) && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}


	@Override
	public String toString() {
		return "RegistrationRequest [username=" + username + ", password=" + password + ", email=" + email + "]";
	}
	
	
	
}
