package com.tmsoft.runnerz.run.run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    // private final JdbcRunRepository runRepository;

    // public RunController(JdbcRunRepository runRepository) {
    // this.runRepository = runRepository;
    // }

    private final RunRepository runRepository;

    public RunController(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    @GetMapping("")
    List<Run> findAll() {
        return runRepository.findAll();
    }

    @GetMapping("/{id}")
    Run findById(@PathVariable Integer id) {

        Optional<Run> run = runRepository.findById(id);
        if (run.isEmpty()) {
            throw new RunNotFoundException("Run not found");
        }

        return run.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Run run) {
        // runRepository.create(run);
        runRepository.save(run);
    }

    @PutMapping("")
    Run update(@Valid @RequestBody Run run) {
        // Optional<Run> updatedRun = runRepository.update(run);
        // if (updatedRun.isEmpty()) {
        // throw new RunNotFoundException("Run not found");
        // }

        Run updatedRun = runRepository.save(run);
        return updatedRun;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        // runRepository.delete(id);
        runRepository.deleteById(id);
    }

    @GetMapping("/location/{location}")
    List<Run> findByLocation(@PathVariable String location) {
        return runRepository.findAllByLocation(location);
    }
}
