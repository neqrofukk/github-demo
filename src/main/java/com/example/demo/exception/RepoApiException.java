package com.example.demo.exception;

import com.example.demo.exception.handler.RepoException;
import org.springframework.http.HttpStatus;

public class RepoApiException extends RepoException {

    public RepoApiException(String message, HttpStatus status) {
        super(message, status);
    }

}
