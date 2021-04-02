package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.request.TeamRegRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.EventRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/pecfest-registration")
public class EventRegistrationController {

    @Autowired
    private EventRegistrationService eventRegService;

    @PostMapping("/{event_id}/{team_name}")
    public WrapperResponse registerTeamForAnEvent(@PathVariable("event_id") Long eventId, @RequestBody TeamRegRequest teamRegRequest, @RequestHeader("session_id") String sessionId,
                                              @PathVariable(value = "team_name") String teamName){
        return eventRegService.registerTeamForAnEvent(eventId,teamRegRequest,teamName,sessionId);
    }
    @PostMapping("/{event_id}")
    public WrapperResponse registerIndividualForAnEvent(@PathVariable("event_id") Long eventId, @RequestHeader("session_id") String sessionId,@RequestBody TeamRegRequest teamRegRequest){
        return eventRegService.registerAnIndividual(eventId,sessionId,teamRegRequest);
    }
}
