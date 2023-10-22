package com.hergott.stockops.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hergott.stockops.inventory.dao.UserDAOImpl;
import com.hergott.stockops.inventory.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAOImpl userDAOImpl;

	@Override
	public User createUser(User user) {
		return userDAOImpl.createUser(user);
	}

	@Override
	public User getUserById(int id) {
		return userDAOImpl.getUserById(id);
	}

	@Override
	public User updateUser(User user) {
		return userDAOImpl.updateUser(user);
	}

	@Override
	public boolean deleteUser(User user) {
		return userDAOImpl.deleteUser(user);
	}

}
