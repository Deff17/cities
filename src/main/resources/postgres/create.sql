CREATE TABLE IF NOT EXISTS cities (
    id BIGINT NOT NULL,
    name TEXT NOT NULL,
    photo TEXT NOT NULL,

    PRIMARY KEY (id, name)
);