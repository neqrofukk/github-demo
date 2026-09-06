package com.example.demo.util;

import com.example.demo.client.github.GitHubRepositoryResponse;
import com.example.demo.dto.RepoCreateCommand;
import com.example.demo.dto.RepoDto;
import com.example.demo.dto.RepoUpdateCommand;
import com.example.demo.entity.Repo;

import java.time.OffsetDateTime;

public final class RepoTestDataFactory {

    public static final String OWNER = "neqrofukk";
    public static final String REPOSITORY_NAME = "medical-clinic";
    public static final String FULL_NAME = OWNER + "/" + REPOSITORY_NAME;
    public static final String DESCRIPTION = "Highly advanced future tech medical app";
    public static final String CLONE_URL = "https://github.com/neqrofukk/medical-clinic.git";
    public static final int STARS = 2137;
    public static final String CREATED_AT_RAW = "2000-11-23T08:12:30Z";
    public static final OffsetDateTime CREATED_AT = OffsetDateTime.parse(CREATED_AT_RAW);

    public static final String UPDATED_OWNER = "neqrofukk2";
    public static final String UPDATED_REPOSITORY_NAME = "medical-clinic2";
    public static final String UPDATED_FULL_NAME = UPDATED_OWNER + "/" + UPDATED_REPOSITORY_NAME;
    public static final String UPDATED_DESCRIPTION = DESCRIPTION + " updated";
    public static final String UPDATED_CLONE_URL = "https://github.com/neqrofukk2/medical-clinic2.git";
    public static final int UPDATED_STARS = 6767;
    public static final OffsetDateTime UPDATED_CREATED_AT = OffsetDateTime.parse("2000-11-24T09:14:30Z");


    private RepoTestDataFactory() { }

    public static Repo repoEntity() {
        return Repo.builder()
                .id(1L)
                .fullName(FULL_NAME)
                .owner(OWNER)
                .repositoryName(REPOSITORY_NAME)
                .description(DESCRIPTION)
                .cloneUrl(CLONE_URL)
                .stars(STARS)
                .createdAt(CREATED_AT)
                .version(0L)
                .build();
    }

    public static Repo updatedRepoEntity() {
        return Repo.builder()
                .id(1L)
                .fullName(UPDATED_FULL_NAME)
                .owner(UPDATED_OWNER)
                .repositoryName(UPDATED_REPOSITORY_NAME)
                .description(UPDATED_DESCRIPTION)
                .cloneUrl(UPDATED_CLONE_URL)
                .stars(UPDATED_STARS)
                .createdAt(CREATED_AT)
                .version(0L)
                .build();
    }

    public static RepoDto repoDto() {
        return RepoDto.builder()
                .fullName(FULL_NAME)
                .description(DESCRIPTION)
                .cloneUrl(CLONE_URL)
                .stars(STARS)
                .createdAt(CREATED_AT)
                .build();
    }

    public static RepoDto updatedRepoDto() {
        return RepoDto.builder()
                .fullName(UPDATED_FULL_NAME)
                .description(UPDATED_DESCRIPTION)
                .cloneUrl(UPDATED_CLONE_URL)
                .stars(UPDATED_STARS)
                .createdAt(UPDATED_CREATED_AT)
                .build();
    }

    public static GitHubRepositoryResponse gitHubRepositoryResponse() {
        return GitHubRepositoryResponse.builder()
                .fullName(FULL_NAME)
                .description(DESCRIPTION)
                .cloneUrl(CLONE_URL)
                .stars(STARS)
                .createdAt(CREATED_AT_RAW)
                .build();
    }

    public static RepoCreateCommand repoCreateCommand() {
        return RepoCreateCommand.builder()
                .description(DESCRIPTION)
                .cloneUrl(CLONE_URL)
                .stars(STARS)
                .build();
    }

    public static RepoUpdateCommand repoUpdateCommand() {
        return RepoUpdateCommand.builder()
                .owner(UPDATED_OWNER)
                .repositoryName(UPDATED_REPOSITORY_NAME)
                .description(UPDATED_DESCRIPTION)
                .cloneUrl(UPDATED_CLONE_URL)
                .stars(UPDATED_STARS)
                .build();
    }
}