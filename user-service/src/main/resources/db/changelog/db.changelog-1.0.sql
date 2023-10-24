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
    dt_create TIMESTAMP,
    dt_update TIMESTAMP
);
--rollback DROP TABLE app.users;

--changeset mmichalenok:3
CREATE TABLE IF NOT EXISTS app.user_role
(
    user_uuid uuid NOT NULL,
    role  VARCHAR(32) ,
    CONSTRAINT user_role_pkey PRIMARY KEY (user_uuid, role)
);
--rollback DROP TABLE app.user_status;



----changeset mmichalenok:3

--CREATE TABLE IF NOT EXISTS app.user_status
--(
--    user_id uuid NOT NULL,
--    status  VARCHAR(32) ,
--    CONSTRAINT user_status_pkey PRIMARY KEY (user_id)
--);
----rollback DROP TABLE app.user_status;