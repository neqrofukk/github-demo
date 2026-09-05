package com.example.demo.controller;

import com.example.demo.dto.RepoCreateCommand;
import com.example.demo.dto.RepoDto;
import com.example.demo.dto.RepoUpdateCommand;
import com.example.demo.entity.Repo;
import com.example.demo.service.RepoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
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
        RepoDto repo = new RepoDto(
                "neqrofukk/medical-clinic",
                "Highly advanced future tech medical app",
                "https://github.com/neqrofukk/medical-clinic.git",
                2137,
                OffsetDateTime.parse("2000-11-23T08:12:30Z").toLocalDateTime()
        );
        when(service.getApiRepository("neqrofukk", "medical-clinic")).thenReturn(repo);

        // when + then
        mockMvc.perform(get("/repositories/neqrofukk/medical-clinic", "neqrofukk", "medical-clinic"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.fullName").value("neqrofukk/medical-clinic"),
                        jsonPath("$.description").value("Highly advanced future tech medical app"),
                        jsonPath("$.cloneUrl").value("https://github.com/neqrofukk/medical-clinic.git"),
                        jsonPath("$.stars").value(2137),
                        jsonPath("$.createdAt").value("2000-11-23T08:12:30Z")
                );
    }

    @Test
    void getRepository_RepositoryExists_Response200() throws Exception {
        // given
        RepoDto repo = new RepoDto(
                "neqrofukk/medical-clinic",
                "Highly advanced future tech medical app",
                "https://github.com/neqrofukk/medical-clinic.git",
                2137,
                OffsetDateTime.parse("2000-11-23T08:12:30Z").toLocalDateTime()
        );
        when(service.getApiRepository("neqrofukk", "medical-clinic")).thenReturn(repo);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get(("/local/repositories/neqrofukk/medical-clinic")))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.fullName").value("neqrofukk/medical-clinic"),
                        jsonPath("$.description").value("Highly advanced future tech medical app"),
                        jsonPath("$.cloneUrl").value("https://github.com/neqrofukk/medical-clinic.git"),
                        jsonPath("$.stars").value(2137),
                        jsonPath("$.createdAt").value("2000-11-23T08:12:30Z")
                );
    }

    @Test
    void createRepository_ValidCreationData_RepositoryReturned() throws Exception {
        // given
        RepoCreateCommand repoCreateCommand = new RepoCreateCommand(
                "Highly advanced future tech medical app",
                "https://github.com/neqrofukk/medical-clinic.git",
                2137
        );
        RepoDto repo = new RepoDto(
                "neqrofukk/medical-clinic",
                "Highly advanced future tech medical app",
                "https://github.com/neqrofukk/medical-clinic.git",
                2137,
                OffsetDateTime.parse("2000-11-23T08:12:30Z").toLocalDateTime()
        );
        when(service.createRepository("neqrofukk", "medical-clinic", repoCreateCommand)).thenReturn(repo);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.post("/repositories/neqrofukk/medical-clinic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(repoCreateCommand)))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.fullName").value("neqrofukk/medical-clinic"),
                        jsonPath("$.description").value("Highly advanced future tech medical app"),
                        jsonPath("$.cloneUrl").value("https://github.com/neqrofukk/medical-clinic.git"),
                        jsonPath("$.stars").value(2137),
                        jsonPath("$.createdAt").value("2000-11-23T08:12:30Z")
                );
    }

    @Test
    void updateRepository_RepositoryExists_RepositoryUpdatedAndReturned() throws Exception {
        // given
        RepoUpdateCommand repoUpdateCommand = new RepoUpdateCommand(
                "neqrofukk2",
                "medical-clinic2",
                "Highly advanced future tech medical app updated",
                "https://github.com/neqrofukk/medical-clinic2.git",
                6767
        );
        RepoDto updatedRepo = new RepoDto(
                "neqrofukk2/medical-clinic2",
                "Highly advanced future tech medical app updated",
                "https://github.com/neqrofukk/medical-clinic2.git",
                6767,
                OffsetDateTime.parse("2000-11-24T09:14:30Z").toLocalDateTime()
        );
        when(service.updateRepository("neqrofukk", "medical-clinic", repoUpdateCommand)).thenReturn(updatedRepo);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.put("repositories/neqrofukk/medical-clinic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(repoUpdateCommand)))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.fullName").value("neqrofukk2"),
                        jsonPath("$.description").value("Highly advanced future tech medical app updated"),
                        jsonPath("$.cloneUrl").value("https://github.com/neqrofukk/medical-clinic2.git"),
                        jsonPath("$.stars").value(6767),
                        jsonPath("$.createdAt").value("2000-11-24T09:14:30Z")
                );
    }

    @Test
    void deleteRepository() {

    }
}