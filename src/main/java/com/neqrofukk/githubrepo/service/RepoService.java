package com.neqrofukk.githubrepo.service;

import com.neqrofukk.githubrepo.client.github.GitHubRepositoryClient;
import com.neqrofukk.githubrepo.client.github.GitHubRepositoryResponse;
import com.neqrofukk.githubrepo.dto.RepoCreateCommand;
import com.neqrofukk.githubrepo.dto.RepoDto;
import com.neqrofukk.githubrepo.dto.RepoUpdateCommand;
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
        return mapper.toDto(repo);
    }

    @Transactional(readOnly = true)
    public RepoDto getRepository(String owner, String repositoryName) {
        String fullName = Repo.buildFullName(owner, repositoryName);
        Repo repoEntity = repository.findByFullName(fullName).orElseThrow(() -> new RepoNotFoundException(fullName));
        return mapper.toDto(repoEntity);
    }

    @Transactional
    public RepoDto createRepository(String owner, String repositoryName, RepoCreateCommand repo) {
        String fullName = Repo.buildFullName(owner, repositoryName);
        if (repository.existsByFullName(fullName)) {
            throw new RepoAlreadyExistsException(fullName);
        }
        Repo repoEntity = mapper.toEntity(owner, repositoryName, repo);
        Repo savedRepo = repository.save(repoEntity);
        return mapper.toDto(savedRepo);
    }

    @Transactional
    public RepoDto updateRepository(String owner, String repositoryName, RepoUpdateCommand repo) {
        String fullName = Repo.buildFullName(owner, repositoryName);
        Repo repoEntity = repository.findByFullName(fullName).orElseThrow(() -> new RepoNotFoundException(fullName));

        String newFullName = Repo.buildFullName(
                repo.owner() != null ? repo.owner() : repoEntity.getOwner(),
                repo.repositoryName() != null ? repo.repositoryName() : repoEntity.getRepositoryName());

        if (!newFullName.equals(fullName) && repository.existsByFullName(newFullName)) {
            throw new RepoAlreadyExistsException(newFullName);
        }

        repoEntity.update(repo);
        return mapper.toDto(repoEntity);
    }

    @Transactional
    public void deleteRepository(String owner, String repositoryName) {
        String fullName = Repo.buildFullName(owner, repositoryName);
        Repo repoEntity = repository.findByFullName(fullName).orElseThrow(() -> new RepoNotFoundException(fullName));
        repository.delete(repoEntity);
    }

}
