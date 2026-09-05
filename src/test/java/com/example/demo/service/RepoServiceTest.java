package com.example.demo.service;

import com.example.demo.client.GitHubRepoClient;
import com.example.demo.dto.RepoApi;
import com.example.demo.dto.RepoCreateCommand;
import com.example.demo.dto.RepoDto;
import com.example.demo.dto.RepoUpdateCommand;
import com.example.demo.entity.Repo;
import com.example.demo.exception.RepoAlreadyExistsException;
import com.example.demo.exception.RepoNotFoundException;
import com.example.demo.mapper.RepoMapper;
import com.example.demo.repository.RepoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
                "2000-11-23T08:12:30Z"
        );
        when(gitHubRepoClient.getRepo("neqrofukk", "medical-clinic")).thenReturn(repo);

        // when
        RepoDto result = repoService.getApiRepository("neqrofukk", "medical-clinic");

        // then
        Assertions.assertAll(
                () -> assertEquals("neqrofukk/medical-clinic", result.fullName()),
                () -> assertEquals("Highly advanced future tech medical app", result.description()),
                () -> assertEquals("https://github.com/neqrofukk/medical-clinic.git", result.cloneUrl()),
                () -> assertEquals(2137, result.stars()),
                () -> assertEquals(OffsetDateTime.parse("2000-11-23T08:12:30Z").toLocalDateTime(), result.createdAt())
        );
        verify(gitHubRepoClient).getRepo("neqrofukk", "medical-clinic");
    }

    @Test
    void getRepository_RepositoryExists_RepositoryReturned() {
        // given
        Repo repo = new Repo(
                1L,
                "neqrofukk/medical-clinic",
                "Highly advanced future tech medical app",
                "https://github.com/neqrofukk/medical-clinic.git",
                2137,
                OffsetDateTime.parse("2000-11-23T08:12:30Z").toLocalDateTime(),
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
                () -> assertEquals(OffsetDateTime.parse("2000-11-23T08:12:30Z").toLocalDateTime(), result.createdAt())
        );
        verify(repoRepository).findByFullName("neqrofukk/medical-clinic");
    }

    @Test
    void getRepository_RepositoryNotFound_ThrowsException() {
        // given
        when(repoRepository.findByFullName("neqrofukk/medical-clinic")).thenReturn(Optional.empty());

        // when + then
        RepoNotFoundException exception = assertThrows(
                RepoNotFoundException.class,
                () -> repoService.getRepository("neqrofukk", "medical-clinic"));
        assertEquals("Repo neqrofukk/medical-clinic not found", exception.getMessage());
    }

    @Test
    void createRepository_ValidCreationData_RepositoryCreatedAndReturned() {
        // given
        RepoCreateCommand repoCreateCommand = new RepoCreateCommand(
                "Highly advanced future tech medical app",
                "https://github.com/neqrofukk/medical-clinic.git",
                2137
        );
        Repo repoEntity = repoMapper.toRepoEntity("neqrofukk", "medical-clinic", repoCreateCommand);
        when(repoRepository.save(any(Repo.class))).thenReturn(repoEntity);

        // when
        RepoDto result = repoService.createRepository("neqrofukk", "medical-clinic", repoCreateCommand);

        // then
        Assertions.assertAll(
                () -> assertEquals("neqrofukk/medical-clinic", result.fullName()),
                () -> assertEquals("Highly advanced future tech medical app", result.description()),
                () -> assertEquals("https://github.com/neqrofukk/medical-clinic.git", result.cloneUrl()),
                () -> assertEquals(2137, result.stars())
        );
        verify(repoRepository).save(any(Repo.class));
    }

    @Test
    void createRepository_RepositoryAlreadyExists_ThrowsException() {
        // given
        RepoCreateCommand repoCreateCommand = new RepoCreateCommand(
                "Highly advanced future tech medical app",
                "https://github.com/neqrofukk/medical-clinic.git",
                2137
        );
        when(repoRepository.existsByFullName("neqrofukk/medical-clinic")).thenReturn(true);

        // when + then
        RepoAlreadyExistsException exception = assertThrows(
                RepoAlreadyExistsException.class,
                () -> repoService.createRepository("neqrofukk", "medical-clinic", repoCreateCommand));
        assertEquals("Repo neqrofukk/medical-clinic already exists", exception.getMessage());
        verify(repoRepository).existsByFullName("neqrofukk/medical-clinic");
    }

    @Test
    void updateRepository_RepositoryExists_RepositoryUpdatedAndReturned() {
        // given
        RepoUpdateCommand repoUpdateCommand = new RepoUpdateCommand(
                "neqrofukk2",
                "medical-clinic2",
                "Highly advanced future tech medical app updated",
                "https://github.com/neqrofukk/medical-clinic2.git",
                6767
        );
        Repo repo = new Repo(
                1L,
                "neqrofukk/medical-clinic",
                "Highly advanced future tech medical app",
                "https://github.com/neqrofukk/medical-clinic.git",
                2137,
                OffsetDateTime.parse("2000-11-23T08:12:30Z").toLocalDateTime(),
                "neqrofukk",
                1L
        );
        Repo updatedRepo = new Repo(
                1L,
                "neqrofukk2/medical-clinic2",
                "Highly advanced future tech medical app updated",
                "https://github.com/neqrofukk/medical-clinic2.git",
                6767,
                OffsetDateTime.parse("2000-11-24T09:14:30Z").toLocalDateTime(),
                "neqrofukk2",
                1L
        );

        when(repoRepository.findByFullName("neqrofukk/medical-clinic")).thenReturn(Optional.of(repo));
        when(repoRepository.save(repo)).thenReturn(updatedRepo);

        // when
        RepoDto result = repoService.updateRepository("neqrofukk", "medical-clinic", repoUpdateCommand);

        // then
        Assertions.assertAll(
                () -> assertEquals("neqrofukk2/medical-clinic2", result.fullName()),
                () -> assertEquals("Highly advanced future tech medical app updated", result.description()),
                () -> assertEquals("https://github.com/neqrofukk/medical-clinic2.git", result.cloneUrl()),
                () -> assertEquals(6767, result.stars())
        );
        verify(repoRepository).save(repo);
    }

    @Test
    void updateRepository_RepositoryNotFound_ThrowsException() {
        // given
        RepoUpdateCommand repoUpdateCommand = new RepoUpdateCommand(
                "neqrofukk2",
                "medical-clinic2",
                "Highly advanced future tech medical app updated",
                "https://github.com/neqrofukk/medical-clinic2.git",
                6767
        );
        when(repoRepository.findByFullName("neqrofukk/medical-clinic")).thenReturn(Optional.empty());

        // when + then
        RepoNotFoundException exception = assertThrows(
                RepoNotFoundException.class,
                () -> repoService.updateRepository("neqrofukk", "medical-clinic", repoUpdateCommand));
        assertEquals("Repo neqrofukk/medical-clinic not found", exception.getMessage());
        verify(repoRepository).findByFullName("neqrofukk/medical-clinic");
    }

    @Test
    void deleteRepository_RepositoryExists_RepositoryDeleted() {
        // when
        String fullName = "neqrofukk/medical-clinic";
        Repo repo = new Repo(
                1L,
                "neqrofukk2/medical-clinic2",
                "Highly advanced future tech medical app updated",
                "https://github.com/neqrofukk/medical-clinic.git",
                6767,
                OffsetDateTime.parse("2000-11-24T09:14:30Z").toLocalDateTime(),
                "neqrofukk2",
                1L
        );
        when(repoRepository.findByFullName(fullName)).thenReturn(Optional.of(repo));

        // when
        repoService.deleteRepository("neqrofukk", "medical-clinic");

        // then
        verify(repoRepository, times(1)).findByFullName(fullName);
        verify(repoRepository, times(1)).delete(repo);
    }

    @Test
    void deleteRepository_RepositoryExists_ThrowsException() {
        // given
        when(repoRepository.findByFullName("neqrofukk/medical-clinic")).thenReturn(Optional.empty());

        // when + then
        RepoNotFoundException exception = assertThrows(
                RepoNotFoundException.class,
                () -> repoService.deleteRepository("neqrofukk", "medical-clinic"));
        assertEquals("Repo neqrofukk/medical-clinic not found", exception.getMessage());
    }
}