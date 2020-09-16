package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.enums.Club;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.EventRegistrationAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin/pecfest-registration")
public class EventRegistrationAdminController {
    @Autowired
    private EventRegistrationAdminService eventRegAdminService;

    @PostMapping("/{organizing_club}/{event_name}/{team_name}")
    public WrapperResponse registerTeamForAnEvent(@PathVariable("event_name") String eventName, @RequestBody List<String> pecFestIds, @RequestHeader("session_id") String sessionId,
                                                  @PathVariable(value = "team_name") String teamName,@PathVariable(value = "organizing_club") Club organizingClub){
        return eventRegAdminService.registerTeamForAnEvent(eventName,pecFestIds,teamName,sessionId,organizingClub);
    }
    @GetMapping
    public WrapperResponse getEventsRegistrationsData(@RequestHeader("session_id") String sessionId) {
        return eventRegAdminService.getEventsRegistrationsData(sessionId);
    }
}
