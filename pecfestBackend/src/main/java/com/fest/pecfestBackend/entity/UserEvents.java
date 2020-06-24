package com.fest.pecfestBackend.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UserEvents")
public class UserEvents implements Serializable {

	private static final long serialVersionUID = 1L;
   
	 @Id
	 private String id;
	 
	 private String userEvents;
	 
	 public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getUserEvents() {
		return Arrays.asList(userEvents.split(","));
	}

	public void setUserEvents(List<String> userEvents) {
		this.userEvents = String.join(",", userEvents);
	}

}
