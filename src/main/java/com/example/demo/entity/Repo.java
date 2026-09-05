package com.example.demo.entity;

import com.example.demo.dto.RepoUpdateCommand;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "repositories")
public class Repo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String fullName;
    private String description;
    @Column(nullable = false)
    private String cloneUrl;
    @Column(nullable = false)
    private Integer stars;
    @Column(nullable = false)
    private OffsetDateTime createdAt;
    @Column(nullable = false)
    private String owner;

    @Version
    private Long version;

    public static String buildFullName(String owner, String repositoryName) {
        return owner + "/" + repositoryName;
    }

    public void update(String owner, String repositoryName, RepoUpdateCommand command) {
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
