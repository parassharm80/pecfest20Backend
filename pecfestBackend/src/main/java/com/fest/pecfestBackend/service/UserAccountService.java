package com.fest.pecfestBackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fest.pecfestBackend.entity.Confirmation;
import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.repository.ConfirmationRepo;
import com.fest.pecfestBackend.repository.UserRepo;


@Service
public class UserAccountService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ConfirmationRepo confirmationRepo;
	
	@Autowired
	private EmailSenderService emailSenderService;

	public ModelAndView displayRegistration(ModelAndView modelAndView, User user) {
		modelAndView.addObject("user",user);
		modelAndView.setViewName("register");
		return modelAndView;
	}
	
	public ModelAndView registerUser(ModelAndView modelAndView, User user) {
		User existingUser = userRepo.findByEmail(user.getEmail());
		if(existingUser==null) {
			modelAndView.addObject("message","This email already exists!");
			modelAndView.setViewName("Error");
		}
		else {
			userRepo.save(user);
			Confirmation confirmation = new Confirmation(user);
			confirmationRepo.save(confirmation);
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmail());
			mailMessage.setSubject("PECFEST Registration");
			mailMessage.setFrom("");
			mailMessage.setText("");
			emailSenderService.sendEmail(mailMessage);
			modelAndView.addObject("emailId",user.getEmail());
			modelAndView.setViewName("registrationSuccessful");
			
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/confirm", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam String confirmationToken) {
		Confirmation confirmation = confirmationRepo.findByConfirmToken(confirmationToken);
		if(confirmation!=null) {
			User user = userRepo.findByEmail(confirmation.getUser().getEmail());
			user.setEnabled(true);
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
