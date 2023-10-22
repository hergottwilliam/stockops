package com.hergott.stockops.inventory.service;

import com.hergott.stockops.inventory.model.User;

public interface UserService {
	User createUser(User user);
	User getUserById(int id);
	User updateUser(User user);
	boolean deleteUser(User user);
}
