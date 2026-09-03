package com.example.demo.service;

import com.example.demo.client.GitHubRepoClient;
import com.example.demo.entity.Repo;
import com.example.demo.exception.RepoNotFoundException;
import com.example.demo.mapper.RepoMapper;
import com.example.demo.dto.RepoApi;
import com.example.demo.dto.RepoCreateCommand;
import com.example.demo.dto.RepoDto;
import com.example.demo.dto.RepoUpdateCommand;
import com.example.demo.repository.RepoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RepoService {
    private final GitHubRepoClient client;
    private final RepoMapper mapper;
    private final RepoRepository repository;

    public RepoDto getApiRepository(String owner, String repositoryName) {
        RepoApi repo = client.getRepo(owner, repositoryName);
        return mapper.toRepoDto(repo);
    }

    public RepoDto getRepository(String owner, String repositoryName) {
        String fullName = Repo.buildFullName(owner, repositoryName);
        Repo repoEntity = repository.findByFullName(fullName).orElseThrow(() -> new RepoNotFoundException("Repo " + fullName + " not found"));
        return mapper.toRepoDto(repoEntity);
    }
    
    public RepoDto createRepository(String owner, String repositoryName, RepoCreateCommand repo) {
        Repo repoEntity = Repo.addRepo(owner, repositoryName, repo);
        Repo savedRepo = repository.save(repoEntity);
        return mapper.toRepoDto(savedRepo);
    }

    public RepoDto updateRepository(String owner, String repositoryName, RepoUpdateCommand repo) {
        String fullName = Repo.buildFullName(owner, repositoryName);
        Repo repoEntity = repository.findByFullName(fullName).orElseThrow(() -> new RepoNotFoundException("Repo " + fullName + " not found"));
        repoEntity.updateRepo(owner, repositoryName, repo);
        Repo savedRepo = repository.save(repoEntity);
        return mapper.toRepoDto(savedRepo);
    }

    public void deleteRepository(String owner, String repositoryName) {
        String fullName = Repo.buildFullName(owner, repositoryName);
        Repo repoEntity = repository.findByFullName(fullName).orElseThrow(() -> new RepoNotFoundException("Repo " + fullName + " not found"));
        repository.delete(repoEntity);
    }

}
