package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record RepoCreateCommand(
        String description,
        @NotBlank
        String cloneUrl,
        @NotNull
        @PositiveOrZero
        Integer stars
) { }
