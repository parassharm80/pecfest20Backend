package com.fest.pecfestBackend.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fest.pecfestBackend.entity.EventUsers;
import com.fest.pecfestBackend.repository.EventUsersRepo;

@RestController
@RequestMapping("/eventusers")
public class EventUsersController {
	
	@Autowired
	private EventUsersRepo eventUsersRepo;
	
	@GetMapping
	public List<EventUsers> getUserEvents(){
		return (List<EventUsers>)eventUsersRepo.findAll();
		// change fetch for specific user
	}
	
	@PostMapping
	public void addUserEvent(@RequestBody EventUsers body) {
		eventUsersRepo.save(body);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity editUser(@PathVariable("id") String id,@RequestBody EventUsers body) {
		// Check if id is not present error
		EventUsers userOldData = (EventUsers) eventUsersRepo.findByUserId(id);
		
		List<String> userNewEvents = body.getEventUsers();
		List<String> userOldEvents = userOldData.getEventUsers();
		
		List<String> userAllEvents = Stream.concat(userOldEvents.stream(), userNewEvents.stream()).distinct().collect(Collectors.toList());
		
		body.setEventUsers(userAllEvents);
		
		eventUsersRepo.save(body);
		return ResponseEntity.ok(body);
	}
	
}
