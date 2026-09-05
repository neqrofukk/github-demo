package com.example.demo.exception.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class RepoException extends RuntimeException {
    private final HttpStatus status;

    protected RepoException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
