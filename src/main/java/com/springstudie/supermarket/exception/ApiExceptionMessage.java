package com.springstudie.supermarket.exception;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ApiExceptionMessage {
    private HttpStatus status;
    private List<String> errors;

    public ApiExceptionMessage(HttpStatus status, List<String> errors) {
        this.status = status;
        this.errors = errors;
    }

    public ApiExceptionMessage(HttpStatus status, String error) {
        this.status = status;
        errors = Collections.singletonList(error);
    }
}
