package com.example.demo.model;

public record RepoDto(
        String fullName,
        String description,
        String cloneUrl,
        Integer stars,
        String createdAt
) { }
