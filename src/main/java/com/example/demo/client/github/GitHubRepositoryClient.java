package com.example.demo.client.github;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "neqro-github-api",
        url = "${github.api.url}",
        configuration = GitHubRepositoryClientConfig.class)
public interface GitHubRepositoryClient {

    @GetMapping("/{owner}/{repositoryName}")
    GitHubRepositoryResponse getRepository(@PathVariable String owner, @PathVariable String repositoryName);

}
