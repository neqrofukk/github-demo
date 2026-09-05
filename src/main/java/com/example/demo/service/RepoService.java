package com.example.demo.service;

import com.example.demo.client.github.GitHubRepositoryClient;
import com.example.demo.dto.*;
import com.example.demo.entity.Repo;
import com.example.demo.exception.RepoAlreadyExistsException;
import com.example.demo.exception.RepoNotFoundException;
import com.example.demo.mapper.RepoMapper;
import com.example.demo.repository.RepoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RepoService {
    private final GitHubRepositoryClient gitHubClient;
    private final RepoMapper mapper;
    private final RepoRepository repository;

    public RepoDto getGitHubRepository(String owner, String repositoryName) {
        GitHubRepositoryResponse repo = gitHubClient.getRepository(owner, repositoryName);
        return mapper.toRepoDto(repo);
    }

    @Transactional(readOnly = true)
    public RepoDto getRepository(String owner, String repositoryName) {
        String fullName = Repo.buildFullName(owner, repositoryName);
        Repo repoEntity = repository.findByFullName(fullName).orElseThrow(() -> new RepoNotFoundException(fullName));
        return mapper.toRepoDto(repoEntity);
    }

    @Transactional
    public RepoDto createRepository(String owner, String repositoryName, RepoCreateCommand repo) {
        String fullName = Repo.buildFullName(owner, repositoryName);
        if (repository.existsByFullName(fullName)) {
            throw new RepoAlreadyExistsException(fullName);
        }
        Repo repoEntity = mapper.toRepoEntity(owner, repositoryName, repo);
        Repo savedRepo = repository.save(repoEntity);
        return mapper.toRepoDto(savedRepo);
    }

    @Transactional
    public RepoDto updateRepository(String owner, String repositoryName, RepoUpdateCommand repo) {
        String fullName = Repo.buildFullName(owner, repositoryName);
        Repo repoEntity = repository.findByFullName(fullName).orElseThrow(() -> new RepoNotFoundException(fullName));
        repoEntity.update(owner, repositoryName, repo);
        Repo savedRepo = repository.save(repoEntity);
        return mapper.toRepoDto(savedRepo);
    }

    @Transactional
    public void deleteRepository(String owner, String repositoryName) {
        String fullName = Repo.buildFullName(owner, repositoryName);
        Repo repoEntity = repository.findByFullName(fullName).orElseThrow(() -> new RepoNotFoundException(fullName));
        repository.delete(repoEntity);
    }

}
