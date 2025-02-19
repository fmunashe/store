package com.example.store.exceptions.handler;

import com.example.store.exceptions.RecordNotFoundException;
import com.example.store.messages.ErrorResponseMessage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponseMessage> handleException(RecordNotFoundException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(ErrorResponseMessage.builder()
                        .message(exception.getMessage())
                        .status(exception.getStatusCode())
                        .build());
    }
}
