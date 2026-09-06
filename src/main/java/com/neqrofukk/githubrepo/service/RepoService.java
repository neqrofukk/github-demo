package com.neqrofukk.githubrepo.service;

import com.neqrofukk.githubrepo.client.github.GitHubRepositoryClient;
import com.neqrofukk.githubrepo.client.github.GitHubRepositoryResponse;
import com.neqrofukk.githubrepo.dto.*;
import com.neqrofukk.githubrepo.entity.Repo;
import com.neqrofukk.githubrepo.exception.RepoAlreadyExistsException;
import com.neqrofukk.githubrepo.exception.RepoNotFoundException;
import com.neqrofukk.githubrepo.mapper.RepoMapper;
import com.neqrofukk.githubrepo.repository.RepoRepository;
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
        repoEntity.update(repo);
        return mapper.toRepoDto(repoEntity);
    }

    @Transactional
    public void deleteRepository(String owner, String repositoryName) {
        String fullName = Repo.buildFullName(owner, repositoryName);
        Repo repoEntity = repository.findByFullName(fullName).orElseThrow(() -> new RepoNotFoundException(fullName));
        repository.delete(repoEntity);
    }



}
