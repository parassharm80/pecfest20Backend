package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.response.WrapperResponse;
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
    public WrapperResponse verifySessionId(@RequestHeader("session_id") String sessionId){
        return WrapperResponse.builder().data(!Objects.isNull(sessionService.verifySessionId(sessionId))).build();
    }
}
