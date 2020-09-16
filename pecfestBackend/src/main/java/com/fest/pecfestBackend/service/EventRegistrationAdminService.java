package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.entity.Event;
import com.fest.pecfestBackend.entity.Team;
import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.enums.Club;
import com.fest.pecfestBackend.repository.EventRepo;
import com.fest.pecfestBackend.repository.TeamRepo;
import com.fest.pecfestBackend.response.EventsRegsDataResponse;
import com.fest.pecfestBackend.response.WrapperResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventRegistrationAdminService {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private EventRepo eventRepo;
    @Autowired EventRegistrationService eventRegService;
    @Autowired
    private TeamRepo teamRepo;

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

    public WrapperResponse getEventsRegistrationsData(String sessionId,String eventName) {
        User user=sessionService.verifySessionId(sessionId);
        if(!Optional.ofNullable(user).isPresent()|| Objects.isNull(user.getCoordinatingClubName())||user.getCoordinatingClubName().equals(Club.EMPTY))
            return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("Not authorized").build();
        Club coordinatingClub=user.getCoordinatingClubName();
        List<Event> eventList;
        if(coordinatingClub.equals(Club.ALL))
            eventList= eventRepo.findAllByEventName(eventName);
        else
            eventList= eventRepo.findAllByEventNameAndOrganizingClub(eventName,coordinatingClub);

        if(CollectionUtils.isNotEmpty(eventList))
            return WrapperResponse.builder().data(getEventsRegs(eventList)).build();
        else
            return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("No such event name exists. Check for spelling mistakes").build();
    }

    private List<EventsRegsDataResponse> getEventsRegs(List<Event> eventList) {
        List<EventsRegsDataResponse> regsDataResponses=new ArrayList<>();
        List<Team> teams=teamRepo.findAllByEventId(eventList.parallelStream().map(Event::getEventID).collect(Collectors.toList()));
        for(Event event:eventList)
        for(Team team:teams)
            regsDataResponses.add(EventsRegsDataResponse.builder().eventName(event.getEventName()).teamName(team.getTeamName())
            .leaderPecFestId(team.getLeaderPecFestId()).organizingClub(event.getOrganizingClub()).teamId(team.getTeamId()).memberPecFestIdList(StringUtils.join(team.getMemberPecFestIdList(), ',')).build());

        return regsDataResponses;
    }
}

