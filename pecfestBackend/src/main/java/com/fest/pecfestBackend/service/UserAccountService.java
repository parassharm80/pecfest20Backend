package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.entity.Confirmation;
import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.repository.ConfirmationRepo;
import com.fest.pecfestBackend.repository.UserRepo;
import com.fest.pecfestBackend.request.UserSignUpRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.StandardCharsets;
import java.util.Objects;


@Service
public class UserAccountService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ConfirmationRepo confirmationRepo;
	
	@Autowired
	private EmailSenderService emailSenderService;

	@Value("${spring.mail.username}")
	private String mailUsername;
	@Value("${domain-name}")
	private String domainHost;

	public ModelAndView displayRegistration(ModelAndView modelAndView, User user) {
		modelAndView.addObject("user",user);
		modelAndView.setViewName("register");
		return modelAndView;
	}
	
	public WrapperResponse registerUser(UserSignUpRequest userSignUpRequest) {
		User existingUser = userRepo.findByEmail(userSignUpRequest.getEmail());
		if(Objects.isNull(existingUser)) {
			String hashedPassword= Hashing.sha512().hashString(userSignUpRequest.getPassword(), StandardCharsets.UTF_8).toString();
			User newUser=User.builder().email(userSignUpRequest.getEmail()).firstName(userSignUpRequest.getFirstName()).lastName(userSignUpRequest.getLastName())
					.gender(userSignUpRequest.getGender()).isVerified(false).otpForPasswordReset(null)
					.password(hashedPassword).sessionId(StringUtils.EMPTY).yearOfEducation(userSignUpRequest.getYearOfEducation())
					.build();
			userRepo.save(newUser);
			newUser.setPecFestId("PECFEST"+ newUser.getFirstName().charAt(0)+newUser.getLastName().charAt(0)+newUser.getId().toString());
			userRepo.save(newUser);
			Confirmation confirmation = new Confirmation(newUser);
			confirmationRepo.save(confirmation);
			emailSenderService.sendEmail(createEmailMessage(newUser.getPecFestId(), newUser.getEmail(),confirmation.getConfirmToken()));
			return WrapperResponse.builder().statusMessage("Check email for PECFEST ID and verification.").build();
		}

		return WrapperResponse.builder().statusMessage("EmailID already registered").build();
	}
	private SimpleMailMessage createEmailMessage(String pecFestId,String emailId,String confirmationToken) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(emailId);
		message.setFrom(mailUsername);
		message.setSubject("PECFEST ID and Email Verification");
		message.setText("Your PECFEST 2020 Id is: "+pecFestId+". This ID will be used for events' registration. "+
				"For emailVerification: Click here: "+domainHost+"?confirmation_token="+confirmationToken);
		return message;
	}
	
	@RequestMapping(value="/confirm", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam String confirmationToken) {
		Confirmation confirmation = confirmationRepo.findByConfirmToken(confirmationToken);
		if(confirmation!=null) {
			User user = userRepo.findByEmail(confirmation.getUser().getEmail());
			user.setVerified(true);
			userRepo.save(user);
			modelAndView.setViewName("Account Verified");
			
		}
		else {
			modelAndView.addObject("message","This link is invalid or broken!");
			modelAndView.setViewName("Error");
			
		}
		return modelAndView;
	}
	
}
