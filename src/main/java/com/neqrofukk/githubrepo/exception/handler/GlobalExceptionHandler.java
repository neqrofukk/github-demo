package com.neqrofukk.githubrepo.exception.handler;

import com.neqrofukk.githubrepo.exception.RepoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RepoException.class)
    public ProblemDetail handleRepoException(RepoException ex) {
        log.error("Handled Repo exception: status = {}, message = {}", ex.getStatus().value(), ex.getMessage());
        return ProblemDetail.forStatusAndDetail(
                ex.getStatus(),
                ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleConflict(DataIntegrityViolationException ex) {
        log.error("Conflict occurred: ", ex);
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "Repository with this owner/repositoryName combination already exists"
        );
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception exception) {
        log.error("Unexpected error occurred: ", exception);
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected error occurred " + OffsetDateTime.now());
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    ProblemDetail handleOptimisticLock(OptimisticLockingFailureException ex) {
        log.warn("Concurrent modification: {}", ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "Repository was modified by another request, retry");
    }

}

