package com.example.demo.dto;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record RepoDto(
        String fullName,
        String description,
        String cloneUrl,
        Integer stars,
        OffsetDateTime createdAt
) { }
