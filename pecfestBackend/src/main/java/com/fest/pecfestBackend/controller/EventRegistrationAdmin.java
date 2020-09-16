package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.EventRegistrationAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin/pecfest-registration")
public class EventRegistrationAdmin {
    @Autowired
    private EventRegistrationAdminService eventRegAdminService;

    @PostMapping("/{event_id}/{team_name}")
    public WrapperResponse registerTeamForAnEvent(@PathVariable("event_id") Long eventId, @RequestBody List<String> pecFestIds, @RequestHeader("session_id") String sessionId,
                                                  @PathVariable(value = "team_name") String teamName){
        return eventRegAdminService.registerTeamForAnEvent(eventId,pecFestIds,teamName,sessionId);
    }
}
