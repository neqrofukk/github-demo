package com.neqrofukk.githubrepo.dto;

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
