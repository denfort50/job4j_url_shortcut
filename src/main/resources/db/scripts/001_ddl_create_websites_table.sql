CREATE TABLE IF NOT EXISTS websites (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    login VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    reg_status BOOLEAN DEFAULT TRUE
);