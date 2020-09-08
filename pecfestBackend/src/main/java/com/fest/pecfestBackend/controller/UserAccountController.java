package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.request.UserSignUpRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.service.UserAccountService;

@RestController("/register")
public class UserAccountController {


	@Autowired
	private UserAccountService useraccountService;
	
	@GetMapping
	public ModelAndView displayRegistration(ModelAndView modelAndView, User user) {
		return useraccountService.displayRegistration(modelAndView, user);
	}
	
	@PostMapping
	public WrapperResponse registerUser(@RequestBody UserSignUpRequest userSignUpRequest) {
		return useraccountService.registerUser(userSignUpRequest);
	}
	
	@RequestMapping(value="/confirm", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam String confirmationToken) {
		return useraccountService.confirmUserAccount(modelAndView, confirmationToken);
	}
}
