package com.example.demo.dto;

public record RepoDto(
        String fullName,
        String description,
        String cloneUrl,
        Integer stars,
        String createdAt
) { }
