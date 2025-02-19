package com.example.store.messages;

import lombok.Builder;
import lombok.Data;

import org.springframework.http.HttpStatusCode;

@Data
@Builder
public class ErrorResponseMessage {
    private String message;
    private HttpStatusCode status;
}
