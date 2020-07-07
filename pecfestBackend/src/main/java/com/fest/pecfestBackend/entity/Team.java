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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name ="Team")
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long teamId;

	@Column(nullable = false)
	private String teamName;

	@Column(nullable = false)
	private long eventId;	
		
	@Column
	private long[] studentId;

}
