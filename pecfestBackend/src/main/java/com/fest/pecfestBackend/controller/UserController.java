package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.enums.Club;
import com.fest.pecfestBackend.request.EditUserDetailsRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.SessionService;
import com.fest.pecfestBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;


@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private SessionService sessionService;
	@GetMapping
	public WrapperResponse<List<User>> getUsers(@RequestHeader("session_id") String sessionId) throws Exception {
		User user=sessionService.verifySessionId(sessionId);
		if(Objects.isNull(user)||!user.getCoordinatingClubName().equals(Club.ALL))
			throw new Exception("FORBIDDEN!!");
		return userService.getUser();
	}
	@PostMapping
	public WrapperResponse<User> addUser(@RequestBody User body) {
		return userService.addUser(body);
	}
	
	@PutMapping("/{id}")
	public WrapperResponse editUser(@PathVariable("id") Long id
			,@RequestBody @Valid EditUserDetailsRequest editUserDetailsRequest) {
		return userService.editUser(id, editUserDetailsRequest);
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
