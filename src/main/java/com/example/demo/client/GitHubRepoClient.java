package com.example.demo.client;

import com.example.demo.model.RepoApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "neqroApi", url = "https://api.github.com/repos") // /neqrofukk/medical-clinic
public interface GitHubRepoClient {

    @GetMapping("/{owner}/{repositoryName}")
    RepoApi getRepo(@PathVariable String owner, @PathVariable String repositoryName);

}
