package com.example.demo.service;

import com.example.demo.client.GitHubRepoClient;
import com.example.demo.entity.Repo;
import com.example.demo.mapper.RepoMapper;
import com.example.demo.model.RepoApi;
import com.example.demo.model.RepoCreateCommand;
import com.example.demo.model.RepoDto;
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
    
    public RepoDto createRepository(String owner, String repositoryName, RepoCreateCommand repo) {
        Repo repoEntity = (new Repo()).addRepo(owner, repositoryName, repo);
        return mapper.to
    }

}
