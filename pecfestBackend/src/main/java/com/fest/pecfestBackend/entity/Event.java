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
    @Column (length = 2000)
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
    @Column (length = 2000)
    private String organizerContactNo;

    @Column(nullable = false)
    private Integer minNumberOfParticipants;

    @Column(nullable = false)
    private Integer maxNumberOfParticipants;

    private LocalDateTime eventStartDateAndTime;
    private LocalDateTime eventEndDateAndTime;
    @Column (length = 6000)
    private String eventDescription;
    @Column (length = 6000)
    private String prizeMoneyWorth;
    @Column (length = 6000)
    private String venue;
    @Column (length = 6000)
    private String rules;
    private String createdBy;
    private String updatedBy;
}
