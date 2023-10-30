--liquibase formatted sql

--changeset mmichalenok:1
CREATE SCHEMA IF NOT EXISTS app
    AUTHORIZATION postgres;

--changeset mmichalenok:2

CREATE TABLE IF NOT EXISTS app.users
(
    uuid uuid PRIMARY KEY,
    mail VARCHAR(64) NOT NULL UNIQUE,
    mobile_phone VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    status VARCHAR(32) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
--rollback DROP TABLE app.users;

--changeset mmichalenok:3
CREATE TABLE IF NOT EXISTS app.user_role
(
    user_uuid uuid NOT NULL,
    role  VARCHAR(32) ,
    CONSTRAINT user_role_pkey PRIMARY KEY (user_uuid, role),
    CONSTRAINT user_role_enum CHECK (role IN ('ADMIN', 'USER'))
);
--rollback DROP TABLE app.user_role;

