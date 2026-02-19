--liquibase formatted sql

--changeset htmfilho:4
create table if not exists ignored_table (
    id             serial       primary key,
    datasource     integer      not null,
    name           varchar(100) not null,
    constraint fk_datasource_ignored_table foreign key (datasource) references datasource (id) on delete cascade
);

--rollback drop table if exists ignored_table;