--liquibase formatted sql

--changeset pulsarmn:create_auth_users_table
CREATE TABLE auth_users
(
    id            UUID PRIMARY KEY,
    username      VARCHAR(32)  NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    phone_number  VARCHAR(50)  NULL,
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_auth_users_username ON auth_users (username);

--rollback DROP TABLE auth_users;
