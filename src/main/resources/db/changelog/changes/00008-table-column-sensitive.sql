--liquibase formatted sql

--changeset htmfilho:8
alter table table_column add sensitive boolean null;

--rollback alter table table_column drop column sensitive;