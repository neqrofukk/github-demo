package com.neqrofukk.githubrepo.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RepoUpdateCommand(
        @Size(max = 255)
        String owner,
        @Size(max = 255)
        String repositoryName,
        @Size(max = 255)
        String description,
        @Size(max = 255)
        String cloneUrl,
        Integer stars
) { }
