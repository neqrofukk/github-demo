package com.example.demo.controller;

import com.example.demo.model.RepoDto;
import com.example.demo.service.RepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RepoController {
    private final RepoService service;

    @GetMapping("/repositories/{owner}/{repositoryName}")
    RepoDto getGitHubRepository(@PathVariable String owner, @PathVariable String repositoryName) {
        return service.getApiRepository(owner, repositoryName);
    }

    @GetMapping("/local/repositories/{owner}/{repositoryName}")
    RepoDto getRepository(@PathVariable String owner, @PathVariable String repositoryName) {
        return service.getRepository(owner, repositoryName);
    }

}
