package com.fest.pecfestBackend.repository;

import com.fest.pecfestBackend.entity.Event;
import com.fest.pecfestBackend.enums.Club;
import com.fest.pecfestBackend.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {
    List<Event> findByOrganizingClub(Club organizingClub);
    List<Event> findByEventType(EventType eventType);
    Event findByEventName(String eventName);
}
