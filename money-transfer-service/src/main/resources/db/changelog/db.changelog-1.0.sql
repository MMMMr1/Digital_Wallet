--liquibase formatted sql

--changeset mmichalenok:1
CREATE SCHEMA IF NOT EXISTS app
    AUTHORIZATION postgres;

--changeset mmichalenok:2

CREATE TABLE IF NOT EXISTS app.transfers
(
    id uuid PRIMARY KEY,
    account_to uuid NOT NULL,
    reference_number VARCHAR(64) NOT NULL,
    amount VARCHAR(64) NOT NULL,
    currency_code VARCHAR(64) NOT NULL,
    status SMALLINT NOT NULL,
    type SMALLINT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
--rollback DROP TABLE app.transfers;