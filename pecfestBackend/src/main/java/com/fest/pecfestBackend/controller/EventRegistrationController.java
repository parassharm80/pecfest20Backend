package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.EventRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pecfest-registration")
public class EventRegistrationController {

    @Autowired
    private EventRegistrationService eventRegService;
    @PostMapping("/{event_id}/{team_name}")
    public WrapperResponse registerForAnEvent(@PathVariable("event_id") Long eventId, @RequestBody List<String> pecFestIds,@RequestHeader("session_id") String sessionId,
    @PathVariable(value = "team_name") String teamName){
        return eventRegService.registerForAnEvent(eventId,pecFestIds,teamName,sessionId);
    }
}
