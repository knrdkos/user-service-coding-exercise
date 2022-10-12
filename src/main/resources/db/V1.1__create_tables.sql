CREATE TABLE users
(
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT,
    name              VARCHAR(320),
    email             VARCHAR(320),
    password          VARCHAR(320),
    email_verified    boolean,
    enabled           boolean,
    verification_code VARCHAR(64),
    created_at        TIMESTAMP,
    updated_at        TIMESTAMP
);

CREATE UNIQUE INDEX index_email ON users (email);