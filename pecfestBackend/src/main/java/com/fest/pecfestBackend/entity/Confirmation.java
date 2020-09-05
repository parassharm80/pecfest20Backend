package com.fest.pecfestBackend.entity;

//import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Confirmation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long tokenid;
	
	@Column(name="confirm_token")
	private String confirmToken;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false,name = "id")
	private User user;
	
	public Confirmation(User user) {
		this.user = user;
		createdDate = new Date();
		confirmToken = UUID.randomUUID().toString();
	}
	
	
	

}