package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.entity.Event;
import com.fest.pecfestBackend.enums.Club;
import com.fest.pecfestBackend.repository.EventRepo;
import com.fest.pecfestBackend.request.AddEventRequest;
import com.fest.pecfestBackend.response.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepo eventRepo;

    public WrapperResponse getEventsByOrganizingClubName(Club organizingClubName) {
        return WrapperResponse.builder().data(eventRepo.findByOrganizingClub(organizingClubName)).build();
    }

    public WrapperResponse addEvent(AddEventRequest addEventRequest) {
        Event event=eventRepo.findByEventName(addEventRequest.getEventName());
        if(Objects.isNull(event))
            return WrapperResponse.builder().data(getNewEvent(addEventRequest)).statusMessage("Event added successfully").build();
        else
            return WrapperResponse.builder().statusCode(HttpStatus.BAD_REQUEST.toString()).httpStatus(HttpStatus.BAD_REQUEST).statusMessage("The event name already exists!").build();
    }
    public WrapperResponse getAllEvents() {
        return WrapperResponse.builder().data(eventRepo.findAll()).build();
    }

    private Event getNewEvent(AddEventRequest addEventRequest) {
        return Event.builder().eventCount(addEventRequest.getEventCount()).eventDescription(addEventRequest.getEventDescription()).eventEndDateAndTime(addEventRequest.getEventEndDateAndTime())
                .organizingClub(addEventRequest.getOrganizingClub()).organizerContactNo(addEventRequest.getOrganizerContactNo())
                .minNumberOfParticipants(addEventRequest.getMinNumberOfParticipants()).maxNumberOfParticipants(addEventRequest.getMaxNumberOfParticipants()).eventStartDateAndTime(addEventRequest.getEventStartDateAndTime())
                .eventType(addEventRequest.getEventType()).build();
    }

    public WrapperResponse deleteEvent(Long eventId) {
        Optional<Event> event=eventRepo.findById(eventId);
        if(event.isPresent()) {
            eventRepo.deleteById(eventId);
            return WrapperResponse.builder().data(event.get()).statusMessage("Event deleted successfully").build();
        }
        else{
            return WrapperResponse.builder().statusMessage("No such event exists").httpStatus(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.toString()).build();
        }
    }
}
