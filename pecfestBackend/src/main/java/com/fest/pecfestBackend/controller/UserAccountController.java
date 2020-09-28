package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.request.UserSignUpRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/register")
public class UserAccountController {

	@Autowired
	private UserAccountService useraccountService;

	@PostMapping
	public WrapperResponse registerUser(@RequestBody @Valid UserSignUpRequest userSignUpRequest) {
		return useraccountService.registerUser(userSignUpRequest);
	}
	
	@GetMapping("/verify")
	public WrapperResponse confirmUserAccount(@RequestParam("confirmation_token") String confirmationToken) {
		return useraccountService.confirmUserAccount(confirmationToken);
	}
}
