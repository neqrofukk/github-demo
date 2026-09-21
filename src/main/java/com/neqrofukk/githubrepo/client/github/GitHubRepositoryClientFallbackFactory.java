package com.neqrofukk.githubrepo.client.github;

import com.neqrofukk.githubrepo.entity.Repo;
import com.neqrofukk.githubrepo.exception.GitHubRepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GitHubRepositoryClientFallbackFactory implements FallbackFactory<GitHubRepositoryClient> {

    @Override
    public GitHubRepositoryClient create(Throwable cause) {
        log.error("An exception occurred when calling the GitHubRepositoryClient ", cause);
        return new GitHubRepositoryClient() {
            @Override
            public GitHubRepositoryResponse getRepository(String owner, String repositoryName) {
                String fullName = Repo.buildFullName(owner, repositoryName);
                if (cause instanceof GitHubRepositoryException) {
                    throw (GitHubRepositoryException) cause;
                }
                log.warn("[Fallback] getRepository failed for {} ", fullName);
                return new GitHubRepositoryResponse(fullName, null, null, null, null);
            }
        };
    }

}
