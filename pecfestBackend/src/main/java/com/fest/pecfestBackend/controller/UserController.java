package com.fest.pecfestBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.repository.UserRepo;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.UserService;

import java.util.List;

import javax.json.JsonObject;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public WrapperResponse<List<User>> getUsers(){
		return userService.getUser();
	}
	
	@PostMapping
	public WrapperResponse<User> addUser(@RequestBody User body) {
		return userService.addUser(body);
	}
	
	@PatchMapping("/{id}")
	public WrapperResponse<User> editUser(@PathVariable("id") Long id,@RequestBody User body) {
		return userService.editUser(id, body);
	}
}