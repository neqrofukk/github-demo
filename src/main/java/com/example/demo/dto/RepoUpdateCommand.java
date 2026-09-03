package com.example.demo.dto;

public record RepoUpdateCommand(
        String owner,
        String repositoryName,
        String description,
        String cloneUrl,
        Integer stars
) {
}
