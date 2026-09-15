package com.neqrofukk.githubrepo.client.github;

import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class GitHubRepositoryClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new GitHubRepositoryErrorDecoder();
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(100, 1000, 3);
    }

}
