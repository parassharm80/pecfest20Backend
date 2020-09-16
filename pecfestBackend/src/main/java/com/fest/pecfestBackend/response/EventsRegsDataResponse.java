package com.fest.pecfestBackend.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fest.pecfestBackend.enums.Club;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EventsRegsDataResponse {
    private long teamId;
    private String teamName;
    private String leaderPecFestId;
    private String memberPecFestIdList;
    private String eventName;
    private Club organizingClub;
}
