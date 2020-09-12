package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;
    @GetMapping("/verify")
    public boolean verifySessionId(@RequestHeader(value = "session_id",required = false) String sessionId){
        if(Objects.isNull(sessionId)||sessionId.length()<2)
            return false;
        return !Objects.isNull(sessionService.verifySessionId(sessionId));
    }
}
