package com.fest.pecfestBackend.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fest.pecfestBackend.entity.Event;
import com.fest.pecfestBackend.enums.Club;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EventListByClubNameResponse {
    private Club clubName;
    private List<Event> eventList;
}
