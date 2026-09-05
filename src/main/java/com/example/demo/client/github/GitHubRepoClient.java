package com.example.demo.client.github;

import com.example.demo.dto.RepoGitHub;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "neqroApi",
        url = "https://api.github.com/repos",
        configuration = GitHubFeignConfig.class)
public interface GitHubRepoClient {

    @GetMapping("/{owner}/{repositoryName}")
    RepoGitHub getRepo(@PathVariable String owner, @PathVariable String repositoryName);

}
