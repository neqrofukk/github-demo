package com.example.demo.entity;

import com.example.demo.model.RepoCreateCommand;
import com.example.demo.model.RepoUpdateCommand;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "repositories")
public class Repo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fullName;
    String description;
    String cloneUrl;
    Integer stars;
    LocalDateTime createdAt;
    String owner;

    @Version
    private Long version;

    public static String buildFullName(String owner, String repositoryName) {
        return owner + "/" + repositoryName;
    }

    public static Repo addRepo(String owner, String repositoryName, RepoCreateCommand command) {
        Repo repo = new Repo();
        repo.setFullName(buildFullName(owner, repositoryName));
        repo.setDescription(command.description());
        repo.setCloneUrl(command.cloneUrl());
        repo.setStars(command.stars());
        repo.setCreatedAt(LocalDateTime.now());
        repo.setOwner(owner);
        return repo;
    }

    public void updateRepo(String owner, String repositoryName, RepoUpdateCommand command) {
        if (command.owner() != null) {
            this.fullName = buildFullName(command.owner(), command.repositoryName() != null ? command.repositoryName() : repositoryName);
            this.owner = command.owner();
        }
        if (command.repositoryName() != null) {
            this.fullName = buildFullName(command.owner() != null ? command.owner() : owner, command.repositoryName());
        }
        if (command.description() != null) {
            this.description = command.description();
        }
        if (command.cloneUrl() != null) {
            this.cloneUrl = command.cloneUrl();
        }
        if (command.stars() != null) {
            this.stars = command.stars();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Repo))
            return false;
        Repo other = (Repo) o;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
