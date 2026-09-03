package com.example.demo.mapper;

import com.example.demo.entity.Repo;
import com.example.demo.dto.RepoApi;
import com.example.demo.dto.RepoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RepoMapper {

    RepoDto toRepoDto(RepoApi repo);
    RepoDto toRepoDto(Repo repo);

    @Mapping(target = "id", ignore = true)
    Repo toEntity(RepoDto repo);
}
