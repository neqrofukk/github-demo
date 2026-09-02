package com.example.demo.controller;

import com.example.demo.model.RepoCreateCommand;
import com.example.demo.model.RepoDto;
import com.example.demo.model.RepoUpdateCommand;
import com.example.demo.service.RepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/repositories/{owner}/{repositoryName}")
    RepoDto createRepository(@PathVariable String owner, @PathVariable String repositoryName, @RequestBody RepoCreateCommand body) {
        return service.createRepository(owner, repositoryName, body);
    }

    @PutMapping("/repositories/{owner}/{repositoryName}")
    RepoDto updateRepository(@PathVariable String owner, @PathVariable String repositoryName, @RequestBody RepoUpdateCommand body) {
        return service.updateRepository(owner, repositoryName, body);
    }

    @DeleteMapping("/repositories/{owner}/{repositoryName}")
    void deleteRepository(@PathVariable String owner, @PathVariable String repositoryName) {
        service.deleteRepository(owner, repositoryName);
    }

}
