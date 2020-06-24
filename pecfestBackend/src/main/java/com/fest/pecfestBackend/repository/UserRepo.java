package com.fest.pecfestBackend.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fest.pecfestBackend.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{
	

}
