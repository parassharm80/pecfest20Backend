package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.entity.Team;
import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.repository.EventRepo;
import com.fest.pecfestBackend.repository.TeamRepo;
import com.fest.pecfestBackend.repository.UserRepo;
import com.fest.pecfestBackend.response.UserDetailsResponse;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private TeamRepo teamRepo;
	@Autowired
	private EventRepo eventRepo;
	
	public WrapperResponse<List<User>> getUser() {
		return WrapperResponse.<List<User>>builder()
				.data(userRepo.findAll()).build();
	}
	
	public WrapperResponse<User> addUser(User body){
		User entitiesList = userRepo.findByEmail(body.getEmail());
		if(entitiesList!=null)
		{
			return WrapperResponse.<User>builder().data(body).
			statusCode("FAILED").
			statusMessage("CONFIGURATION ALREADY EXISTS").httpStatus(HttpStatus.BAD_REQUEST).build();
		}
		// Storing password hash instead of password
		String hashedPassword= Hashing.sha512().hashString(body.getPassword(), StandardCharsets.UTF_8).toString();
		body.setPassword(hashedPassword);
		userRepo.save(body);
		return WrapperResponse.<User>builder().data(body).build();
	}
	
	public WrapperResponse<User> editUser(Long id, User body) {
		
		if(!userRepo.existsById(id))
		{
			return WrapperResponse.<User>builder().data(body).
			statusCode("FAILED").
			statusMessage("CONFIGURATION DOES NOT EXISTS").build();			
		}
		body.setId(id);
		User entitiesList = userRepo.findByEmail(body.getEmail());
		if(entitiesList!=null)
		{
			return WrapperResponse.<User>builder().data(body).
			statusCode("FAILED").
			statusMessage("CONFIGURATION ALREADY EXISTS").build();
		}
		userRepo.save(body);
		return WrapperResponse.<User>builder().data(body).build();
	}

	public WrapperResponse<User> setAccommodation(Long id) {

		if(!userRepo.findById(id).isPresent()){
			return WrapperResponse.<User>builder().
					statusCode("FAILED").
					statusMessage("CONFIGURATION DOES NOT EXISTS").build();
		}
		User currentUser= userRepo.findById(id).get();
		currentUser.setRequireAccommodation(true);
		userRepo.save(currentUser);
		return WrapperResponse.<User>builder().data(currentUser).build();
	}

	public WrapperResponse<List<User>> getUsersWithAccommodation() {
		return WrapperResponse.<List<User>>builder()
				.data(userRepo.findByRequireAccommodationTrue()).build();
	}

	@Cacheable("registeredEventsForUser")
    public WrapperResponse getRegisteredEventsForUser(String sessionId) {
		if(Objects.isNull(sessionId)||sessionId.length()<2)
			return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("Invalid sessionId").build();
		User user=userRepo.findBySessionId(sessionId);
		if(Objects.isNull(user))
			return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("Invalid sessionId").build();
		else{
			List<Long> eventsRegisteredIdList=new ArrayList<>();

			List<Team> teams=teamRepo.findAll();
			for(Team team:teams)
				if(team.getMemberPecFestIdList().contains(user.getPecFestId()))
					eventsRegisteredIdList.add(team.getEventId());
				return WrapperResponse.builder().data(eventRepo.findAllById(eventsRegisteredIdList)).build();
		}
    }

	public WrapperResponse getUserDetails(String sessionId) {
		if(Objects.isNull(sessionId)||sessionId.length()<2)
			return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("Invalid sessionId").build();
		User user=userRepo.findBySessionId(sessionId);
		if(Objects.isNull(user))
			return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("Invalid sessionId").build();
		else
			return WrapperResponse.builder().data(UserDetailsResponse.builder().collegeName(user.getCollegeName()).contactNo(user.getContactNo()
			).email(user.getEmail()).fullName(user.getName()).gender(user.getGender()).yearOfEducation(user.getYearOfEducation()
			).id(user.getId()).pecFestId(user.getPecFestId())
					.firstName(user.getFirstName()).lastName(user.getLastName()).build()).build();

	}
}
