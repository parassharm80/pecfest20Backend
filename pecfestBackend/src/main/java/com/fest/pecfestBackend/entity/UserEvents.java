package com.fest.pecfestBackend.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "UserEvents")
public class UserEvents implements Serializable {

	private static final long serialVersionUID = 1L;
   
	 @Id
	 private String id;
	 
	 private String userEvents;

	public List<String> getUserEvents() {
		return Arrays.asList(userEvents.split(","));
	}

	public void setUserEvents(List<String> userEvents) {
		this.userEvents = String.join(",", userEvents);
	}

}
