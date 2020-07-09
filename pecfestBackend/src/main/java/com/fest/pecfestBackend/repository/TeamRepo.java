package com.fest.pecfestBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.fest.pecfestBackend.entity.Team;
import com.fest.pecfestBackend.entity.User;

@Repository
public interface TeamRepo extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team>{ 

}
