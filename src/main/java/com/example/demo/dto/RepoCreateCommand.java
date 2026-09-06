package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RepoCreateCommand(
        @Size(max = 255)
        String description,
        @NotBlank
        @Size(max = 255)
        String cloneUrl,
        @NotNull
        @PositiveOrZero
        Integer stars
) { }
