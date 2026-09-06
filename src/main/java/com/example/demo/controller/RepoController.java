package com.example.demo.controller;

import com.example.demo.dto.RepoCreateCommand;
import com.example.demo.dto.RepoDto;
import com.example.demo.dto.RepoUpdateCommand;
import com.example.demo.service.RepoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "repositories", description = "Operations for managing repositories")
@RestController
@RequiredArgsConstructor
public class RepoController {
    private final RepoService service;

    @Operation(summary = "Get GitHub repository by owner and repository name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned GitHub repository"),
            @ApiResponse(responseCode = "301", description = "Repository moved permanently"),
            @ApiResponse(responseCode = "403", description = "Access forbidden"),
            @ApiResponse(responseCode = "404", description = "Repository not found")
    })
    @GetMapping("/github/repositories/{owner}/{repositoryName}")
    @ResponseStatus(HttpStatus.OK)
    public RepoDto getGitHubRepository(@PathVariable String owner, @PathVariable String repositoryName) {
        log.info("GET /repositories/{}/{}", owner, repositoryName);
        return service.getGitHubRepository(owner, repositoryName);
    }

    @Operation(summary = "Get local repository by owner and repository name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned local repository"),
            @ApiResponse(responseCode = "404", description = "Repository not found")
    })
    @GetMapping("/local/repositories/{owner}/{repositoryName}")
    @ResponseStatus(HttpStatus.OK)
    public RepoDto getRepository(@PathVariable String owner, @PathVariable String repositoryName) {
        log.info("GET /local/repositories/{}/{}", owner, repositoryName);
        return service.getRepository(owner, repositoryName);
    }

    @Operation(summary = "Create repository")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Repository created"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "Repository already exists")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/local/repositories/{owner}/{repositoryName}")
    public RepoDto createRepository(@PathVariable String owner, @PathVariable String repositoryName, @Valid @RequestBody RepoCreateCommand body) {
        log.info("POST /repositories/{}/{}", owner, repositoryName);
        return service.createRepository(owner, repositoryName, body);
    }

    @Operation(summary = "Update repository")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repository updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Repository not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/local/repositories/{owner}/{repositoryName}")
    public RepoDto updateRepository(@PathVariable String owner, @PathVariable String repositoryName, @Valid @RequestBody RepoUpdateCommand body) {
        log.info("PUT /local/repositories/{}/{}", owner, repositoryName);
        return service.updateRepository(owner, repositoryName, body);
    }

    @Operation(summary = "Delete repository")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Repository deleted"),
            @ApiResponse(responseCode = "404", description = "Repository not found")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/local/repositories/{owner}/{repositoryName}")
    public void deleteRepository(@PathVariable String owner, @PathVariable String repositoryName) {
        log.info("DELETE /local/repositories/{}/{}", owner, repositoryName);
        service.deleteRepository(owner, repositoryName);
    }

}
