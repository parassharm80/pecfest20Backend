package com.fest.pecfestBackend.service.impl;

import com.fest.pecfestBackend.dto.EditEventDTO;
import com.fest.pecfestBackend.dto.RegisterEventDTO;
import com.fest.pecfestBackend.entity.*;
import com.fest.pecfestBackend.enums.EventCountType;
import com.fest.pecfestBackend.enums.EventType;
import com.fest.pecfestBackend.repository.*;
import com.fest.pecfestBackend.response.GenericApiDataResponse;
import com.fest.pecfestBackend.response.GenericApiResponse;
import com.fest.pecfestBackend.service.IEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements IEventService {

    private static final Logger log = LoggerFactory.getLogger(IEventService.class);

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private EventUsersRepo eventUsersRepository;

    @Autowired
    private UserEventsRepo userEventsRepository;

    @Autowired
    private TeamRepo teamRepository;

    @Autowired
    private UserRepo userRepository;

    @Override
    public GenericApiResponse editEvent(EditEventDTO editEventDTO) {
        try {
            Event event = eventsRepository.findByEventName(editEventDTO.getOldEventName());
            if(Objects.nonNull(event)) {
                event.setEventName(editEventDTO.getNewEventName());
                event.setSchedule(LocalDateTime.of(editEventDTO.getEventDate(),editEventDTO.getEventTime()));
                event.setMinimumParticipantCount(editEventDTO.getMinimumParticipantCount());
                event.setMaximumParticipantCount(editEventDTO.getMaximumParticipantCount());
                event.setEventDescription(editEventDTO.getEventDescription());
                event.setMaxYearThatCanParticipate(editEventDTO.getMaxYearThatCanParticipate());
                eventsRepository.save(event);
                log.info("EDIT_EVENT: Event {} is edited successfully.",event.getEventName());
                return new GenericApiResponse(true,"Event edited successfully.");
            }
            log.info("EDIT_EVENT: Event {} is not present in the system.",editEventDTO.getOldEventName());
            return new GenericApiResponse(false,editEventDTO.getOldEventName()+" is not present in the system.");
        } catch (Exception e) {
            log.error("EDIT_EVENT: Error occurred while editing the event:{} with error {}",editEventDTO.getOldEventName(),e);
            return new GenericApiResponse(false,"Some error occurred while editing the event.");
        }
    }

    @Override
    public GenericApiResponse registerEvent(RegisterEventDTO registerEventDTO) {
        try {
            Event event = eventsRepository.findByEventName(registerEventDTO.getEventName());
            for (String userName : registerEventDTO.getUserNames()) {
                if (!checkValidityForEachUser(event, userName)) {
                    log.info("REGISTER_EVENT: {} user is not valid for registration of event {}", userName, registerEventDTO.getEventName());
                    return new GenericApiResponse(true, getAppropriateMessage(event));
                }
            }
            addUsersInEventToUsersMapping(event, registerEventDTO.getUserNames());
            addEventInUserToEventsMapping(event, registerEventDTO.getUserNames());
            addEventInEventToTeamMapping(event, registerEventDTO.getUserNames(), registerEventDTO.getTeamName());
            return new GenericApiResponse(true,"Registration Successful.");
        } catch (Exception e) {
            log.error("REGISTER_EVENT: Error {} occurred while registering for event: {} and usernames: {}",e,registerEventDTO.getEventName(),registerEventDTO.getUserNames());
            return new GenericApiResponse(false, "Some error occurred while registering for the Event");
        }
    }

    @Override
    public GenericApiDataResponse<Map<String, Map<String, List<Event>>>> getEventsData() {
        try {
            List<Event> events = eventsRepository.findAll();
            Map<EventType, List<Event>> eventTypeToEventMap = events.stream().collect(Collectors.groupingBy(Event::getEventType));
            Map<String, Map<String, List<Event>>> eventTypeToEventSubTypeToEventMap = new HashMap<>();
            eventTypeToEventMap.forEach((eventType, eventList) -> {
                Map<String, List<Event>> eventSubTypeToEventMap = eventList.stream().collect(Collectors.groupingBy(Event::getEventSubType));
                eventTypeToEventSubTypeToEventMap.put(eventType.toString(), eventSubTypeToEventMap);
            });
            return new GenericApiDataResponse<>(true, "Events Data Fetched Successfully.", eventTypeToEventSubTypeToEventMap);
        } catch (Exception e) {
            log.error("EVENTS_DATA Error occurred while fetching the events data with error: "+e);
            return new GenericApiDataResponse<>(false, "Failed to Fetch Data.", null);
        }
    }

    private boolean checkValidityForEachUser(Event event,String userName) {
        User user = userRepository.findByUserName(userName);
        EventUsers eventUsers = eventUsersRepository.findByEventId(event.getId());
        boolean isValidForRegistration = Objects.nonNull(user) && (Objects.isNull(eventUsers) || !eventUsers.getEventUsers().contains(userName));
        return isValidForRegistration;
    }

    private void addUsersInEventToUsersMapping(Event event,List<String> userNames) {
        EventUsers eventUsers = eventUsersRepository.findByEventId(event.getId());
        if(Objects.isNull(eventUsers)) {
            eventUsers = new EventUsers(event.getId(), new ArrayList<>());
        }
        List<String> eventUsersList = eventUsers.getEventUsers();
        eventUsersList.addAll(userNames);
        eventUsers.setEventUsers(eventUsersList);
        eventUsersRepository.save(eventUsers);
    }

    private void addEventInUserToEventsMapping(Event event, List<String> userNames) {
        for(String userName : userNames) {
            UserEvents userEvents = userEventsRepository.findByUserName(userName);
            if(Objects.isNull(userEvents)) {
                userEvents = new UserEvents(userName, new ArrayList<>());
            }
            List<Integer> eventIds = userEvents.getUserEvents();
            eventIds.add(event.getId());
            userEvents.setUserEvents(eventIds);
            userEventsRepository.save(userEvents);
        }
    }

    private void addEventInEventToTeamMapping(Event event, List<String> userNames, String teamName) {
        if(EventCountType.INDIVIDUAL.equals(event.getTeamOrIndividual())){
            return;
        }
        Team team = new Team(teamName, event.getId(), userNames);
        teamRepository.save(team);
    }

    private String getAppropriateMessage(Event event) {
        if(EventCountType.TEAM.equals(event.getTeamOrIndividual())) {
            return "All Users should be registered and minimum participant count is "+ event.getMinimumParticipantCount()+" and maximum participant count is "+event.getMaximumParticipantCount()+".";
        }
        return "Only Student upto " + event.getMaxYearThatCanParticipate()+" year can participate.";
    }
}
