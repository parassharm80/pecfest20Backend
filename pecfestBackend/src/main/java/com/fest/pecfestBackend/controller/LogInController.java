package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.request.LogInRequest;
import com.fest.pecfestBackend.request.ResetPasswordRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LogInController {
    @Autowired
    private LogInService logInService;

   @PostMapping(("/login"))
    public WrapperResponse logInUser(@RequestBody LogInRequest logInRequest){
       return logInService.logInUser(logInRequest);
   }
   @GetMapping("/forgot-password")
    public WrapperResponse forgotPassword(@RequestParam String emailId){
       return logInService.sendVerificationCode(emailId);
   }
   @PostMapping("/reset-password")
    public WrapperResponse resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
       return logInService.resetPassword(resetPasswordRequest);
   }
}
