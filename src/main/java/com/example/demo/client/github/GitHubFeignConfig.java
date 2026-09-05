package com.example.demo.client.github;

import com.example.demo.exception.decoder.GitHubErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class GitHubFeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new GitHubErrorDecoder();
    }

}
