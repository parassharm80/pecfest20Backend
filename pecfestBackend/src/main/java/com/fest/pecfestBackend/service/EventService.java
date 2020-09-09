package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.entity.Event;
import com.fest.pecfestBackend.enums.Club;
import com.fest.pecfestBackend.enums.EventType;
import com.fest.pecfestBackend.repository.EventRepo;
import com.fest.pecfestBackend.request.EventRequest;
import com.fest.pecfestBackend.response.TechnoCultEventResponse;
import com.fest.pecfestBackend.response.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepo eventRepo;

    @Cacheable("eventsByClubName")
    public WrapperResponse getEventsByOrganizingClubName(Club organizingClubName) {
        return WrapperResponse.builder().data(eventRepo.findByOrganizingClub(organizingClubName)).build();
    }

    public WrapperResponse addEvent(EventRequest addEventRequest) {
        Event event = eventRepo.findByEventName(addEventRequest.getEventName());
        if (Objects.isNull(event)) {
            Event newEvent = getNewEvent(addEventRequest);
            eventRepo.save(newEvent);
            return WrapperResponse.builder().data(newEvent).statusMessage("Event added successfully").build();
        } else {
            return WrapperResponse.builder().statusCode(HttpStatus.BAD_REQUEST.toString()).httpStatus(HttpStatus.BAD_REQUEST).statusMessage("The event name already exists!").build();
        }
    }
    @Cacheable("eventList")
    public WrapperResponse getAllEvents() {
        List<Event> eventList=eventRepo.findAll();
        List<Event> culturalEventList=eventList.parallelStream().filter(event->event.getEventType()== EventType.CULTURAL).collect(Collectors.toList());
        List<Event> technicalEventList=eventList.parallelStream().filter(event->event.getEventType()== EventType.TECHNICAL).collect(Collectors.toList());
        List<Event> lectureEventList=eventList.parallelStream().filter(event->event.getEventType()== EventType.LECTURE).collect(Collectors.toList());
        List<Event> workshopEventList=eventList.parallelStream().filter(event->event.getEventType()== EventType.WORKSHOP).collect(Collectors.toList());
        return WrapperResponse.builder().data(TechnoCultEventResponse.builder().culturalEvent(culturalEventList).technicalEvent(technicalEventList).
                workshop(workshopEventList).lecture(lectureEventList).build()).build();
    }

    private Event getNewEvent(EventRequest addEventRequest) {
        return Event.builder().eventCount(addEventRequest.getEventCount()).eventDescription(addEventRequest.getEventDescription()).eventEndDateAndTime(addEventRequest.getEventEndDateAndTime())
                .organizingClub(addEventRequest.getOrganizingClub()).organizerContactNo(addEventRequest.getOrganizerContactNo())
                .minNumberOfParticipants(addEventRequest.getMinNumberOfParticipants()).maxNumberOfParticipants(addEventRequest.getMaxNumberOfParticipants()).eventStartDateAndTime(addEventRequest.getEventStartDateAndTime())
                .eventType(addEventRequest.getEventType()).prizeMoneyWorth(addEventRequest.getPrizeMoneyWorth())
                .venue(addEventRequest.getVenue()).eventName(addEventRequest.getEventName()).build();
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

    public WrapperResponse editEvent(Long eventId, EventRequest editEventRequest) {
        Optional<Event> oldEventOptional=eventRepo.findById(eventId);
        if(oldEventOptional.isPresent()){
            Event oldEvent=oldEventOptional.get();

            oldEvent.setEventDescription(Optional.ofNullable(editEventRequest.getEventDescription()).orElse(oldEvent.getEventDescription()));
            oldEvent.setEventEndDateAndTime(Optional.ofNullable(editEventRequest.getEventEndDateAndTime()).orElse(oldEvent.getEventEndDateAndTime()));
            oldEvent.setEventEndDateAndTime(Optional.ofNullable(editEventRequest.getEventStartDateAndTime()).orElse(oldEvent.getEventStartDateAndTime()));
            oldEvent.setVenue(Optional.ofNullable(editEventRequest.getVenue()).orElse(oldEvent.getVenue()));
            oldEvent.setOrganizerContactNo(Optional.ofNullable(editEventRequest.getOrganizerContactNo()).orElse(oldEvent.getOrganizerContactNo()));
            eventRepo.save(oldEvent);
            return WrapperResponse.builder().data(oldEvent).statusMessage("Edited successfully").build();
        }
        else{
            return WrapperResponse.builder().statusMessage("No such event exists").httpStatus(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.toString()).build();
        }
    }
}
