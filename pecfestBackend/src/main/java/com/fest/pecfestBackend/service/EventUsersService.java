package com.fest.pecfestBackend.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fest.pecfestBackend.entity.EventUsers;
import com.fest.pecfestBackend.repository.EventUsersRepo;
import com.fest.pecfestBackend.response.WrapperResponse;

@Service
public class EventUsersService {
	
	@Autowired
	private EventUsersRepo eventUsersRepo;
	
	public WrapperResponse< List<EventUsers> > fetchEventUsers(EventUsers body){
		return WrapperResponse.< List<EventUsers> >builder().data(eventUsersRepo.findAll()).build();
		// change fetch for specific event
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
}
