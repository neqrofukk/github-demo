package com.example.demo.dto;

import java.time.OffsetDateTime;

public record RepoDto(
        String fullName,
        String description,
        String cloneUrl,
        Integer stars,
        OffsetDateTime createdAt
) { }
