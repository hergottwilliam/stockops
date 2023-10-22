package com.hergott.stockops.inventory.dao;

import com.hergott.stockops.inventory.model.User;

public interface UserDAO {
	User createUser(User user);
	User getUserById(int id);
	User updateUser(User user);
	boolean deleteUser(User user);
}
