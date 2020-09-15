package com.fest.pecfestBackend.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fest.pecfestBackend.enums.EventCount;
import com.fest.pecfestBackend.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
public class AddEventRequest {
    private String eventName;
    private String eventBannerImageUrl;
    private EventType eventType;

    private EventCount eventCount;
    private String organizingClub;
    private String organizerContactNo;

    private Integer minNumberOfParticipants;

    private Integer maxNumberOfParticipants;
    private LocalDateTime eventStartDateAndTime;
    private LocalDateTime eventEndDateAndTime;
    private String eventDescription;
    private String prizeMoneyWorth;
    private String venue;
    private String rules;
}
