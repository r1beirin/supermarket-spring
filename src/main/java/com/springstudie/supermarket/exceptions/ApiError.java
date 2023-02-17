package com.springstudie.supermarket.exceptions;

import org.springframework.http.HttpStatus;
import java.util.Collections;
import java.util.List;

public class ApiError {
    private final HttpStatus status;
    private final List<String> errors;

    public ApiError(HttpStatus status, List<String> errors){
        this.status = status;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String error){
        this.status = status;
        errors = Collections.singletonList(error);
    }
}
