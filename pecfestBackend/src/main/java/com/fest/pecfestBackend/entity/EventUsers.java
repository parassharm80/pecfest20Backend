package com.fest.pecfestBackend.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
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
