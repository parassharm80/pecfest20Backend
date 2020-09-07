package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.enums.Club;
import com.fest.pecfestBackend.request.AddEventRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    private EventService eventService;
    @GetMapping
    public WrapperResponse getEventsByOrganizingClubName(@RequestParam Club clubName){
        return eventService.getEventsByOrganizingClubName(clubName);
    }
    @GetMapping("/all")
    public WrapperResponse getAllEvents(){
        return eventService.getAllEvents();
    }
    @PostMapping
    public WrapperResponse addEvent(@RequestBody AddEventRequest addEventRequest){
        return eventService.addEvent(addEventRequest);
    }
    @DeleteMapping("/{event_id}")
    public WrapperResponse editEvent(@PathVariable("event_id") Long eventId){
        return eventService.deleteEvent(eventId);
    }
}
