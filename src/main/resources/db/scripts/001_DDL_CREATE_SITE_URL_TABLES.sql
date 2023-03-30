CREATE TABLE site (
    id serial primary key not null,
    site text unique,
    login text unique,
    password text
);

CREATE TABLE url (
    id serial primary key not null,
    url text,
    code text,
    count int
);