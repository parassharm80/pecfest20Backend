package com.fest.pecfestBackend.repository;

import com.fest.pecfestBackend.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepo extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team>{ 
    boolean existsByTeamNameAndEventId(String teamName,Long eventId);
    List<Team> findAllByEventId(Long eventId);
    List<Team> findAllByEventId(List<Long> eventIdList);
}
