package com.fest.pecfestBackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fest.pecfestBackend.entity.Team;
import com.fest.pecfestBackend.repository.TeamRepo;
import com.fest.pecfestBackend.response.WrapperResponse;



@Service
public class TeamService {
	
	@Autowired
	private TeamRepo teamRepo;

	public WrapperResponse<List<Team>> getTeam(){
		return WrapperResponse.<List<Team>>builder().
				data(teamRepo.findAll()).build();
	}
	
	public WrapperResponse<Team> addTeam(Team body){
		teamRepo.save(body);
		return WrapperResponse.<Team>builder().
				data(body).build();
	}
	
	public WrapperResponse<Team> editTeam(Long id, Team body){
		
		if(!teamRepo.existsById(id))
		{
			return WrapperResponse.<Team>builder().data(body).build();
		}
		body.setEventId(id);
		teamRepo.save(body);
		return WrapperResponse.<Team>builder().data(body).build();
		
	}
}
