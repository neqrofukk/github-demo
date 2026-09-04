package com.example.demo.service;

import com.example.demo.client.GitHubRepoClient;
import com.example.demo.dto.RepoApi;
import com.example.demo.dto.RepoDto;
import com.example.demo.entity.Repo;
import com.example.demo.mapper.RepoMapper;
import com.example.demo.repository.RepoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RepoServiceTest {
    RepoService repoService;
    GitHubRepoClient gitHubRepoClient;
    RepoRepository repoRepository;
    RepoMapper repoMapper;

    @BeforeEach
    void setup() {
        this.gitHubRepoClient = Mockito.mock(GitHubRepoClient.class);
        this.repoRepository = Mockito.mock(RepoRepository.class);
        this.repoMapper = Mappers.getMapper(RepoMapper.class);
        this.repoService = new RepoService(gitHubRepoClient, repoMapper, repoRepository);
    }

    @Test
    void getApiRepository_RepositoryExists_RepositoryReturned() {
        // given 
        RepoApi repo = new RepoApi(
                "neqrofukk/medical-clinic",
                "Highly advanced future tech medical app",
                "https://github.com/neqrofukk/medical-clinic.git",
                2137,
                "2000-11-23T08:12:30Z");
        when(gitHubRepoClient.getRepo("neqrofukk", "medical-clinic")).thenReturn(repo);

        // when
        RepoDto result = repoService.getApiRepository("neqrofukk", "medical-clinic");

        // then
        Assertions.assertAll(
                () -> assertEquals("neqrofukk/medical-clinic", result.fullName()),
                () -> assertEquals("Highly advanced future tech medical app", result.description()),
                () -> assertEquals("https://github.com/neqrofukk/medical-clinic.git", result.cloneUrl()),
                () -> assertEquals(2137, result.stars()),
                () -> assertEquals(LocalDateTime.parse("2000-11-23T08:12:30Z"), result.createdAt()));
        verify(gitHubRepoClient.getRepo("neqrofukk", "medical-clinic"));
    }

    @Test
    void getRepository() {
        // given
        Repo repo = new Repo(
                1L,
                "neqrofukk/medical-clinic",
                "Highly advanced future tech medical app",
                "https://github.com/neqrofukk/medical-clinic.git",
                2137,
                LocalDateTime.parse("2000-11-23T08:12:30Z"),
                "neqrofukk",
                1L
        );
        when(repoRepository.findByFullName("neqrofukk/medical-clinic")).thenReturn(Optional.of(repo));

        // when
        RepoDto result = repoService.getRepository("neqrofukk", "medical-clinic");

        // then
        Assertions.assertAll(
                () -> assertEquals("neqrofukk/medical-clinic", result.fullName()),
                () -> assertEquals("Highly advanced future tech medical app", result.description()),
                () -> assertEquals("https://github.com/neqrofukk/medical-clinic.git", result.cloneUrl()),
                () -> assertEquals(2137, result.stars()),
                () -> assertEquals(LocalDateTime.parse("2000-11-23T08:12:30Z"), result.createdAt()));
    }

    @Test
    void createRepository() {
    }

    @Test
    void updateRepository() {
    }

    @Test
    void deleteRepository() {
    }
}