--liquibase formatted sql

--changeset htmfilho:7
alter table table_column add primary_key boolean null;

--rollback alter table table_column drop column primary_key;