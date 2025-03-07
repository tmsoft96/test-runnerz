package com.tmsoft.runnerz.run.run;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class InMemoryRunRepository {
    private static final Logger log = Logger.getLogger(InMemoryRunRepository.class.getName());
    private List<Run> runs = new ArrayList<>();

    List<Run> findAll() {
        return runs;
    }

    Optional<Run> findById(Integer id) {
        for (Run run : runs) {
            if (run.id() == id) {
                return Optional.of(run);
            }
        }
        return Optional.empty();
    }

    void create(Run run) {
        runs.add(run);
    }

    Optional<Run> update(Run run) {
        Optional<Run> existingRun = findById(run.id());
        if (existingRun.isPresent()) {
            runs.remove(existingRun.get());
            runs.add(run);
            return Optional.of(run);
        }

        return Optional.empty();
    }

    void delete(Integer id) {
        Optional<Run> existingRun = findById(id);

        if (existingRun.isPresent()) {
            runs.remove(existingRun.get());
        } else {
            throw new RunNotFoundException("Run not found");
        }

    }

    @PostConstruct
    private void init() {
        runs.add(new Run(1,
                "Testing",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                1,
                Location.OUTDOOR,  0));

        runs.add(new Run(2,
                "Testing 2",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                1,
                Location.INDOOR, 0));
    }
}
