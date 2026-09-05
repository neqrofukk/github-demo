package com.example.demo.util;

import com.example.demo.dto.GitHubRepositoryResponse;
import com.example.demo.dto.RepoCreateCommand;
import com.example.demo.dto.RepoDto;
import com.example.demo.dto.RepoUpdateCommand;
import com.example.demo.entity.Repo;

import java.time.OffsetDateTime;

public class RepoTestDataFactory {

    public static GitHubRepositoryResponse gitHubRepositoryResponse() {
        return new GitHubRepositoryResponse(
                "neqrofukk/medical-clinic",
                "Highly advanced future tech medical app",
                "https://github.com/neqrofukk/medical-clinic.git",
                2137,
                "2000-11-23T08:12:30Z"
        );
    }

    public static Repo repoEntity() {
        return new Repo(
                1L,
                "neqrofukk/medical-clinic",
                "Highly advanced future tech medical app",
                "https://github.com/neqrofukk/medical-clinic.git",
                2137,
                OffsetDateTime.parse("2000-11-23T08:12:30Z"),
                "neqrofukk",
                1L
        );
    }

    public static Repo updatedRepoEntity() {
        return new Repo(
                1L,
                "neqrofukk2/medical-clinic2",
                "Highly advanced future tech medical app updated",
                "https://github.com/neqrofukk2/medical-clinic2.git",
                2137,
                OffsetDateTime.parse("2000-11-23T08:12:30Z"),
                "neqrofukk2",
                1L
        );
    }

    public static RepoDto repoDto() {
        return new RepoDto(
                "neqrofukk/medical-clinic",
                "Highly advanced future tech medical app",
                "https://github.com/neqrofukk/medical-clinic.git",
                2137,
                OffsetDateTime.parse("2000-11-23T08:12:30Z")
        );
    }

    public static RepoDto updatedRepoDto() {
        return new RepoDto(
                "neqrofukk2/medical-clinic2",
                "Highly advanced future tech medical app updated",
                "https://github.com/neqrofukk2/medical-clinic2.git",
                6767,
                OffsetDateTime.parse("2000-11-24T09:14:30Z")
        );
    }

    public static RepoCreateCommand repoCreateCommand() {
        return new RepoCreateCommand(
                "Highly advanced future tech medical app",
                "https://github.com/neqrofukk/medical-clinic.git",
                2137
        );
    }

    public static RepoUpdateCommand repoUpdateCommand() {
        return new RepoUpdateCommand(
                "neqrofukk2",
                "medical-clinic2",
                "Highly advanced future tech medical app updated",
                "https://github.com/neqrofukk/medical-clinic2.git",
                6767
        );
    }
}