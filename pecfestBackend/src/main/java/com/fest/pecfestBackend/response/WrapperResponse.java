package com.fest.pecfestBackend.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

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

	@Builder.Default
	@JsonProperty(value = "http_status")
	private HttpStatus httpStatus=HttpStatus.OK;
}