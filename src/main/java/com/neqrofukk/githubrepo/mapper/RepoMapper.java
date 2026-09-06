package com.neqrofukk.githubrepo.mapper;

import com.neqrofukk.githubrepo.client.github.GitHubRepositoryResponse;
import com.neqrofukk.githubrepo.dto.RepoCreateCommand;
import com.neqrofukk.githubrepo.dto.RepoDto;
import com.neqrofukk.githubrepo.entity.Repo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface RepoMapper {

    RepoDto toRepoDto(GitHubRepositoryResponse repo);

    RepoDto toRepoDto(Repo repo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "fullName", expression = "java(Repo.buildFullName(owner, repositoryName))")
    Repo toRepoEntity(String owner, String repositoryName, RepoCreateCommand repo);

    default OffsetDateTime mapCreatedAt(String createdAt) {
        return OffsetDateTime.parse(createdAt);
    }

}
