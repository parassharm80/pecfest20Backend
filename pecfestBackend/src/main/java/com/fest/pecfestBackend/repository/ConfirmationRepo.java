package com.fest.pecfestBackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fest.pecfestBackend.entity.Confirmation;

@Repository
public interface ConfirmationRepo extends CrudRepository<Confirmation, String> {
	Confirmation findByConfirmationToken(String confirmationToken);
}
