package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.response.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventRegistrationAdminService {

    @Autowired
    private SessionService sessionService;

    public WrapperResponse registerTeamForAnEvent(Long eventId, List<String> pecFestIds, String teamName, String sessionId) {
           User user=sessionService.verifySessionId(sessionId);
    }
}
