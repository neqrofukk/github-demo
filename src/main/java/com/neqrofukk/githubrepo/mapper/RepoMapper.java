package com.neqrofukk.githubrepo.mapper;

import com.neqrofukk.githubrepo.client.github.GitHubRepositoryResponse;
import com.neqrofukk.githubrepo.dto.RepoCreateCommand;
import com.neqrofukk.githubrepo.dto.RepoDto;
import com.neqrofukk.githubrepo.entity.Repo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RepoMapper {

    RepoDto toDto(GitHubRepositoryResponse repo);

    RepoDto toDto(Repo repo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "fullName", expression = "java(Repo.buildFullName(owner, repositoryName))")
    Repo toEntity(String owner, String repositoryName, RepoCreateCommand repo);

}
