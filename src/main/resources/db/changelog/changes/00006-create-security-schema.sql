--liquibase formatted sql

--changeset htmfilho:6
create table if not exists users (
    id       integer      primary key auto_increment,
    username varchar(100) not null,
    password varchar(100) not null,
    enabled  boolean      not null,
    created  datetime     not null default current_timestamp,
    constraint uq_username unique (username)
);

create table if not exists authorities (
    id        integer      primary key auto_increment,
    username  varchar(100) not null,
    authority varchar(50)  not null,
    created  datetime      not null default current_timestamp,
    constraint fk_authorities_users foreign key (username) references users (username),
    constraint uq_username_authority unique (username, authority)
);

--rollback drop table authorities; drop table users; drop table group_authorities; drop table group_members; drop table groups;
