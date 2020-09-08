package com.fest.pecfestBackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="Users",indexes = {@Index(unique = true,columnList = "pec_fest_id",name = "pecFestIdIndex")})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "pec_fest_id")
	private String pecFestId;
	
	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	private String gender;
	
	@Column(nullable = false)
	private Long yearOfEducation;

	@Column(name="require_accommodation")
	private Boolean requireAccommodation=false;
	
	@Column
	private Long preference= (long) 0;
	
	@Column
	private Long teamId= (long) 0;
	
	@Column
	private boolean isEnabled; //to check whether the user is verified or not
	@Column
	private String sessionId;

}
