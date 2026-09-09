package com.neqrofukk.githubrepo.client.github;

import com.neqrofukk.githubrepo.dto.RepoDto;
import com.neqrofukk.githubrepo.service.RepoService;
import feign.RetryableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.wiremock.spring.EnableWireMock;

import java.time.OffsetDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@EnableWireMock
class GitHubRepositoryClientTest {

    @Autowired
    private RepoService repoService;

    @Test
    void getRepository_RepositoryExists_RepositoryReturned() {
        stubFor(get("/neqrofukk/medical-clinic").willReturn(okJson("""
                        { "full_name": "neqrofukk/medical-clinic",
                          "description": null,
                          "clone_url": "https://github.com/neqrofukk/medical-clinic.git",
                          "stargazers_count": 0,
                          "created_at": "2026-07-25T15:31:52Z" }
                """)));

        RepoDto result = repoService.getGitHubRepository("neqrofukk", "medical-clinic");

        Assertions.assertAll(
                () -> assertThat(result.fullName()).isEqualTo("neqrofukk/medical-clinic"),
                () -> assertThat(result.description()).isNull(),
                () -> assertThat(result.cloneUrl()).isEqualTo("https://github.com/neqrofukk/medical-clinic.git"),
                () -> assertThat(result.stars()).isEqualTo(0),
                () -> assertThat(result.createdAt()).isEqualTo(OffsetDateTime.parse("2026-07-25T15:31:52Z"))
        );
    }

    @Test
    void getRepository_RepositoryUnavailable_RepositoryReturnedAfterRetry() {
        stubFor(get("/neqrofukk/medical-clinic").inScenario("Retry")
                .whenScenarioStateIs(STARTED)
                .willReturn(serverError()
                        .withStatus(503))
                .willSetStateTo("First retry"));

        stubFor(get("/neqrofukk/medical-clinic").inScenario("Retry")
                .whenScenarioStateIs("First retry")
                .willReturn(serverError()
                        .withStatus(503))
                .willSetStateTo("Second retry"));

        stubFor(get("/neqrofukk/medical-clinic").inScenario("Retry")
                .whenScenarioStateIs("Second retry")
                .willReturn(okJson("""
                                { "full_name": "neqrofukk/medical-clinic",
                                  "description": null,
                                  "clone_url": "https://github.com/neqrofukk/medical-clinic.git",
                                  "stargazers_count": 0,
                                  "created_at": "2026-07-25T15:31:52Z" }
                        """)));

        RepoDto result = repoService.getGitHubRepository("neqrofukk", "medical-clinic");

        Assertions.assertAll(
                () -> assertThat(result.fullName()).isEqualTo("neqrofukk/medical-clinic"),
                () -> assertThat(result.description()).isNull(),
                () -> assertThat(result.cloneUrl()).isEqualTo("https://github.com/neqrofukk/medical-clinic.git"),
                () -> assertThat(result.stars()).isEqualTo(0),
                () -> assertThat(result.createdAt()).isEqualTo(OffsetDateTime.parse("2026-07-25T15:31:52Z"))
        );
        verify(3, getRequestedFor(urlEqualTo("/neqrofukk/medical-clinic")));
    }

    @Test
    void getRepository_RepositoryUnavailable_ThrowsExceptionAfterRetries() {
        stubFor(get("/neqrofukk/medical-clinic")
                .willReturn(serverError().withStatus(503)));

        Assertions.assertThrows(RetryableException.class,
                () -> repoService.getGitHubRepository("neqrofukk", "medical-clinic"));
        verify(3, getRequestedFor(urlEqualTo("/neqrofukk/medical-clinic")));
    }
}