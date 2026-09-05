package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class RepoNotFoundException extends RepoException {

    public RepoNotFoundException(String fullName) {
        super("Repo " + fullName + " not found", HttpStatus.NOT_FOUND);
    }

}
