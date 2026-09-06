package com.neqrofukk.githubrepo.exception;

import org.springframework.http.HttpStatus;

public class GitHubRepositoryException extends RepoException {

    public GitHubRepositoryException(String message, HttpStatus status) {
        super(message, status);
    }

}
