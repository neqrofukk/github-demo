package com.example.demo.dto;

import java.time.LocalDateTime;

public record RepoDto(
        String fullName,
        String description,
        String cloneUrl,
        Integer stars,
        LocalDateTime createdAt
) { }
