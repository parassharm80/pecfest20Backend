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

import com.fest.pecfestBackend.entity.Team;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.TeamService;


@RestController
@RequestMapping("/team")
public class TeamController {
	

	@Autowired
	private TeamService teamService;
	
	@GetMapping
	public WrapperResponse<List<Team>> getTeam(){
		return teamService.getTeam();
	}
	
	@PostMapping
	public WrapperResponse<Team> addTeam(@RequestBody Team body){
		return teamService.addTeam(body);
	}
	
	@PatchMapping
	public WrapperResponse<Team> editTeam(@PathVariable Long id,
			@RequestBody Team body){
		return teamService.editTeam(id, body);
	}
}
