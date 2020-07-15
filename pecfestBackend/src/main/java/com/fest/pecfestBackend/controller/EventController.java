package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.dto.EditEventDTO;
import com.fest.pecfestBackend.dto.RegisterEventDTO;
import com.fest.pecfestBackend.entity.Event;
import com.fest.pecfestBackend.response.GenericApiDataResponse;
import com.fest.pecfestBackend.response.GenericApiResponse;
import com.fest.pecfestBackend.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/event")
public class EventController {

    @Autowired
    private IEventService eventService;

    @RequestMapping(value = "/editEvent", method = RequestMethod.POST)
    public GenericApiResponse editEvent(EditEventDTO eventDTO) {
        return eventService.editEvent(eventDTO);
    }

    @RequestMapping(value = "/registerEvent", method = RequestMethod.POST)
    public GenericApiResponse registerEvent(RegisterEventDTO eventDTO) {
        return eventService.registerEvent(eventDTO);
    }

    @RequestMapping(value = "/getAllEvents", method = RequestMethod.GET)
    public GenericApiDataResponse<Map<String, Map<String, List<Event>>>> getEventsData() {
        return eventService.getEventsData();
    }
}
