package com.neqrofukk.githubrepo.client.github;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "githubClient",
        url = "${github.api.url}",
        configuration = GitHubRepositoryClientConfig.class,
        fallbackFactory = GitHubRepositoryClientFallbackFactory.class)
public interface GitHubRepositoryClient {

    @GetMapping("/{owner}/{repositoryName}")
    GitHubRepositoryResponse getRepository(@PathVariable String owner, @PathVariable String repositoryName);

}
