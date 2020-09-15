package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.request.EventRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import com.fest.pecfestBackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/club-wise") // api for events page
    public WrapperResponse getAllEventsByClub(){
        return eventService.getAllEventsByClub();
    }

    @GetMapping("/all")
    public WrapperResponse getAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/admin") //api for admin page
    public WrapperResponse getEventsForClubAdmins(@RequestHeader("session_id") String sessionId){
        return eventService.getEventsForClubAdmins(sessionId);
    }
    @PostMapping("/admin")
    public WrapperResponse addEvent(@RequestBody EventRequest addEventRequest,@RequestHeader("session_id") String sessionId){
        return eventService.addEvent(addEventRequest,sessionId);
    }
    @DeleteMapping("/admin/{event_id}")
    public WrapperResponse deleteEvent(@PathVariable("event_id") Long eventId,@RequestHeader("session_id") String sessionId){
        return eventService.deleteEvent(eventId,sessionId);
    }
    @PutMapping("/admin/{event_id}")
    public WrapperResponse editEvent(@PathVariable("event_id") Long eventId,@RequestBody EventRequest editEventRequest,
                                     @RequestHeader("session_id") String sessionId){
        return eventService.editEvent(eventId,editEventRequest,sessionId);
    }

    @GetMapping
    public WrapperResponse getEventsByOrganizingClubName(@RequestParam String clubName){
        return eventService.getEventsByOrganizingClubName(clubName);
    }
}
