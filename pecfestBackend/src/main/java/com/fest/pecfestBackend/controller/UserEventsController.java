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
import com.fest.pecfestBackend.repository.UserEventsRepo;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.json.JsonObject;

@RestController
@RequestMapping("/userevents")
public class UserEventsController {

	@Autowired
	private UserEventsRepo userEventsRepo;
	
	@GetMapping
	public List<UserEvents> getUserEvents(){
		return (List<UserEvents>)userEventsRepo.findAll();
		// change fetch for specific user
	}
	
	@PostMapping
	public void addUserEvent(@RequestBody UserEvents body) {
		userEventsRepo.save(body);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity editUser(@PathVariable("id") String id,@RequestBody UserEvents body) {
		// Check if id is not present error
		UserEvents userOldData = (UserEvents) userEventsRepo.findByUserId(id);
		
		List<String> userNewEvents = body.getUserEvents();
		List<String> userOldEvents = userOldData.getUserEvents();
		
		List<String> userAllEvents = Stream.concat(userOldEvents.stream(), userNewEvents.stream()).distinct().collect(Collectors.toList());
		
		body.setUserEvents(userAllEvents);
		
		userEventsRepo.save(body);
		return ResponseEntity.ok(body);
	}
	
}