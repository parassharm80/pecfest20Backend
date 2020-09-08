package com.fest.pecfestBackend.repository;

import com.fest.pecfestBackend.entity.Event;
import com.fest.pecfestBackend.enums.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {
    List<Event> findByOrganizingClub(Club organizingClub);
    Event findByEventName(String eventName);
    boolean existsByEventID(Long eventId);
}
