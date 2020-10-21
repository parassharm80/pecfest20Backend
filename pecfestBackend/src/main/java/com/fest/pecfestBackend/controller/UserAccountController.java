package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.captcha.RecaptchaService;
import com.fest.pecfestBackend.request.UserSignUpRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;


@CrossOrigin
@RestController
@RequestMapping("/register")
public class UserAccountController {

    @Autowired
    private RecaptchaService captchaService;
    
	@Autowired
	private UserAccountService useraccountService;

	@PostMapping
	public WrapperResponse registerUser(@RequestBody @Valid UserSignUpRequest userSignUpRequest,
			@RequestParam(name="g-recaptcha-response") String recaptchaResponse,
			  HttpServletRequest request) {
		  String ip = request.getRemoteAddr();
		  String captchaVerifyMessage = 
		      captchaService.verifyRecaptcha(ip, recaptchaResponse);
		 
		  if ( StringUtils.isNotEmpty(captchaVerifyMessage)) {
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", captchaVerifyMessage);
		    return WrapperResponse.builder().statusMessage("Invalid Captcha").build();
		  }
		return useraccountService.registerUser(userSignUpRequest);
	}
	
	@GetMapping("/verify")
	public WrapperResponse confirmUserAccount(@RequestParam("confirmation_token") String confirmationToken) {
		return useraccountService.confirmUserAccount(confirmationToken);
	}
}
