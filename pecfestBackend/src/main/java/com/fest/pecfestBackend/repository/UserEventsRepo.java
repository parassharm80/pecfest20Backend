package com.fest.pecfestBackend.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fest.pecfestBackend.entity.UserEvents;

@Repository
@EnableJpaRepositories 
public interface UserEventsRepo extends JpaRepository<UserEvents, Long>, JpaSpecificationExecutor<UserEvents>{
	
	@Query("select c from UserEvents c where c.id is ?1")
    UserEvents findByUserId(String id);

}
