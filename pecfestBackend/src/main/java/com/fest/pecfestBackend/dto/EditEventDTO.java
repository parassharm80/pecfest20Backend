package com.fest.pecfestBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditEventDTO {

    private String oldEventName;

    private String newEventName;

    private LocalDate eventDate;

    private LocalTime eventTime;

    private Integer minimumParticipantCount;

    private Integer maximumParticipantCount;

    private String eventDescription;

    private Integer maxYearThatCanParticipate;
}
