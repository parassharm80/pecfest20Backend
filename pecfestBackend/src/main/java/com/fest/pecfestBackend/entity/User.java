package com.fest.pecfestBackend.entity;

import com.fest.pecfestBackend.enums.Club;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
	
	@Column(nullable = false,unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	private String gender;
	
	@Column(nullable = false)
	private Long yearOfEducation;

	@Column(name="require_accommodation")
	private Boolean requireAccommodation=false;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Club coordinatingClubName=null;

	@Column
	private boolean isVerified; //to check whether the user is verified or not
	@Column
	private String sessionId;
	@Column
	private String otpForPasswordReset;
	@Column
	private String contactNo;
	@Column
	private String collegeName;
	public String getName(){
		return this.firstName+" "+this.lastName;
	}
}
