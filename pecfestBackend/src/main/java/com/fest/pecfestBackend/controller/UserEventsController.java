package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.entity.UserEvents;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.UserEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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