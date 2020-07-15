package com.fest.pecfestBackend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@Table(name ="Team")
public class Team extends AbstractEntity<Integer> {

	@Column(nullable = false)
	private String teamName;

	@Column(nullable = false)
	private Integer eventId;
		
	@Column
	private String userNames;

	public List<String> getUserNames() {
		return Arrays.asList(userNames.split(","));
	}

	public void setUserNames(List<String> eventUsers) {
		this.userNames = String.join(",", eventUsers);
	}

	public Team(String teamName, Integer eventId, List<String> userNames) {
		super();
		this.teamName = teamName;
		this.eventId = eventId;
		setUserNames(userNames);
	}

}
