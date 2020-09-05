package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.repository.UserRepo;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;


@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
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
			statusMessage("CONFIGURATION ALREADY EXISTS").build();
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

}
