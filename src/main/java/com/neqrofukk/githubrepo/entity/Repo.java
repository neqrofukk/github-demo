package com.neqrofukk.githubrepo.entity;

import com.neqrofukk.githubrepo.dto.RepoUpdateCommand;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "full_name", unique = true, nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String owner;

    @Column(name = "repository_name", nullable = false)
    private String repositoryName;

    private String description;

    @Column(name = "clone_url", nullable = false)
    private String cloneUrl;

    @Column(nullable = false)
    private Integer stars;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Version
    private Long version;

    public static String buildFullName(String owner, String repositoryName) {
        return owner + "/" + repositoryName;
    }

    public void update(RepoUpdateCommand command) {
        if (command.owner() != null) {
            this.owner = command.owner();
        }
        if (command.repositoryName() != null) {
            this.repositoryName = command.repositoryName();
        }
        this.fullName = buildFullName(this.owner, this.repositoryName);

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
        if (!(o instanceof Repo other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Repo.class.hashCode();
    }
}
