package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.entity.Event;
import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.enums.Club;
import com.fest.pecfestBackend.repository.EventRepo;
import com.fest.pecfestBackend.response.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EventRegistrationAdminService {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private EventRepo eventRepo;

    public WrapperResponse registerTeamForAnEvent(Long eventId, List<String> pecFestIds, String teamName, String sessionId) {
           User user=sessionService.verifySessionId(sessionId);
        if(!Optional.ofNullable(user).isPresent()|| Objects.isNull(user.getCoordinatingClubName())||user.getCoordinatingClubName().equals(Club.EMPTY))
            return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("Not authorized").build();
        Optional<Event> eventOptional=eventRepo.findById(eventId);
        if(eventOptional.isPresent()){
            Event event=eventOptional.get();
        }
        else
            return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("No such event exists").build();
    }
}
