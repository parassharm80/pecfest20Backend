package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.request.LogInRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import org.springframework.stereotype.Service;

@Service
public class LogInService {
    public WrapperResponse authenticateUser(LogInRequest logInRequest){
        return WrapperResponse.builder().build();
    }
}
