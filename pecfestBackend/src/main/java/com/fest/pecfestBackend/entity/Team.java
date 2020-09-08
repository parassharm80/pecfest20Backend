package com.fest.pecfestBackend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

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

	@Column(nullable = false,unique = true)
	private String teamName;

	@Column(nullable = false)
	private long eventId;

	@Column(nullable = false)
	private String leaderPecFestId;
		
	@Column
	private String memberPecFestIdList;

	public List<String> getMemberPecFestIdList(){
		return Arrays.asList(memberPecFestIdList.split(","));
	}
	public void setMemberPecFestIdList(List<String> memberPecFestIdList){
		this.memberPecFestIdList = String.join(",", memberPecFestIdList);
	}

}
