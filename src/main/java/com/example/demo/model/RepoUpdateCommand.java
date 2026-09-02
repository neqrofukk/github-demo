package com.example.demo.model;

public record RepoUpdateCommand(
        String owner,
        String repositoryName,
        String description,
        String cloneUrl,
        Integer stars
) {
}
