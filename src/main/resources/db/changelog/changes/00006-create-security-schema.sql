--liquibase formatted sql

--changeset htmfilho:6
create table if not exists users (
    username varchar(100) not null primary key,
    password varchar(100) not null,
    enabled  boolean      not null
);

create table if not exists authorities (
    username  varchar(100) not null,
    authority varchar(50)  not null,
    constraint fk_authorities_users foreign key (username) references users (username),
    constraint ix_auth_username unique (username, authority)
);

create table if not exists groups (
    id         integer primary key auto_increment,
    group_name varchar(50) not null
);

create table if not exists group_authorities (
    group_id  integer not null,
    authority varchar(50) not null,
    constraint fk_group_authorities_group foreign key (group_id) references groups (id)
);

create table if not exists group_members (
    id       integer      primary key auto_increment,
    username varchar(100) not null,
    group_id integer      not null,
    constraint fk_group_members_group foreign key (group_id) references groups (id)
);