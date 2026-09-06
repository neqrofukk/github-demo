package com.neqrofukk.githubrepo.client.github;

import com.neqrofukk.githubrepo.exception.GitHubRepositoryException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class GitHubRepositoryErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());
        return new GitHubRepositoryException("GitHub API error: " + status.getReasonPhrase(), status);
    }

}