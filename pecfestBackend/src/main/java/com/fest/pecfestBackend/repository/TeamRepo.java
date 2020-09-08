package com.fest.pecfestBackend.repository;

import com.fest.pecfestBackend.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepo extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team>{ 
    boolean existsByTeamName(String teamName);
}
