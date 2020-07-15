package com.fest.pecfestBackend.entity;

import com.fest.pecfestBackend.enums.EventCountType;
import com.fest.pecfestBackend.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "event")
public class Event extends AbstractEntity<Integer> {

    private String eventName;

    private EventCountType teamOrIndividual;

    private EventType eventType;

    private String eventSubType;

    private LocalDateTime schedule;

    private Integer minimumParticipantCount;

    private Integer maximumParticipantCount;

    private String eventDescription;

    private boolean isRegistrationRequired;

    private Integer maxYearThatCanParticipate;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> editedBy;

}
