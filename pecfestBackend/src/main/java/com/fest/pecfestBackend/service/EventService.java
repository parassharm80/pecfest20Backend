package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.entity.Event;
import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.enums.Club;
import com.fest.pecfestBackend.enums.EventType;
import com.fest.pecfestBackend.repository.EventRepo;
import com.fest.pecfestBackend.request.EventRequest;
import com.fest.pecfestBackend.response.EventListByClubNameResponse;
import com.fest.pecfestBackend.response.EventListResponse;
import com.fest.pecfestBackend.response.TechnoCultEventResponse;
import com.fest.pecfestBackend.response.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private SessionService sessionService;

    @Cacheable("eventsByClubName")
    public WrapperResponse getEventsByOrganizingClubName(String organizingClubName) {
        if(Club.fromString(organizingClubName)==null)
            return WrapperResponse.builder().statusCode(HttpStatus.BAD_REQUEST.toString()).httpStatus(HttpStatus.BAD_REQUEST).statusMessage("No such club name exists!").build();
        return WrapperResponse.builder().data(eventRepo.findAllByOrganizingClub(Club.fromString(organizingClubName))).build();
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
                .organizingClub(Club.fromString(addEventRequest.getOrganizingClub())).organizerContactNo(addEventRequest.getOrganizerContactNo())
                .minNumberOfParticipants(addEventRequest.getMinNumberOfParticipants()).maxNumberOfParticipants(addEventRequest.getMaxNumberOfParticipants()).eventStartDateAndTime(addEventRequest.getEventStartDateAndTime())
                .eventType(addEventRequest.getEventType()).prizeMoneyWorth(addEventRequest.getPrizeMoneyWorth())
                .venue(addEventRequest.getVenue()).eventName(addEventRequest.getEventName()).rules(addEventRequest.getRules()).eventBannerImageUrl(addEventRequest.getEventBannerImageUrl())
                .build();
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
            oldEvent.setEventStartDateAndTime(Optional.ofNullable(editEventRequest.getEventStartDateAndTime()).orElse(oldEvent.getEventStartDateAndTime()));
            oldEvent.setVenue(Optional.ofNullable(editEventRequest.getVenue()).orElse(oldEvent.getVenue()));
            oldEvent.setOrganizerContactNo(Optional.ofNullable(editEventRequest.getOrganizerContactNo()).orElse(oldEvent.getOrganizerContactNo()));
            oldEvent.setOrganizingClub(Club.fromString(editEventRequest.getOrganizingClub()));
            oldEvent.setEventName(Optional.ofNullable(editEventRequest.getEventName()).orElse(oldEvent.getEventName()));
            oldEvent.setEventCount(editEventRequest.getEventCount());
            oldEvent.setMaxNumberOfParticipants(editEventRequest.getMaxNumberOfParticipants());
            oldEvent.setMinNumberOfParticipants(editEventRequest.getMinNumberOfParticipants());
            oldEvent.setEventType(editEventRequest.getEventType());
            eventRepo.save(oldEvent);
            return WrapperResponse.builder().data(oldEvent).statusMessage("Edited successfully").build();
        }
        else{
            return WrapperResponse.builder().statusMessage("No such event exists").httpStatus(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.toString()).build();
        }
    }

    @Cacheable("getAllEventsByClub")
    public WrapperResponse getAllEventsByClub()  {
        List<Event> eventList=eventRepo.findAll();
        List<Event> culturalEventList=eventList.parallelStream().filter(event->event.getEventType()== EventType.CULTURAL).collect(Collectors.toList());
        List<Event> technicalEventList=eventList.parallelStream().filter(event->event.getEventType()== EventType.TECHNICAL).collect(Collectors.toList());
        List<Event> lectureEventList=eventList.parallelStream().filter(event->event.getEventType()== EventType.LECTURE).collect(Collectors.toList());
        List<Event> workshopEventList=eventList.parallelStream().filter(event->event.getEventType()== EventType.WORKSHOP).collect(Collectors.toList());

        return WrapperResponse.builder().data(EventListResponse.builder().culturalEvent(getListByClubName(culturalEventList)).technicalEvent(getListByClubName(technicalEventList)).
                workshop(getListByClubName(workshopEventList)).lecture(getListByClubName(lectureEventList)).build()).build();
    }

    private List<EventListByClubNameResponse> getListByClubName(List<Event> eventList) {
        Map<String,List<Event>> map=new HashMap<>();
        List<EventListByClubNameResponse> eventListByClubName=new ArrayList<>();
        for(Event event:eventList) {
            if (!map.containsKey(event.getOrganizingClub().getClubName()))
                map.put(event.getOrganizingClub().getClubName(), new ArrayList<>());
            map.get(event.getOrganizingClub().getClubName()).add(event);
        }
        map.forEach((key,value)->{
            eventListByClubName.add(EventListByClubNameResponse.builder().clubName(key).eventList(value).build());
        });
        return eventListByClubName;
    }

    public WrapperResponse getEventsForClubAdmins(String sessionId) {
        User user=sessionService.verifySessionId(sessionId);
        if(!Optional.ofNullable(user).isPresent()||Objects.isNull(user.getCoordinatingClubName())||user.getCoordinatingClubName().equals(Club.EMPTY))
            return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("Not authorized").build();
        else{
            if(user.getCoordinatingClubName().equals(Club.ALL))// SuperAdmin
                return WrapperResponse.builder().data(eventRepo.findAll()).build();
            else
                return WrapperResponse.builder().data(eventRepo.findAllByOrganizingClub(user.getCoordinatingClubName())).build();
        }
    }
}
