package com.hergott.stockops.inventory.repo;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hergott.stockops.inventory.model.User;

@Repository
@Transactional(readOnly = true)
public interface UserRepository {
	Optional<User> findByEmail(String email);
}
