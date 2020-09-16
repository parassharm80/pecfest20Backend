package com.fest.pecfestBackend.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fest.pecfestBackend.enums.Club;
import com.fest.pecfestBackend.enums.EventCount;
import com.fest.pecfestBackend.enums.EventType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="event")
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long eventID;
    @Column(nullable = false)
    private String eventName;
    private String eventBannerImageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventCount eventCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Club organizingClub;
    private String organizerContactNo;

    @Column(nullable = false)
    private Integer minNumberOfParticipants;

    @Column(nullable = false)
    private Integer maxNumberOfParticipants;

    private LocalDateTime eventStartDateAndTime;
    private LocalDateTime eventEndDateAndTime;

    private String eventDescription;

    private String prizeMoneyWorth;
    private String venue;
    private String rules;
    private String createdBy;
    private String updatedBy;
}
