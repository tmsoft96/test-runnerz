package com.tmsoft.runnerz.run.run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Run(
        @Id Integer id,
        @NotEmpty String title,

        LocalDateTime startedDateTime,
        LocalDateTime endDateTime,
        @Positive Integer miles,
        Location location,
        @Version Integer version) {
    public Run {
        // if (id == null) {
        // throw new IllegalArgumentException("id is required");
        // }
        if (title == null) {
            throw new IllegalArgumentException("title is required");
        }
        if (startedDateTime == null) {
            throw new IllegalArgumentException("startDateTime is required");
        }
        if (endDateTime == null) {
            throw new IllegalArgumentException("endDateTime is required");
        }
        if (miles == null) {
            throw new IllegalArgumentException("miles is required");
        }
        if (location == null) {
            throw new IllegalArgumentException("location is required");
        }
    }
}
