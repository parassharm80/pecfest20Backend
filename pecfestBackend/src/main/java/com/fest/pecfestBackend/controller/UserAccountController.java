package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.response.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
	public WrapperResponse registerUser(ModelAndView modelAndView, User user) {
		return useraccountService.registerUser(modelAndView, user);
	}
	
	@RequestMapping(value="/confirm", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam String confirmationToken) {
		return useraccountService.confirmUserAccount(modelAndView, confirmationToken);
	}
}
