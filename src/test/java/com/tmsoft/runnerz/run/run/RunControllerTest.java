package com.tmsoft.runnerz.run.run;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RunController.class)
public class RunControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    RunRepository repository;

    private final List<Run> runs = new ArrayList<>();

    @BeforeEach
    void setUp() {
        runs.add(new Run(3,
                "Testing 3",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                1,
                Location.OUTDOOR, 0));
    }

    @Test
    void shouldFindAllRuns() throws Exception {
        when(repository.findAll()).thenReturn(runs);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/runs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(runs.size())));
    }

    @Test
    void shouldFindRunOneRun() throws Exception {
       Run run = runs.get(0);
       when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(run));

         mockMvc.perform(MockMvcRequestBuilders.get("/api/runs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(run.id())))
                .andExpect(jsonPath("$.title", is(run.title())))
                .andExpect(jsonPath("$.miles", is(run.miles())))
                .andExpect(jsonPath("$.location", is(run.location().toString())));
    }

    @Test
    void shouldReturnNotFoundForInvalidId() throws Exception {
               mockMvc.perform(MockMvcRequestBuilders.get("/api/runs/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewRun() throws Exception {
        Run run = new Run(null, "test", LocalDateTime.now(), LocalDateTime.now(), 1, Location.INDOOR, 0);
        String runJson = objectMapper.writeValueAsString(run);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/runs")
                .contentType("application/json")
                .content(runJson))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateRun() throws Exception {
        Run run = new Run(null, "test", LocalDateTime.now(), LocalDateTime.now(), 1, Location.INDOOR, 0);
        String runJson = objectMapper.writeValueAsString(run);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/runs")
                .contentType("application/json")
                .content(runJson))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteRun() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/runs/1"))
                .andExpect(status().isOk());
    }
}
