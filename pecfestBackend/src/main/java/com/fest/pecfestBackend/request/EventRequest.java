package com.fest.pecfestBackend.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fest.pecfestBackend.enums.Club;
import com.fest.pecfestBackend.enums.EventCount;
import com.fest.pecfestBackend.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    @NotBlank
    private String eventName;
    @NotNull
    private EventType eventType;
    @NotNull
    private EventCount eventCount;
    private Club organizingClub;
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
