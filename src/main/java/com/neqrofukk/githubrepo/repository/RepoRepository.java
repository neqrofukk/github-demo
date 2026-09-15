package com.neqrofukk.githubrepo.repository;

import com.neqrofukk.githubrepo.entity.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepoRepository extends JpaRepository<Repo, Long> {

    Optional<Repo> findByFullName(String fullName);

    boolean existsByFullName(String fullName);

}
