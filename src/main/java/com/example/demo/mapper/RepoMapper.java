package com.example.demo.mapper;

import com.example.demo.dto.RepoApi;
import com.example.demo.dto.RepoDto;
import com.example.demo.entity.Repo;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface RepoMapper {

    RepoDto toRepoDto(RepoApi repo);
    RepoDto toRepoDto(Repo repo);

    default LocalDateTime map(String createdAt) {
        return OffsetDateTime.parse(createdAt).toLocalDateTime();
    }
}
