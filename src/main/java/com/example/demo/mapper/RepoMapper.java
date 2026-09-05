package com.example.demo.mapper;

import com.example.demo.dto.RepoCreateCommand;
import com.example.demo.dto.RepoDto;
import com.example.demo.dto.RepoGitHub;
import com.example.demo.entity.Repo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface RepoMapper {

    RepoDto toRepoDto(RepoGitHub repo);

    RepoDto toRepoDto(Repo repo);

    @Mapping(target = "fullName", expression = "java(Repo.buildFullName(owner, repositoryName))")
    Repo toRepoEntity(String owner, String repositoryName, RepoCreateCommand repo);

    default LocalDateTime map(String createdAt) {
        return OffsetDateTime.parse(createdAt).toLocalDateTime();
    }

}
