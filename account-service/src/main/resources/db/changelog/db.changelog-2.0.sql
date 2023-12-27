--liquibase formatted sql

--changeset mmichalenok:7
CREATE TABLE IF NOT EXISTS app.transfers
(
    transaction_uuid uuid PRIMARY KEY,
    account_number uuid NOT NULL,
    currency_code CHAR(3) NOT NULL,
    amount NUMERIC(19, 4) NOT NULL,
    reference_number VARCHAR(50) NOT NULL,
    transaction_type SMALLINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT account_number_transaction_fkey FOREIGN KEY (account_number)
        REFERENCES app.accounts (account_number) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
--rollback DROP TABLE app.transfers;
