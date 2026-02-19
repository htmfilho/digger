--liquibase formatted sql

--changeset htmfilho:3
create table if not exists table_column (
    id             serial       primary key,
    database_table integer      not null,
    name           varchar(100) not null,
    friendly_name  varchar(100)     null,
    type           varchar(20)  not null,
    size           integer      not null,
    nullable       boolean      not null,
    default_value  varchar(100)     null,
    documentation  text             null,
    foreign_key    integer          null,
    constraint fk_table_column foreign key (database_table) references database_table (id) on delete cascade,
    constraint fk_column_foreign_key foreign key (foreign_key) references table_column (id) on delete set null
);

--rollback drop table if exists table_column;