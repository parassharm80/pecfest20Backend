package com.fest.pecfestBackend.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamRegRequest {
	
	@NotBlank
	private List<String> pecfestIdList;
	@NotBlank
	private String contentLink;
}
