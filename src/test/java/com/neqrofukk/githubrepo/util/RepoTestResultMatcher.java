package com.neqrofukk.githubrepo.util;

import com.neqrofukk.githubrepo.dto.RepoDto;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RepoTestResultMatcher {

    public static ResultMatcher[] expectRepoDto(RepoDto repo) {
        return new ResultMatcher[] {
                jsonPath("$.fullName").value(repo.fullName()),
                jsonPath("$.description").value(repo.description()),
                jsonPath("$.cloneUrl").value(repo.cloneUrl()),
                jsonPath("$.stars").value(repo.stars()),
                jsonPath("$.createdAt").value(repo.createdAt().toString())
        };
    }

    public static ResultMatcher[] expectExceptionStatus(String status, int statusCode) {
        return new ResultMatcher[] {
                jsonPath("$.detail").value(status),
                jsonPath("$.status").value(statusCode)
        };
    }
}
