package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.repository.TeamRepo;
import com.fest.pecfestBackend.repository.UserRepo;
import com.fest.pecfestBackend.response.WrapperResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EventRegistrationService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    TeamRepo teamRepo;
    @Autowired
    private SessionService sessionService;
    public WrapperResponse registerForAnEvent(Long eventId, List<String> pecFestIds, String teamName, String sessionId) {
        User user=sessionService.verifySessionId(sessionId);
        if(Objects.isNull(user)) {
            return WrapperResponse.builder().httpStatus(HttpStatus.FORBIDDEN).statusMessage("Invalid sessionId.Log in again").build();
        }
        else{
           String inValidPecFestIds=pecFestIds.parallelStream().filter(pecFestId->!userRepo.existsByPecFestId(pecFestId)).collect(Collectors.joining(", "));
           if(StringUtils.isAllEmpty(inValidPecFestIds))
           {
                if()
           }
           else{
               return WrapperResponse.builder().httpStatus(HttpStatus.BAD_REQUEST).statusMessage("Invalid PECFEST ids: "+inValidPecFestIds).build();
           }
        }

    }
}