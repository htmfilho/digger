--liquibase formatted sql

--changeset htmfilho:6
alter table users add first_name varchar(30) null;
alter table users add last_name varchar(30) null;

--rollback alter table users drop column first_name; alter table users drop column last_name;