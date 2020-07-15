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

import java.util.List;

@Entity
@Getter
@Setter
@Table(name="Users")
public class User extends AbstractEntity<Integer> {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String userName;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	private String gender;
	
	@Column(nullable = false)
	private Long yearOfEducation;

	@Column(name="accommodation")
	private Boolean requireAccommodation=false;
	
	@Column
	private Long preference= (long) 0;
	
	@Column
	private List<Long> teamId;
	
}
