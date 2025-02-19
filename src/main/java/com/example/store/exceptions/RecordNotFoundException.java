package com.example.store.exceptions;

import lombok.Getter;
import lombok.Setter;

import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class RecordNotFoundException extends RuntimeException {
    private HttpStatusCode statusCode;

    public RecordNotFoundException(String message, HttpStatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
