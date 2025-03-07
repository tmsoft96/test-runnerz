 package com.tmsoft.runnerz.run.run;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface RunRepository extends ListCrudRepository<Run, Integer> {

    // custom query method
    List<Run> findAllByLocation(String location);

    // OR
    // @Query("SELECT * FROM run WHERE location = :location")
    // List<Run> findAllByLocation(String location);
} 