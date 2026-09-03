package com.example.demo.dto;

public record RepoCreateCommand(
        String description,
        String cloneUrl,
        Integer stars
) {
}
