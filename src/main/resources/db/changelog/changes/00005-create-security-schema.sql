--liquibase formatted sql

--changeset htmfilho:5
create table if not exists users (
    id       serial       primary key,
    username varchar(100) not null,
    password varchar(100) not null,
    enabled  boolean      not null,
    created  timestamp    not null default current_timestamp,
    constraint uq_username unique (username)
);

create table if not exists authorities (
    id        serial       primary key,
    username  varchar(100) not null,
    authority varchar(50)  not null,
    created   timestamp    not null default current_timestamp,
    constraint fk_authorities_users foreign key (username) references users (username) on delete cascade,
    constraint uq_username_authority unique (username, authority)
);

--rollback drop table authorities; drop table users;