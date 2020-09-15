package com.fest.pecfestBackend.repository;

import com.fest.pecfestBackend.entity.Event;
import com.fest.pecfestBackend.enums.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {
    Event findByEventName(String eventName);
    List<Event> findAllByOrganizingClub(Club organizingClub);
    List<Event> findAllByEventNameAndOrganizingClub(String eventName,Club organizingClub);
}
