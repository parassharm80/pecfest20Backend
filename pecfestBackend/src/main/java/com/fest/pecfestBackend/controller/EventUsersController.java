package com.fest.pecfestBackend.controller;


import com.fest.pecfestBackend.entity.EventUsers;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.EventUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventusers")
public class EventUsersController {
	
	@Autowired
	private EventUsersService eventUsersService;
	
	@GetMapping
	public WrapperResponse<List<EventUsers> > getEventUsers(@RequestBody EventUsers body) {
		return eventUsersService.fetchEventUsers(body);
	}
	
	@PostMapping
	public WrapperResponse<EventUsers> addEventUser(@RequestBody EventUsers body) {
		return eventUsersService.saveEventUsers(body);
	}
	
	@PatchMapping("/{id}")
	public WrapperResponse<EventUsers> editEventUser(@PathVariable("id") String id,@RequestBody EventUsers body) {
		return eventUsersService.updateEventUsers(id, body);
	}

}
