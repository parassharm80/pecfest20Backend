package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.request.UserSignUpRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class UserAccountController {


	@Autowired
	private UserAccountService useraccountService;
	
//	@GetMapping
//	public ModelAndView displayRegistration(ModelAndView modelAndView, User user) {
//		return useraccountService.displayRegistration(modelAndView, user);
//	}
	
	@PostMapping
	public WrapperResponse registerUser(@RequestBody @Valid UserSignUpRequest userSignUpRequest) {
		return useraccountService.registerUser(userSignUpRequest);
	}
	
//	@RequestMapping(value="/confirm", method = {RequestMethod.GET, RequestMethod.POST})
//	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam String confirmationToken) {
//		return useraccountService.confirmUserAccount(modelAndView, confirmationToken);
//	}
}
