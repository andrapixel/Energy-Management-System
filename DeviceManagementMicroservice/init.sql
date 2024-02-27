CREATE EXTENSION IF NOT EXISTS "pgcrypto";

create table if not exists device(
    device_id uuid primary key default gen_random_uuid(),
    description varchar(255) not null,
    address varchar(255) not null,
    max_hourly_energy_consumption double precision not null,
    user_id uuid
);