package com.neqrofukk.githubrepo.client.github;

import com.neqrofukk.githubrepo.exception.GitHubRepositoryException;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class GitHubRepositoryErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        FeignException feignException = feign.FeignException.errorStatus(methodKey, response);
        HttpStatus status = HttpStatus.valueOf(response.status());
        if (status.value() >= 500) {
            return new RetryableException(
                    status.value(),
                    feignException.getMessage(),
                    response.request().httpMethod(),
                    feignException,
                    (Long) null,
                    response.request()
            );
        }
        return new GitHubRepositoryException("GitHub API error: " + status.getReasonPhrase(), status);
    }

}