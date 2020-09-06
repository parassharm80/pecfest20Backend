package com.fest.pecfestBackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="Users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	private String gender;
	
	@Column(nullable = false)
	private Long yearofeducation;

	@Column(name="accommodation")
	private Boolean requireAccommodation=false;
	
	@Column
	private Long preference= (long) 0;
	
	@Column
	private Long teamId= (long) 0;
	
	@Column
	private boolean isEnabled;

}
