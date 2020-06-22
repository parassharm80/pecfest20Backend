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
import java.util.List;

import javax.json.JsonObject;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepo userRepo;
	
	@GetMapping
	public List<User> getUsers(){
		return (List<User>)userRepo.findAll();
	}
	
	@PostMapping
	public void addUser(@RequestBody User body) {
		userRepo.save(body);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity editUser(@PathVariable("id") String id,@RequestBody User body) {
		userRepo.save(body);
		return ResponseEntity.ok(body);
	}
	
	
	
}
