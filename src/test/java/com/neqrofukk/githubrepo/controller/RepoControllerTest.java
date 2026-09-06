package com.neqrofukk.githubrepo.controller;

import com.neqrofukk.githubrepo.exception.GitHubRepositoryException;
import com.neqrofukk.githubrepo.exception.RepoAlreadyExistsException;
import com.neqrofukk.githubrepo.exception.RepoNotFoundException;
import com.neqrofukk.githubrepo.service.RepoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static com.neqrofukk.githubrepo.util.RepoTestDataFactory.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RepoController.class)
class RepoControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    RepoService service;

    @Test
    void getGitHubRepository_RepositoryExists_Response200() throws Exception {
        // given
        when(service.getGitHubRepository("neqrofukk", "medical-clinic")).thenReturn(gitHubRepositoryDto());

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/github/repositories/neqrofukk/medical-clinic"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.fullName").value("neqrofukk/medical-clinic"),
                        jsonPath("$.description").isEmpty(),
                        jsonPath("$.cloneUrl").value("https://github.com/neqrofukk/medical-clinic.git"),
                        jsonPath("$.stars").value(0),
                        jsonPath("$.createdAt").value("2026-07-25T15:31:52Z")
                );
        verify(service).getGitHubRepository("neqrofukk", "medical-clinic");
    }

    @Test
    void getGitHubRepository_RepositoryNotFound_Response404() throws Exception {
        // given
        when(service.getGitHubRepository("neqrofukk", "medical-clinic"))
                .thenThrow(new GitHubRepositoryException("GitHub API error: Not Found", HttpStatus.NOT_FOUND));

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/github/repositories/neqrofukk/medical-clinic"))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.status").value(404),
                        jsonPath("$.detail").value("GitHub API error: Not Found")
                );
        verify(service).getGitHubRepository("neqrofukk", "medical-clinic");
    }

    @Test
    void getGitHubRepository_AccessForbidden_Response403() throws Exception {
        // given
        when(service.getGitHubRepository("neqrofukk", "medical-clinic"))
                .thenThrow(new GitHubRepositoryException("GitHub API error: Forbidden", HttpStatus.FORBIDDEN));

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/github/repositories/neqrofukk/medical-clinic"))
                .andExpectAll(
                        status().isForbidden(),
                        jsonPath("$.status").value(403),
                        jsonPath("$.detail").value("GitHub API error: Forbidden")
                );
        verify(service).getGitHubRepository("neqrofukk", "medical-clinic");
    }

    @Test
    void getGitHubRepository_RepositoryMoved_Response301() throws Exception {
        // given
        when(service.getGitHubRepository("neqrofukk", "medical-clinic"))
                .thenThrow(new GitHubRepositoryException("GitHub API error: Moved Permanently", HttpStatus.MOVED_PERMANENTLY));

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/github/repositories/neqrofukk/medical-clinic"))
                .andExpectAll(
                        status().isMovedPermanently(),
                        jsonPath("$.status").value(301),
                        jsonPath("$.detail").value("GitHub API error: Moved Permanently")
                );
        verify(service).getGitHubRepository("neqrofukk", "medical-clinic");
    }

    @Test
    void getRepository_RepositoryExists_Response200() throws Exception {
        // given
        when(service.getRepository("neqrofukk", "medical-clinic")).thenReturn(repoDto());

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/local/repositories/neqrofukk/medical-clinic"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.fullName").value("neqrofukk/medical-clinic"),
                        jsonPath("$.description").value("Highly advanced future tech medical app"),
                        jsonPath("$.cloneUrl").value("https://github.com/neqrofukk/medical-clinic.git"),
                        jsonPath("$.stars").value(2137),
                        jsonPath("$.createdAt").value("2000-11-23T08:12:30Z")
                );
        verify(service).getRepository("neqrofukk", "medical-clinic");
    }

    @Test
    void getRepository_RepositoryNotFound_Response404() throws Exception {
        // given
        when(service.getRepository("neqrofukk", "medical-clinic")).thenThrow(new RepoNotFoundException("neqrofukk/medical-clinic"));

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/local/repositories/neqrofukk/medical-clinic"))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.status").value(404),
                        jsonPath("$.detail").value("Repo neqrofukk/medical-clinic not found")
                );
        verify(service).getRepository("neqrofukk", "medical-clinic");
    }

    @Test
    void createRepository_ValidCreationData_Response201() throws Exception {
        // given
        when(service.createRepository("neqrofukk", "medical-clinic", repoCreateCommand())).thenReturn(repoDto());

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.post("/local/repositories/neqrofukk/medical-clinic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(repoCreateCommand())))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.fullName").value("neqrofukk/medical-clinic"),
                        jsonPath("$.description").value("Highly advanced future tech medical app"),
                        jsonPath("$.cloneUrl").value("https://github.com/neqrofukk/medical-clinic.git"),
                        jsonPath("$.stars").value(2137),
                        jsonPath("$.createdAt").value("2000-11-23T08:12:30Z")
                );
        verify(service).createRepository("neqrofukk", "medical-clinic", repoCreateCommand());
    }

    @Test
    void createRepository_RepositoryAlreadyExists_Response409() throws Exception {
        // given
        when(service.createRepository("neqrofukk", "medical-clinic", repoCreateCommand())).thenThrow(new RepoAlreadyExistsException("neqrofukk/medical-clinic"));

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.post("/local/repositories/neqrofukk/medical-clinic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(repoCreateCommand())))
                .andExpectAll(
                        status().isConflict(),
                        jsonPath("$.status").value(409),
                        jsonPath("$.detail").value("Repo neqrofukk/medical-clinic already exists")
                );
        verify(service).createRepository("neqrofukk", "medical-clinic", repoCreateCommand());
    }

    @Test
    void updateRepository_RepositoryExists_Response200() throws Exception {
        // given
        when(service.updateRepository("neqrofukk", "medical-clinic", repoUpdateCommand())).thenReturn(updatedRepoDto());

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.put("/local/repositories/neqrofukk/medical-clinic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(repoUpdateCommand())))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.fullName").value("neqrofukk2/medical-clinic2"),
                        jsonPath("$.description").value("Highly advanced future tech medical app updated"),
                        jsonPath("$.cloneUrl").value("https://github.com/neqrofukk2/medical-clinic2.git"),
                        jsonPath("$.stars").value(6767),
                        jsonPath("$.createdAt").value("2000-11-24T09:14:30Z")
                );
        verify(service).updateRepository("neqrofukk", "medical-clinic", repoUpdateCommand());
    }

    @Test
    void updateRepository_RepositoryNotFound_Response404() throws Exception {
        // given
        when(service.updateRepository("neqrofukk", "medical-clinic", repoUpdateCommand()))
                .thenThrow(new RepoNotFoundException("neqrofukk/medical-clinic"));

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.put("/local/repositories/neqrofukk/medical-clinic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(repoUpdateCommand())))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.status").value(404),
                        jsonPath("$.detail").value("Repo neqrofukk/medical-clinic not found")
                );
        verify(service).updateRepository("neqrofukk", "medical-clinic", repoUpdateCommand());
    }

    @Test
    void deleteRepository_RepositoryExists_NoContentReturned() throws Exception {
        // when + then
        mockMvc.perform(MockMvcRequestBuilders.delete(("/local/repositories/neqrofukk/medical-clinic")))
                .andExpect(status().isNoContent());
        verify(service).deleteRepository("neqrofukk", "medical-clinic");
    }

    @Test
    void deleteRepository_RepositoryNotFound_Response404() throws Exception {
        // given
        doThrow(new RepoNotFoundException("neqrofukk/medical-clinic"))
                .when(service)
                .deleteRepository("neqrofukk", "medical-clinic");

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.delete("/local/repositories/neqrofukk/medical-clinic"))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.status").value(404),
                        jsonPath("$.detail").value("Repo neqrofukk/medical-clinic not found")
                );
        verify(service).deleteRepository("neqrofukk", "medical-clinic");
    }

}