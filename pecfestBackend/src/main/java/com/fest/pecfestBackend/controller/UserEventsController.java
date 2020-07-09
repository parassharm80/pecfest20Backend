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
import com.fest.pecfestBackend.entity.UserEvents;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.UserEventsService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.json.JsonObject;

@RestController
@RequestMapping("/userevents")
public class UserEventsController {

	@Autowired
	private UserEventsService userEventsService;
	
	@GetMapping
	public WrapperResponse<List<UserEvents> > getUserEvents(@RequestBody UserEvents body) {
		return userEventsService.fetchUserEvents(body);
	}
	
	@PostMapping
	public WrapperResponse<UserEvents> addUserEvent(@RequestBody UserEvents body) {
		return userEventsService.saveUserEvents(body);
	}
	
	@PatchMapping("/{id}")
	public WrapperResponse<UserEvents> editUserEvents(@PathVariable("id") String id,@RequestBody UserEvents body) {
		return userEventsService.updateUserEvents(id, body);
	}
	
}