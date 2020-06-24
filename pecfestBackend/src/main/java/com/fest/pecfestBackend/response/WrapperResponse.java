package com.fest.pecfestBackend.response;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
public class WrapperResponse<T> {
	
	private T data;
	
	@Builder.Default
	@JsonProperty(value = "status_code")
	private String statusCode = "SUCCESS";
	
	@Builder.Default
	@JsonProperty(value = "status_message")
	private String statusMessage = "SUCCESS";

	
}
