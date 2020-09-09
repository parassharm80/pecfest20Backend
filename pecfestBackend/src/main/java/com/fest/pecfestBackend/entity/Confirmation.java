package com.fest.pecfestBackend.entity;

//import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Confirmation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long tokenId;
	
	@Column(name="confirmation_token")
	private String confirmationToken;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false,name = "id")
	private User user;
	
	public Confirmation(User user) {
		this.user = user;
		createdDate = new Date();
		confirmationToken = UUID.randomUUID().toString();
	}
	
	
	

}
