package com.manusmd.mystudyv2.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomResponse<T> {
    private String message;
    private T data;
    private HttpStatus status;

    public CustomResponse(T data, String message, HttpStatus status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }
}
