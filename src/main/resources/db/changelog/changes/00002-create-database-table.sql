--liquibase formatted sql

--changeset htmfilho:2
create table if not exists database_table (
    id            serial       primary key,
    datasource    integer      not null,
    name          varchar(100) not null,
    friendly_name varchar(100)     null,
    type          varchar(20)      null,
    documentation text             null,
    total_columns integer          null,
    constraint fk_datasource_table foreign key (datasource) references datasource (id) on delete cascade
);

--rollback drop table if exists database_table;