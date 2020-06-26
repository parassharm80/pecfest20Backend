package com.fest.pecfestBackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.repository.UserRepo;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.sun.el.stream.Optional;


@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	public WrapperResponse<List<User>> getUser() {
		return WrapperResponse.<List<User>>builder()
				.data(userRepo.findAll()).build();
	}
	
	public WrapperResponse<User> addUser(User body){
		List<User> entitiesList = userRepo.findByEmail(body.getEmail());
		if(!entitiesList.isEmpty())
		{
			return WrapperResponse.<User>builder().data(body).
			statusCode("FAILED").
			statusMessage("CONFIGURATION ALREADY EXISTS").build();
		}
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
		List<User> entitiesList = userRepo.findByEmail(body.getEmail());
		if(!entitiesList.isEmpty())
		{
			return WrapperResponse.<User>builder().data(body).
			statusCode("FAILED").
			statusMessage("CONFIGURATION ALREADY EXISTS").build();
		}
		userRepo.save(body);
		return WrapperResponse.<User>builder().data(body).build();
	}

}
