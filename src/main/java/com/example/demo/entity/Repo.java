package com.example.demo.entity;

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
    
    public Repo addRepo() {
        
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
