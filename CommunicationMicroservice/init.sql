CREATE EXTENSION IF NOT EXISTS "pgcrypto";

DROP TABLE IF EXISTS energy_data;

create table energy_data(
    data_id uuid primary key default gen_random_uuid(),
    timestamp bigint not null unique,
    device_id uuid not null,
    measurement_value real not null
);