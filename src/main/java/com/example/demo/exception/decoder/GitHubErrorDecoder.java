package com.example.demo.exception.decoder;

import com.example.demo.exception.RepoApiException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class GitHubErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());
        return new RepoApiException("GitHub API error: " + status.getReasonPhrase(), status);
    }

}