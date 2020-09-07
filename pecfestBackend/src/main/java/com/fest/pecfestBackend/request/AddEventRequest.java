package com.fest.pecfestBackend.request;

import com.fest.pecfestBackend.enums.Club;
import com.fest.pecfestBackend.enums.EventCount;
import com.fest.pecfestBackend.enums.EventType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
@Getter
@Setter
public class AddEventRequest {

    private String eventName;
    private EventType eventType;
    private EventCount eventCount;
    private Club organizingClub;
    private String organizerContactNo;

    private Integer minNumberOfParticipants;

    private Integer maxNumberOfParticipants;
    private LocalDateTime eventStartDateAndTime;
    private LocalDateTime eventEndDateAndTime;
    private String eventDescription;
}
