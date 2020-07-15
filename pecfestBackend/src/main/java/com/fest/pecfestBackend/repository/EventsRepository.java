package com.fest.pecfestBackend.repository;

import com.fest.pecfestBackend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventsRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {

    Optional<Event> findById(Integer id);

    Event findByEventName(String eventName);

}
