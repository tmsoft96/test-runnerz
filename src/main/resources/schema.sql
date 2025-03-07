-- DROP TABLE IF EXISTS Run;

CREATE TABLE IF NOT EXISTS Run (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    started_date_time timestamp NOT NULL,
    end_date_time timestamp NOT NULL,
    miles INT NOT NULL,
    location VARCHAR(10) NOT NULL,
    version INT
);