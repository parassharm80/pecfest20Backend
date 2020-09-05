package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.repository.UserRepo;
import com.fest.pecfestBackend.request.LogInRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class LogInService {
    @Autowired
    UserRepo userRepo;

    public WrapperResponse authenticateUser(LogInRequest logInRequest){
        String hashedPassword=Hashing.sha512().hashString(logInRequest.getPassword(), StandardCharsets.UTF_8).toString();
        if(Objects.isNull(userRepo.findByEmailAndPassword(logInRequest.getEmailId(),hashedPassword)))
            return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("Oops wrong password/email!").build();
        else
            return WrapperResponse.builder().build();
    }
}
