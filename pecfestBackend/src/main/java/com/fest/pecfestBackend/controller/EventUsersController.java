package com.fest.pecfestBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fest.pecfestBackend.service.EventUsersService;
import com.fest.pecfestBackend.entity.EventUsers;
import com.fest.pecfestBackend.response.WrapperResponse;

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
