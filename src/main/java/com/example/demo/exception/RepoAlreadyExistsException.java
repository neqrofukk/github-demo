package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class RepoAlreadyExistsException extends RepoException {

    public RepoAlreadyExistsException(String fullName) {
        super("Repo " + fullName + " already exists", HttpStatus.CONFLICT);
    }

}
