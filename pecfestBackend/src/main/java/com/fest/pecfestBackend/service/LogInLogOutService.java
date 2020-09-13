package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.repository.UserRepo;
import com.fest.pecfestBackend.request.LogInRequest;
import com.fest.pecfestBackend.request.ResetPasswordRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Service
public class LogInLogOutService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmailSenderService emailSenderService;

    @Value("${domain-name}")
    private String domainHost;

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
        String randomOtpCode= UUID.randomUUID().toString()+user.getId();
        emailSenderService.sendEmail(createEmailMessage(randomOtpCode, emailId,user.getId()));
        user.setOtpForPasswordReset(randomOtpCode);
        userRepo.save(user);
        return WrapperResponse.builder().build();
    }

    private SimpleMailMessage createEmailMessage(String verificationCode,String emailId,Long userId) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(emailId);
        message.setFrom("registrations@pecfest.in");
        message.setSubject("Reset Password Instructions");
        message.setText("Click here to reset password : "+domainHost+"/reset-password/"+userId+"/"+verificationCode);
        return message;
    }

    public WrapperResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        User user=userRepo.findByIdAndOtpForPasswordReset(resetPasswordRequest.getUserId(),resetPasswordRequest.getVerificationCode());
        if(Objects.isNull(user)||Objects.isNull(user.getOtpForPasswordReset()))
            return WrapperResponse.builder().httpStatus(HttpStatus.BAD_REQUEST).statusMessage("Uhhh. Ohhh. Broken Link").build();
        else{
            String newHashedPassword=Hashing.sha512().hashString(resetPasswordRequest.getPassword(), StandardCharsets.UTF_8).toString();
            user.setPassword(newHashedPassword);
            userRepo.save(user);
        }

        return WrapperResponse.builder().statusMessage("Password reset successful").build();
    }

    public WrapperResponse logOutUser(String sessionId) {
        if(Objects.isNull(sessionId)||sessionId.length()<2)
            return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("Invalid sessionId").build();
        User user=userRepo.findBySessionId(sessionId);
        user.setSessionId(StringUtils.EMPTY);
        userRepo.save(user);
        return WrapperResponse.builder().statusMessage("Logged Out").data(StringUtils.EMPTY).build();
    }
}
