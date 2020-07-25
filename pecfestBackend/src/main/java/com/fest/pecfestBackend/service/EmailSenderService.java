package com.fest.pecfestBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

	private JavaMailSender javamailSender;
	
	@Autowired
	public EmailSenderService(JavaMailSender javaMailSender) {
		this.javamailSender= javaMailSender;
	}
	
	@Async
	public void sendEmail(SimpleMailMessage eMailMessage) {
		javamailSender.send(eMailMessage);
	}
}
