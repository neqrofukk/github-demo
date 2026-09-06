package com.neqrofukk.githubrepo.client.github;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class GitHubRepositoryClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new GitHubRepositoryErrorDecoder();
    }

}
