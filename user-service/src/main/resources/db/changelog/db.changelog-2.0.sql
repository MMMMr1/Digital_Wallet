--liquibase formatted sql

--changeset mmichalenok:1

CREATE TABLE IF NOT EXISTS app.verification
(
    mail VARCHAR(64) PRIMARY KEY,
    code VARCHAR(64) NOT NULL
)
--rollback DROP TABLE app.verification;