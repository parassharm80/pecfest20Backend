package com.fest.pecfestBackend.entity;


import java.util.Arrays;
import java.util.List;

import javax.persistence.*;

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
	@Column(nullable = false)
	private String id;

	@Column(nullable = false)
	private String eventUsers;

	@Column(nullable = false)
	private int isTeam;

	public List<String> getEventUsers() {
		return Arrays.asList(eventUsers.split(","));
	}

	public void setEventUsers(List<String> eventUsers) {
		this.eventUsers = String.join(",", eventUsers);
	}

}
