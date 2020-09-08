package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.request.LogInRequest;
import com.fest.pecfestBackend.request.ResetPasswordRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.LogInLogOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LogInLogOutController {
    @Autowired
    private LogInLogOutService logInLogOutService;

   @PostMapping(("/login"))
    public WrapperResponse logInUser(@RequestBody LogInRequest logInRequest){
       return logInLogOutService.logInUser(logInRequest);
   }
   @GetMapping("/generate-verification-code")
    public WrapperResponse forgotPassword(@RequestParam String emailId){
       return logInLogOutService.sendVerificationCode(emailId);
   }
   @PostMapping("/reset-password")
    public WrapperResponse resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
       return logInLogOutService.resetPassword(resetPasswordRequest);
   }
   @DeleteMapping("/logout")
    public WrapperResponse logOutUser(@RequestHeader("session_id") String sessionId){
       return logInLogOutService.logOutUser(sessionId);
   }
}
