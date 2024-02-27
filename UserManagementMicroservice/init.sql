CREATE EXTENSION IF NOT EXISTS "pgcrypto";

create table if not exists "user"(
    user_id uuid primary key default gen_random_uuid(),
    name varchar(50) not null,
    email varchar(30) not null,
    password text not null,
    role varchar(10) not null
    );