--liquibase formatted sql

--changeset htmfilho:1
create table if not exists datasource (
    id           serial       primary key,
    name         varchar(100) not null,
    description  varchar(300)     null,
    driver       varchar(200) not null,
    url          varchar(200) not null,
    username     varchar(50)      null,
    password     varchar(50)      null,
    total_tables integer          null
);

--rollback drop table if exists datasource;