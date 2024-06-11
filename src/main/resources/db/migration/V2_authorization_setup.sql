CREATE SEQUENCE IF NOT EXISTS authorization_id_seq;

CREATE TABLE IF NOT EXISTS "user" (
    id bigint not null PRIMARY KEY DEFAULT nextval('authorization_id_seq'),
    username varchar(255) not null unique,
    password varchar(255) not null,
    role varchar(50) not null
);