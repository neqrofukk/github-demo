package com.neqrofukk.githubrepo.service;

import com.neqrofukk.githubrepo.client.github.GitHubRepositoryClient;
import com.neqrofukk.githubrepo.dto.RepoDto;
import com.neqrofukk.githubrepo.entity.Repo;
import com.neqrofukk.githubrepo.exception.RepoAlreadyExistsException;
import com.neqrofukk.githubrepo.exception.RepoNotFoundException;
import com.neqrofukk.githubrepo.mapper.RepoMapper;
import com.neqrofukk.githubrepo.repository.RepoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.time.OffsetDateTime;
import java.util.Optional;

import static com.neqrofukk.githubrepo.util.RepoTestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RepoServiceTest {
    RepoService repoService;
    GitHubRepositoryClient gitHubRepositoryClient;
    RepoRepository repoRepository;
    RepoMapper repoMapper;

    @BeforeEach
    void setup() {
        this.gitHubRepositoryClient = Mockito.mock(GitHubRepositoryClient.class);
        this.repoRepository = Mockito.mock(RepoRepository.class);
        this.repoMapper = Mappers.getMapper(RepoMapper.class);
        this.repoService = new RepoService(gitHubRepositoryClient, repoMapper, repoRepository);
    }

    @Test
    void getGitHubRepository_RepositoryExists_RepositoryReturned() {
        // given
        when(gitHubRepositoryClient.getRepository("neqrofukk", "medical-clinic")).thenReturn(gitHubRepositoryResponse());

        // when
        RepoDto result = repoService.getGitHubRepository("neqrofukk", "medical-clinic");

        // then
        Assertions.assertAll(
                () -> assertEquals("neqrofukk/medical-clinic", result.fullName()),
                () -> assertNull(result.description()),
                () -> assertEquals("https://github.com/neqrofukk/medical-clinic.git", result.cloneUrl()),
                () -> assertEquals(0, result.stars()),
                () -> assertEquals(OffsetDateTime.parse("2026-07-25T15:31:52Z"), result.createdAt())
        );
        verify(gitHubRepositoryClient).getRepository("neqrofukk", "medical-clinic");
    }

    @Test
    void getRepository_RepositoryExists_RepositoryReturned() {
        // given
        when(repoRepository.findByFullName("neqrofukk/medical-clinic")).thenReturn(Optional.of(repoEntity()));

        // when
        RepoDto result = repoService.getRepository("neqrofukk", "medical-clinic");

        // then
        Assertions.assertAll(
                () -> assertEquals("neqrofukk/medical-clinic", result.fullName()),
                () -> assertEquals("Highly advanced future tech medical app", result.description()),
                () -> assertEquals("https://github.com/neqrofukk/medical-clinic.git", result.cloneUrl()),
                () -> assertEquals(2137, result.stars()),
                () -> assertEquals(OffsetDateTime.parse("2000-11-23T08:12:30Z"), result.createdAt())
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
        Repo repoEntity = repoMapper.toEntity("neqrofukk", "medical-clinic", repoCreateCommand());
        when(repoRepository.save(any(Repo.class))).thenReturn(repoEntity);

        // when
        RepoDto result = repoService.createRepository("neqrofukk", "medical-clinic", repoCreateCommand());

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
        when(repoRepository.existsByFullName("neqrofukk/medical-clinic")).thenReturn(true);

        // when + then
        RepoAlreadyExistsException exception = assertThrows(
                RepoAlreadyExistsException.class,
                () -> repoService.createRepository("neqrofukk", "medical-clinic", repoCreateCommand()));
        assertEquals("Repo neqrofukk/medical-clinic already exists", exception.getMessage());
        verify(repoRepository).existsByFullName("neqrofukk/medical-clinic");
    }

    @Test
    void updateRepository_RepositoryExists_RepositoryUpdatedAndReturned() {
        // given
        when(repoRepository.findByFullName("neqrofukk/medical-clinic")).thenReturn(Optional.of(repoEntity()));

        // when
        RepoDto result = repoService.updateRepository("neqrofukk", "medical-clinic", repoUpdateCommand());

        // then
        Assertions.assertAll(
                () -> assertEquals("neqrofukk2/medical-clinic2", result.fullName()),
                () -> assertEquals("Highly advanced future tech medical app updated", result.description()),
                () -> assertEquals("https://github.com/neqrofukk2/medical-clinic2.git", result.cloneUrl()),
                () -> assertEquals(6767, result.stars())
        );
        verify(repoRepository).findByFullName("neqrofukk/medical-clinic");
    }

    @Test
    void updateRepository_RepositoryNotFound_ThrowsException() {
        // given
        when(repoRepository.findByFullName("neqrofukk/medical-clinic")).thenReturn(Optional.empty());

        // when + then
        RepoNotFoundException exception = assertThrows(
                RepoNotFoundException.class,
                () -> repoService.updateRepository("neqrofukk", "medical-clinic", repoUpdateCommand()));
        assertEquals("Repo neqrofukk/medical-clinic not found", exception.getMessage());
        verify(repoRepository).findByFullName("neqrofukk/medical-clinic");
    }

    @Test
    void updateRepository_NewFullNameAlreadyTaken_ThrowsException() {
        // given
        when(repoRepository.findByFullName("neqrofukk/medical-clinic")).thenReturn(Optional.of(repoEntity()));
        when(repoRepository.existsByFullName("neqrofukk2/medical-clinic2")).thenReturn(true);

        // when + then
        RepoAlreadyExistsException exception = assertThrows(
                RepoAlreadyExistsException.class,
                () -> repoService.updateRepository("neqrofukk", "medical-clinic", repoUpdateCommand()));
        assertEquals("Repo neqrofukk2/medical-clinic2 already exists", exception.getMessage());
    }

    @Test
    void deleteRepository_RepositoryExists_RepositoryDeleted() {
        // given
        String fullName = "neqrofukk/medical-clinic";
        when(repoRepository.findByFullName(fullName)).thenReturn(Optional.of(repoEntity()));

        // when
        repoService.deleteRepository("neqrofukk", "medical-clinic");

        // then
        verify(repoRepository).findByFullName(fullName);
        verify(repoRepository).delete(repoEntity());
    }

    @Test
    void deleteRepository_RepositoryNotFound_ThrowsException() {
        // given
        when(repoRepository.findByFullName("neqrofukk/medical-clinic")).thenReturn(Optional.empty());

        // when + then
        RepoNotFoundException exception = assertThrows(
                RepoNotFoundException.class,
                () -> repoService.deleteRepository("neqrofukk", "medical-clinic"));
        assertEquals("Repo neqrofukk/medical-clinic not found", exception.getMessage());
    }
}