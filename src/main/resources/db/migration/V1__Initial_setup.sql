CREATE TABLE IF NOT EXISTS users (
    patient_id bigint not null PRIMARY KEY,
    name varchar(255) not null,
    gender varchar(50) not null,
    age int not null,
    email varchar(50),
    phone_number varchar(50) not null,
    created_at timestamp not null,
    updated_at timestamp not null
);
