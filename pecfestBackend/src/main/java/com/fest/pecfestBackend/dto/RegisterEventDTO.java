package com.fest.pecfestBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterEventDTO {

    private String eventName;

    private String teamName;

    private List<String> userNames;
}
