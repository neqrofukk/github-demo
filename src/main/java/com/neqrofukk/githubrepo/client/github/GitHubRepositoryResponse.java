package com.neqrofukk.githubrepo.client.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record GitHubRepositoryResponse(
        @JsonProperty("full_name")
        String fullName,
        String description,
        @JsonProperty("clone_url")
        String cloneUrl,
        @JsonProperty("stargazers_count")
        Integer stars,
        @JsonProperty("created_at")
        String createdAt
) { }
