package com.fest.pecfestBackend.service;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.entity.UserEvents;
import com.fest.pecfestBackend.repository.UserEventsRepo;

@Service
public class UserEventsService {

	@Autowired
	private UserEventsRepo userEventsRepo;
	
	public WrapperResponse< List<UserEvents> > fetchUserEvents(UserEvents body){
		return WrapperResponse.< List<UserEvents> >builder().data(userEventsRepo.findAll()).build();
		// change fetch for specific user
	}
	
	public WrapperResponse<UserEvents> saveUserEvents(UserEvents body){
		String userId = body.getId();
		
		UserEvents userResult = (UserEvents) userEventsRepo.findUserById(userId);
		
		if(userResult!=null)
		{
			return WrapperResponse.<UserEvents>builder().data(body).
				statusCode("FAILED").
				statusMessage("CONFIGURATION ALREADY EXISTS").build();
		}
		
		userEventsRepo.save(body);
		return WrapperResponse.<UserEvents>builder().data(body).build();
	}
	
	public WrapperResponse<UserEvents> updateUserEvents(String id, UserEvents body){
		
		UserEvents userOldData = (UserEvents) userEventsRepo.findUserById(id);
		
		if(userOldData==null)
		{
			userEventsRepo.save(body);	
		}
		else
		{
			List<String> userNewEvents = body.getUserEvents();
			List<String> userOldEvents = userOldData.getUserEvents();
			
			boolean isSubset = userOldEvents.containsAll(userNewEvents);
			
			if(isSubset)
			{
				return WrapperResponse.<UserEvents>builder().data(body).
					statusCode("FAILED").
					statusMessage("CONFIGURATION ALREADY EXISTS").build();		
			}
			
			List<String> userAllEvents = Stream.concat(userOldEvents.stream(), userNewEvents.stream()).distinct().collect(Collectors.toList());
			
			body.setUserEvents(userAllEvents);
			
			userEventsRepo.save(body);
		}
		return WrapperResponse.<UserEvents>builder().data(body).build();
	}	
}
