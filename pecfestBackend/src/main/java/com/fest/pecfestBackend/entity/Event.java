package com.fest.pecfestBackend.entity;

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
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long eventID;
    @Column(nullable = false)
    private String eventName;
    @Column(nullable = false)
    private EventType eventType;
    @Column(nullable = false)
    private EventCount eventCount;
    private Club organizingClub;
    private String organizerContactNo;
    @Column(nullable = false)
    private Integer minNumberOfParticipants;
    @Column(nullable = false)
    private Integer maxNumberOfParticipants;
    private LocalDateTime eventStartDateAndTime;
    private LocalDateTime eventEndDateAndTime;
    private String eventDescription;
}
