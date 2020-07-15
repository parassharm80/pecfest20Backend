package com.fest.pecfestBackend.entity;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Entity;
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
@Table(name = "UserEvents")
public class UserEvents extends AbstractEntity<Integer> implements Serializable {

	private static final long serialVersionUID = 1L;

 	private String userName;

 	private String eventIds;

	public List<Integer> getUserEvents() {
		return Stream.of(eventIds.split(","))
				.map(Integer::parseInt)
				.collect(Collectors.toList());
	}

	public void setUserEvents(List<Integer> eventIds) {
		this.eventIds = eventIds.stream().map(String::valueOf).collect(Collectors.joining(","));
	}

	public UserEvents(String userName, List<Integer> eventIds) {
		this.userName = userName;
		setUserEvents(eventIds);
	}
}
