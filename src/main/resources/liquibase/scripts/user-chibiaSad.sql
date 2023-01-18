-- liquibase formatted sql
-- changeset chibiaSad:1
CREATE TABLE users
(
    chat_id          BIGINT          PRIMARY KEY,
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

--changeset chibiaSad:3
DROP TABLE animals;
ALTER TABLE users DROP COLUMN user_age;
ALTER TABLE users ADD COLUMN shelter_type INT;
ALTER TABLE users ADD COLUMN email VARCHAR(30);
ALTER TABLE users ADD COLUMN phone_number VARCHAR(30);

--changeset chibiaSad:4
CREATE SEQUENCE animal_seq START WITH 1;
CREATE SEQUENCE avatar_seq START WITH 1;

CREATE TABLE volunteers
(
    id               SERIAL          PRIMARY KEY,
    chat_id          BIGINT          NOT NULL,
    name             VARCHAR (100)   NOT NULL,
    is_online        BOOLEAN         NOT NULL
);

CREATE TABLE dog_adopters
(
    chat_id          BIGINT          PRIMARY KEY,
    user_name        VARCHAR (100)   NOT NULL,
    user_age         INT             NOT NULL  CHECK (user_age >= 18),
    user_time        TIMESTAMP       NOT NULL,
    email            VARCHAR(50)     NOT NULL,
    phone_number     VARCHAR(20)     NOT NULL,
    address          TEXT            NOT NULL
);

CREATE TABLE dogs
(
    animal_id        SERIAL          PRIMARY KEY,
    register_date    TIMESTAMP       NOT NULL,
    animal_gender    BOOLEAN         NOT NULL,
    animal_name      VARCHAR(100),
    animal_age       INT             NOT NULL  CHECK (animal_age >= 0),
    description      TEXT,
    is_adopted       BOOLEAN         NOT NULL  DEFAULT false,
    chat_id          BIGINT          REFERENCES dog_adopters (chat_id)
);

CREATE TABLE dog_avatars
(
    avatar_id        SERIAL         PRIMARY KEY,
    data             bytea          NOT NULL,
    file_path        text           NOT NULL,
    file_size        BIGINT         NOT NULL,
    media_type       varchar(50)    NOT NULL,
    animal_id        INT            REFERENCES dogs (animal_id)
);

CREATE TABLE cat_adopters
(
    chat_id          BIGINT          PRIMARY KEY ,
    user_name        VARCHAR (100)   NOT NULL,
    user_age         INT             NOT NULL  CHECK (user_age >= 18),
    user_time        TIMESTAMP       NOT NULL,
    email            VARCHAR(50)     NOT NULL,
    phone_number     VARCHAR(20)     NOT NULL,
    address          TEXT            NOT NULL
);

CREATE TABLE cats
(
    animal_id        SERIAL          PRIMARY KEY,
    register_date    TIMESTAMP       NOT NULL,
    animal_gender    BOOLEAN         NOT NULL,
    animal_name      VARCHAR(100),
    animal_age       INT             NOT NULL  CHECK (animal_age >= 0),
    description      TEXT,
    is_adopted       BOOLEAN         NOT NULL  DEFAULT false,
    chat_id          BIGINT          REFERENCES cat_adopters (chat_id)
);

CREATE TABLE cat_avatars
(
    avatar_id        SERIAL         PRIMARY KEY,
    data             bytea          NOT NULL,
    file_path        text           NOT NULL,
    file_size        BIGINT         NOT NULL,
    media_type       varchar(50)    NOT NULL,
    animal_id        INT            REFERENCES cats (animal_id)
);

--changeset chibiaSad:5
ALTER TABLE users ADD COLUMN request_status BOOLEAN DEFAULT false;

--changeset chibiaSad:6
ALTER TABLE cats DROP COLUMN chat_id;
ALTER TABLE dogs DROP COLUMN chat_id;
ALTER TABLE dog_adopters ADD COLUMN animal_id INT REFERENCES dogs(animal_id);
ALTER TABLE cat_adopters ADD COLUMN animal_id INT REFERENCES cats(animal_id);

--changeset chibiaSad:7
DROP TABLE cat_avatars;
DROP TABLE dog_avatars;

--changeset chibiaSad:8
ALTER TABLE dog_adopters ADD COLUMN report_request BOOLEAN DEFAULT false;

ALTER TABLE cat_adopters ADD COLUMN report_request BOOLEAN DEFAULT false;

--changeset chibiaSad:9
CREATE TABLE cat_reports
(
    id              BIGSERIAL       PRIMARY KEY,
    chat_id         BIGINT          NOT NULL REFERENCES cat_adopters (chat_id),
    text            TEXT,
    file_path       VARCHAR(100)
);

CREATE TABLE dog_reports
(
    id              BIGSERIAL       PRIMARY KEY,
    chat_id         BIGINT          NOT NULL REFERENCES dog_adopters (chat_id),
    text            TEXT,
    file_path       VARCHAR(100)
)