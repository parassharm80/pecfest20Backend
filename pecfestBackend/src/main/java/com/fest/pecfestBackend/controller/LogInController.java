package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.request.LogInRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LogInController {
    @Autowired
    private LogInService logInService;

   @PostMapping
    public WrapperResponse logInUser(@RequestBody LogInRequest logInRequest){
       return logInService.authenticateUser(logInRequest);
   }

}
