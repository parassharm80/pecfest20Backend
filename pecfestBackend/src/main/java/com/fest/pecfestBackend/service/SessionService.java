package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SessionService {
    @Autowired
    UserRepo userRepo;
    public User verifySessionId(String sessionId){
        return userRepo.findBySessionId(sessionId);
    }
}
