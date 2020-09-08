package com.fest.pecfestBackend.repository;

import com.fest.pecfestBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{

	User findByEmail(String email);
	User findByEmailAndPassword(String email,String password);
	List<User> findByRequireAccommodationTrue();
	User findBySessionId(String sessionId);
	boolean existsByVerifiedAndPecFestId(boolean isVerified,String pecFestId);
}
