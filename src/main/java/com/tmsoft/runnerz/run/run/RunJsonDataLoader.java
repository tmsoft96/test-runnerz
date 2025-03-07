package com.tmsoft.runnerz.run.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class RunJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RunJsonDataLoader.class);
    // private final JdbcRunRepository runRepository;
    private final RunRepository runRepository;
    private final ObjectMapper objectMapper;

    // public RunJsonDataLoader(JdbcRunRepository runRepository, ObjectMapper objectMapper) {
    //     this.runRepository = runRepository;
    //     this.objectMapper = objectMapper;
    // }

    public RunJsonDataLoader(RunRepository runRepository, ObjectMapper objectMapper) {
        this.runRepository = runRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (runRepository.count() == 0) {
            log.info("Loading data from JSON file");
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/runs.json")) {
                Runs runs = objectMapper.readValue(inputStream, Runs.class);
                log.info("Loaded {} runs", runs.runs().size());
               runRepository.saveAll(runs.runs());
            } catch (Exception e) {
                log.error("Failed to load data from JSON file", e);
            }
        } else {
            log.info("Not loading Runs from JSON file because the collection is not empty");
        }
    }
}
