package com.fest.pecfestBackend.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@NoArgsConstructor
public class UserSignUpRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private Boolean requireAccommodation;
    @Email
    private String email;
    @NotBlank
    private String password;
    private String gender;
    private Long yearOfEducation;

}
