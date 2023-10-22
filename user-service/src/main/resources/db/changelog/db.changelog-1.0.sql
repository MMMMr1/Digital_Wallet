--liquibase formatted sql

--changeset mmichalenok:1
CREATE SCHEMA IF NOT EXISTS app
    AUTHORIZATION postgres;

--changeset mmichalenok:2

CREATE TABLE IF NOT EXISTS app.users
(
    uuid         uuid PRIMARY KEY,
    mail   VARCHAR(64)   NOT NULL UNIQUE,
    mobile_phone   VARCHAR(64)   NOT NULL UNIQUE,
    password   VARCHAR(2048) NOT NULL,
    role       VARCHAR(32)   NOT NULL,
    status       VARCHAR(32)   NOT NULL,
    dtcreate TIMESTAMP,
    dtupdate TIMESTAMP
);
--rollback DROP TABLE app.users;