package com.fest.pecfestBackend.service;


import com.fest.pecfestBackend.entity.EventUsers;
import com.fest.pecfestBackend.entity.Team;
import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.repository.EventUsersRepo;
import com.fest.pecfestBackend.repository.TeamRepo;
import com.fest.pecfestBackend.repository.UserRepo;
import com.fest.pecfestBackend.response.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EventUsersService {
	
	@Autowired
	private EventUsersRepo eventUsersRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private TeamRepo teamRepo;
	
	public WrapperResponse< List<EventUsers> > fetchEventUsers(EventUsers body){
		return WrapperResponse.< List<EventUsers> >builder().data(eventUsersRepo.findAll()).build();
		// change fetch for specific event
	}

	public  EventUsers fetchEvent(String id){

		EventUsers eventResult = (EventUsers) eventUsersRepo.findEventById(id);
			return eventResult;
	}
	
	public WrapperResponse<EventUsers> saveEventUsers(EventUsers body){
		String eventId = body.getId();
		EventUsers eventResult = (EventUsers) eventUsersRepo.findEventById(eventId);
		
		if(eventResult!=null)
		{
			return WrapperResponse.<EventUsers>builder().data(body).
				statusCode("FAILED").
				statusMessage("CONFIGURATION ALREADY EXISTS").build();
		}

		eventUsersRepo.save(body);
		return WrapperResponse.<EventUsers>builder().data(body).build();
	}
	
	public WrapperResponse<EventUsers> updateEventUsers(String id, EventUsers body){
		
		EventUsers eventOldData = (EventUsers) eventUsersRepo.findEventById(id);
		
		if(eventOldData==null)
		{
			eventUsersRepo.save(body);	
		}
		else
		{
			List<String> eventNewUsers = body.getEventUsers();
			List<String> eventOldUsers = eventOldData.getEventUsers();
			
			
			boolean isSubset = eventOldUsers.containsAll(eventNewUsers);
			
			if(isSubset)
			{
				return WrapperResponse.<EventUsers>builder().data(body).
					statusCode("FAILED").
					statusMessage("CONFIGURATION ALREADY EXISTS").build();		
			}
			
			List<String> eventAllusers = Stream.concat(eventOldUsers.stream(), eventNewUsers.stream()).distinct().collect(Collectors.toList());
			
			body.setEventUsers(eventAllusers);
			
			eventUsersRepo.save(body);
		}
		return WrapperResponse.<EventUsers>builder().data(body).build();
	}

	public  List<User> fetchAllUsers(String id){

		EventUsers eventResult = (EventUsers) eventUsersRepo.findEventById(id);
		List<String> userIds = eventResult.getEventUsers();
		List<Long> modifiedUserIds = new ArrayList<>();
		for(String ID: userIds){
			modifiedUserIds.add(Long.parseLong(ID));
		}
         return userRepo.findAllById(modifiedUserIds);
	}

	public Map<Team,List<User>> fetchAllTeams(String id){

		EventUsers eventResult = (EventUsers) eventUsersRepo.findEventById(id);
		List<String> teamIds = eventResult.getEventUsers();
		List<Long> modifiedTeamIds = new ArrayList<>();
		for(String ID: teamIds){
			modifiedTeamIds.add(Long.parseLong(ID));
		}
		List<Team> teams = teamRepo.findAllById(modifiedTeamIds);

		Map<Team,List<User>> result = new HashMap<>();

		for(Team team:teams){
			List<String> pecFestIds= team.getMemberPecFestIdList();
			List<User> users =userRepo.findAllByPecFestId(pecFestIds);
			result.put(team,users);
		}
     return result;
	}
}
