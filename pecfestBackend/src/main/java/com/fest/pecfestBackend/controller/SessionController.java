package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.enums.Club;
import com.fest.pecfestBackend.response.SessionResponse;
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
    public SessionResponse verifySessionId(@RequestHeader(value = "session_id",required = false) String sessionId){
        User user=sessionService.verifySessionId(sessionId);
        if(Objects.isNull(user))
            return SessionResponse.builder().isAdmin(false).isLoggedIn(false).build();
        if(Objects.isNull(user.getCoordinatingClubName())||user.getCoordinatingClubName().equals(Club.EMPTY))
            return SessionResponse.builder().isAdmin(false).isLoggedIn(true).build();
        return SessionResponse.builder().isAdmin(true).isLoggedIn(true).build();
    }
}
