package com.example.demo.service;

import com.example.demo.client.GitHubRepoClient;
import com.example.demo.entity.Repo;
import com.example.demo.exception.RepoAlreadyExistsException;
import com.example.demo.exception.RepoNotFoundException;
import com.example.demo.mapper.RepoMapper;
import com.example.demo.dto.RepoApi;
import com.example.demo.dto.RepoCreateCommand;
import com.example.demo.dto.RepoDto;
import com.example.demo.dto.RepoUpdateCommand;
import com.example.demo.repository.RepoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RepoService {
    private final GitHubRepoClient client;
    private final RepoMapper mapper;
    private final RepoRepository repository;

    @Transactional
    public RepoDto getApiRepository(String owner, String repositoryName) {
        RepoApi repo = client.getRepo(owner, repositoryName);
        return mapper.toRepoDto(repo);
    }

    @Transactional
    public RepoDto getRepository(String owner, String repositoryName) {
        String fullName = Repo.buildFullName(owner, repositoryName);
        Repo repoEntity = repository.findByFullName(fullName).orElseThrow(() -> new RepoNotFoundException(fullName));
        return mapper.toRepoDto(repoEntity);
    }

    @Transactional
    public RepoDto createRepository(String owner, String repositoryName, RepoCreateCommand repo) {
        String fullName = Repo.buildFullName(owner, repositoryName);
        Repo repoEntity = mapper.toRepoEntity(owner, repositoryName, repo);
        if (repository.existsByFullName(fullName)) {
            throw new RepoAlreadyExistsException(fullName);
        }
        Repo savedRepo = repository.save(repoEntity);
        return mapper.toRepoDto(savedRepo);
    }

    @Transactional
    public RepoDto updateRepository(String owner, String repositoryName, RepoUpdateCommand repo) {
        String fullName = Repo.buildFullName(owner, repositoryName);
        Repo repoEntity = repository.findByFullName(fullName).orElseThrow(() -> new RepoNotFoundException(fullName));
        repoEntity.updateRepo(owner, repositoryName, repo);
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
