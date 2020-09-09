package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.repository.UserRepo;
import com.fest.pecfestBackend.request.LogInRequest;
import com.fest.pecfestBackend.request.ResetPasswordRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;

@Service
public class LogInLogOutService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmailSenderService emailSenderService;

    public WrapperResponse logInUser(LogInRequest logInRequest){
        String hashedPassword=Hashing.sha512().hashString(logInRequest.getPassword(), StandardCharsets.UTF_8).toString();
        User user=userRepo.findByEmailAndPassword(logInRequest.getEmailId(),hashedPassword);
        if(Objects.isNull(user)) {
            return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("Oops wrong password/email!").build();
        }
        else if(!user.isVerified()){
            return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("Your mail has not been verified yet.Check your past emails").build();
        }
        else {
            Random rand = new Random();
            Integer randomNumber= rand.nextInt(10000)+10;
            String randomString=user.getEmail()+randomNumber.toString();
            String sessionId= Hashing.sha1().hashString(randomString, StandardCharsets.UTF_8).toString();
            user.setSessionId(sessionId);
            userRepo.save(user);
            return WrapperResponse.builder().data(sessionId).build();
        }
    }
    public WrapperResponse sendVerificationCode(String emailId) {
        User user=userRepo.findByEmail(emailId);
        if(Objects.isNull(user)||!user.isVerified())
            return WrapperResponse.builder().httpStatus(HttpStatus.BAD_REQUEST).statusMessage("You have not been registered/verified yet").build();
        Random rand = new Random();
        int verificationCode = rand.nextInt(900000) + 100000;
        emailSenderService.sendEmail(createEmailMessage(verificationCode, emailId));
        user.setOtpForPasswordReset((long) verificationCode);
        return WrapperResponse.builder().build();
    }

    private SimpleMailMessage createEmailMessage(int verificationCode,String emailId) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(emailId);
        message.setFrom("registrations@pecfest.in");
        message.setSubject("Reset Password Instructions");
        message.setText("The verification code for resetting the password is: "+verificationCode);
        return message;
    }

    public WrapperResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        User user=userRepo.findByEmailAndOtpForPasswordReset(resetPasswordRequest.getEmailId(),resetPasswordRequest.getVerificationCode());
        if(Objects.isNull(user)||Objects.isNull(user.getOtpForPasswordReset()))
            return WrapperResponse.builder().httpStatus(HttpStatus.BAD_REQUEST).statusMessage("Wrong verification Code").build();
        else{
            String newHashedPassword=Hashing.sha512().hashString(resetPasswordRequest.getPassword(), StandardCharsets.UTF_8).toString();
            user.setPassword(newHashedPassword);
            user.setOtpForPasswordReset(null);
        }

        return WrapperResponse.builder().statusMessage("Password reset successful").build();
    }

    public WrapperResponse logOutUser(String sessionId) {
        User user=userRepo.findBySessionId(sessionId);
        user.setSessionId(StringUtils.EMPTY);
        return WrapperResponse.builder().statusMessage("Logged Out").data(StringUtils.EMPTY).build();
    }
}