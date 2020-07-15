package com.fest.pecfestBackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.fest.pecfestBackend.entity.EventUsers;

@Repository
@EnableJpaRepositories
public interface EventUsersRepo extends JpaRepository<EventUsers, Integer>, JpaSpecificationExecutor<EventUsers>{
	
	@Query("select c from EventUsers c where c.id is ?1")
	EventUsers findEventById(String id);

	EventUsers findByEventId(Integer eventId);
}
