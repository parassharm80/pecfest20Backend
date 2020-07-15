package com.fest.pecfestBackend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericApiResponse {

    protected boolean success;
    protected String message;

}
