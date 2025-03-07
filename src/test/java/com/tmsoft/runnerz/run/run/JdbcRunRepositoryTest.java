package com.tmsoft.runnerz.run.run;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(JdbcRunRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JdbcRunRepositoryTest {

    @Autowired
    JdbcRunRepository repository;

    @BeforeEach
    void setUp() {
        repository.create(new Run(1,
                "Testing",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                1,
                Location.OUTDOOR, 0));

        repository.create(new Run(2,
                "Testing 2",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                1,
                Location.INDOOR, 0));
    }

     @Test
    void shouldFindAllRuns() {
        List<Run> runs = repository.findAll();
        assertEquals(2, runs.size(), "Should find 2 runs");
    }

    @Test
    void shouldFindWithValidId() {
        Run run = repository.findById(1).get();
        assertEquals(1, run.id(), "Should find run with id 1");
    }

    @Test
    void shouldNotFindWithInvalidId() {
        NoSuchElementException notFoundException = assertThrows(
            NoSuchElementException.class,
                () -> repository.findById(3).get());
        assertEquals("No value present", notFoundException.getMessage());
    }

    @Test
    void shouldCreateNewRun() {
        repository.create(new Run(3,
                "Testing 3",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                1,
                Location.OUTDOOR, 0));

        List<Run> runs = repository.findAll();
        assertEquals(3, runs.size(), "Should find 3 runs");
    }

    @Test
    void shouldUpdateRun() {
        Run run = repository.findById(1).get();
        run = new Run(1,
                "Testing Updated",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                1,
                Location.OUTDOOR, 0);

        repository.update(run);

        run = repository.findById(1).get();
        assertEquals("Testing Updated", run.title(), "Should update run with id 1");
    }

    @Test
    void shouldDeleteRun() {
        repository.delete(1);

        List<Run> runs = repository.findAll();
        assertEquals(1, runs.size(), "Should find 1 run");
    }

    @Test
    void shouldCountRuns() {
        int count = repository.count();
        assertEquals(2, count, "Should count 2 runs");
    }
}
