package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
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
	public WrapperResponse<User> editUser(@PathVariable("id") Long id
			,@RequestBody User body) {
		return userService.editUser(id, body);
	}

	@PatchMapping("/accommodation/{id}")
	public WrapperResponse<User> accommodationRequired(@PathVariable("id") Long id) {
		return userService.setAccommodation(id);
	}
	@GetMapping("/registered-events")
	public WrapperResponse getRegisteredEvents(@RequestHeader("session_id") String sessionId) {
		return userService.getRegisteredEventsForUser(sessionId);
	}
	@GetMapping("/details")
	public WrapperResponse getUserDetails(@RequestHeader("session_id") String sessionId){
		return userService.getUserDetails(sessionId);
	}
	
}
