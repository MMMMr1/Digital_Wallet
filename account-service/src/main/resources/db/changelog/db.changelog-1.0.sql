--liquibase formatted sql

--changeset mmichalenok:5
CREATE SCHEMA IF NOT EXISTS app
    AUTHORIZATION postgres;

--changeset mmichalenok:6

CREATE TABLE IF NOT EXISTS app.accounts
(
    account_number uuid PRIMARY KEY,
    client_id uuid NOT NULL,
    currency_code CHAR(3) NOT NULL,
    current_balance NUMERIC(19, 4) NOT NULL,
    open_date DATE NOT NULL,
    close_date DATE,
    max_limit NUMERIC(19, 4) NOT NULL,
    is_active BOOLEAN NOT NULL
        CONSTRAINT positive_balance_check CHECK ( current_balance >= 0 ),
        CONSTRAINT max_limit_balance_check CHECK ( current_balance <= accounts.max_limit ),
        CONSTRAINT date_check CHECK (close_date > accounts.open_date)
);
--rollback DROP TABLE app.accounts;
