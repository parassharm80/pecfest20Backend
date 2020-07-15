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
public class EventUsers extends AbstractEntity<Integer> {

	private Integer eventId;
	
	private String userNames;

	public List<String> getEventUsers() {
		return Arrays.asList(userNames.split(","));
	}

	public void setEventUsers(List<String> eventUsers) {
		this.userNames = String.join(",", eventUsers);
	}

	public EventUsers(Integer eventId, List<String> userNames) {
		super();
		this.eventId = eventId;
		setEventUsers(userNames);
	}

}
