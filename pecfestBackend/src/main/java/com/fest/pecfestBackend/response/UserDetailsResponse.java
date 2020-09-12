package com.fest.pecfestBackend.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserDetailsResponse {
    private String pecFestId;
    private String fullName;
    private String firstName;
    private String lastName;
    private Long id;
    private String email;
    private String collegeName;
    private String contactNo;
    private Long yearOfEducation;
    private String gender;
}
