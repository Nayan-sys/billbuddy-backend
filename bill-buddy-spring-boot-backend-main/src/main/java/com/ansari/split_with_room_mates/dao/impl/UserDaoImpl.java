package com.ansari.split_with_room_mates.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ansari.split_with_room_mates.dao.UserDao;
import com.ansari.split_with_room_mates.dto.User;
import com.ansari.split_with_room_mates.repository.UserRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UserDaoImpl implements UserDao {

	private UserRepository repository;

	@Override
	public User saveUserDao(User user) {
		return repository.save(user);
	}

	@Override
	public User findByEmailDao(String userEmail) {
		List<User> users = repository.findByEmail(userEmail);

		if (users.size() > 0) {
			return users.get(0);
		}

		return null;
	}

	@Override
	public User findByUserIdDao(int userId) {
		Optional<User> optional = repository.findById(userId);

		if (optional.isPresent()) {
			return optional.get();
		}

		return null;
	}
}