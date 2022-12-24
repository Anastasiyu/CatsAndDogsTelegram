-- liquibase formatted sql
-- changeset chibiaSad:1
CREATE TABLE users
(
    chat_id          BIGINT          PRIMARY KEY ,
    user_name        VARCHAR (100)   NOT NULL,
    user_age         INT             NOT NULL,
    user_time        TIMESTAMP       NOT NULL
);

--changeset chibiaSad:2
CREATE TABLE animals
(
    animal_id        SERIAL          PRIMARY KEY,
    register_date    TIMESTAMP       NOT NULL,
    dtype            VARCHAR         NOT NULL,
    animal_gender    BOOLEAN         NOT NULL,
    animal_name      VARCHAR(100),
    animal_age       INT             NOT NULL,
    description      TEXT,
    chat_id          BIGINT          REFERENCES users (chat_id)
);

