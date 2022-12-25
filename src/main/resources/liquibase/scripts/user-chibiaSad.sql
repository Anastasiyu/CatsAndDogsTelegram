-- liquibase formatted sql
-- changeset safiulina:1

CREATE TABLE Dogs
(
    animalId   SERIAL,
    registerAnimals TIMESTAMP,
    animalName VARCHAR(100),
    animalAge INT,
    description TEXT);                     -- описание

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE Dogs
(
    chatId            BIGINT NOT NULL,
    user_id        BIGINT,
    CONSTRAINT pk_notification_task PRIMARY KEY (chatId)
);
-- changeset safiulina:2 добавление таблицы
CREATE TABLE "Users" (
                        chatId int4 primary key,         -- id
                        userName varchar (100) NOT NULL, -- имя пользователя
                        userAge int,
                        userTime Timestamp

);