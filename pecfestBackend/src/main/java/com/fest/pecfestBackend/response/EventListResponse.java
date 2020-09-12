package com.fest.pecfestBackend.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EventListResponse {
    private List<EventListByClubNameResponse> culturalEvent;
    private List<EventListByClubNameResponse> technicalEvent;
    private List<EventListByClubNameResponse> lecture;
    private List<EventListByClubNameResponse> workshop;
}
