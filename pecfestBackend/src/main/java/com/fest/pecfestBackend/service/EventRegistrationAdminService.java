package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.entity.Event;
import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.enums.Club;
import com.fest.pecfestBackend.repository.EventRepo;
import com.fest.pecfestBackend.response.WrapperResponse;
import org.apache.commons.collections4.CollectionUtils;
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
    @Autowired EventRegistrationService eventRegService;

    public WrapperResponse registerTeamForAnEvent(String eventName, List<String> pecFestIds, String teamName, String sessionId,Club organizingClub) {

           User user=sessionService.verifySessionId(sessionId);
        if(!Optional.ofNullable(user).isPresent()|| Objects.isNull(user.getCoordinatingClubName())||user.getCoordinatingClubName().equals(Club.EMPTY))
            return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("Not authorized").build();
        List<Event> eventList= eventRepo.findAllByEventNameAndOrganizingClub(eventName,organizingClub);
        if(CollectionUtils.isNotEmpty(eventList)){
            Event event=eventList.get(0);
            if(event.getOrganizingClub().equals(Club.ALL)||event.getOrganizingClub().equals(user.getCoordinatingClubName())){
                    return eventRegService.registerTeamForAnEvent(event.getEventID(),pecFestIds,teamName,sessionId);
            }
            else
                return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("Not authorized to modify "+event.getOrganizingClub()+" club's registrations").build();
        }
        else
            return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("No such event exists").build();
    }
}
