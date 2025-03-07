package com.tmsoft.runnerz.run.run;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Repository
public class JdbcRunRepository {
    private static final Logger log = Logger.getLogger(JdbcRunRepository.class.getName());
    private final JdbcClient jdbcClient;

    public JdbcRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll() {
        return jdbcClient.sql("select * from Run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> findById(Integer id) {
        return jdbcClient.sql(
                "select id, title, started_date_time, end_date_time, miles, location, version from Run where id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    public void create(Run run) {
        var updated = jdbcClient.sql(
                "insert into Run (title, started_date_time, end_date_time, miles, location, version) values (?, ?, ?, ?, ?, ?)")
                .params(List.of(run.title(), run.startedDateTime(), run.endDateTime(), run.miles(),
                        run.location().name()))
                .update();

        Assert.state(updated == 1, "Failed to create run " + run.title());
    }

    public Optional<Run> update(Run run) {
        var updated = jdbcClient.sql(
                "update Run set title = ?, started_date_time = ?, end_date_time = ?, miles = ?, location = ? version = ? where id = ?")
                .params(List.of(run.title(), run.startedDateTime(), run.endDateTime(), run.miles(),
                        run.location().name(), run.version(), run.id()))
                .update();

        Assert.state(updated == 1, "Failed to update run " + run.title());

        return Optional.of(run);
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from Run where id = ?")
                .params(List.of(id))
                .update();

        Assert.state(updated == 1, "Failed to delete run " + id);
    }

    public int count() {
        return jdbcClient.sql("select * from Run")
                .query()
                .listOfRows()
                .size();
    }

    public void saveAll(List<Run> runs) {
        for (Run run : runs) {
            create(run);
        }
    }

}
