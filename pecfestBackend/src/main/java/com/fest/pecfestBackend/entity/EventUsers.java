package com.fest.pecfestBackend.entity;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name="EventUsers")
public class EventUsers {
	
	@Id
	private String id;
	
	private String eventUsers;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getEventUsers() {
		return Arrays.asList(eventUsers.split(","));
	}

	public void setEventUsers(List<String> eventUsers) {
		this.eventUsers = String.join(",", eventUsers);
	}

}
