package com.fest.pecfestBackend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericApiDataResponse<T> extends GenericApiResponse {

    private T data;

    public GenericApiDataResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
