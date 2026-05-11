package com.ansari.split_with_room_mates.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ansari.split_with_room_mates.dao.UserDao;
import com.ansari.split_with_room_mates.dto.Items;
import com.ansari.split_with_room_mates.dto.User;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {

	private UserDao userDao;

	private HttpSession httpSession;

	/**
	 * User registration
	 */
	@PostMapping(value = "/saveUser")
	public User saveUserController(@RequestBody User user) {
		return userDao.saveUserDao(user);
	}

	/**
	 * User login
	 */
	@GetMapping(value = "/loginUser/{userEmail}/{userPass}")
	public ResponseEntity<?> loginWithUserController(
			@PathVariable String userEmail,
			@PathVariable String userPass) {

		System.out.println("login user !!!");

		User user = userDao.findByEmailDao(userEmail);

		if (user != null && user.getPassword().equals(userPass)) {
			httpSession.setAttribute("userSession", user.getEmail());

			return ResponseEntity.ok(
					Map.of(
							"message", "Login Success",
							"userEmail", user.getEmail()));
		}

		return ResponseEntity.ok(
				Map.of(
						"message", "Invalid Credentials"));
	}

	/**
	 * User logout
	 */
	@GetMapping(value = "/userLogout")
	public ResponseEntity<?> userLogout() {

		System.out.println("logout user !!!");

		String email = (String) httpSession.getAttribute("userSession");

		if (email != null) {
			httpSession.invalidate();

			return ResponseEntity.ok(
					Map.of(
							"message", "Logout Success",
							"userEmail", email));
		}

		return ResponseEntity.ok(
				Map.of(
						"message", "User already logged out"));
	}

	/**
	 * Total sum of logged-in user's items
	 */
	@GetMapping(value = "/getUserLoggedInAddedItemsSummation")
	public ResponseEntity<?> getUserLoggedInAddedItemsSummation() {

		String email = (String) httpSession.getAttribute("userSession");

		if (email != null) {
			User user = userDao.findByEmailDao(email);

			if (user != null) {
				Double totalSum = user.getItems()
						.stream()
						.mapToDouble(Items::getPrice)
						.sum();

				return ResponseEntity.ok(
						Map.of("totalSum", totalSum));
			}
		}

		return ResponseEntity.ok(
				Map.of(
						"message", "User not found. Please login first."));
	}

	/**
	 * Get logged-in user details
	 */
	@GetMapping(value = "/getUserName")
	public User getUserNameController() {

		String email = (String) httpSession.getAttribute("userSession");

		if (email != null) {
			return userDao.findByEmailDao(email);
		}

		return null;
	}
}