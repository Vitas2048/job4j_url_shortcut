CREATE TABLE site (
    id serial primary key not null,
    site text unique,
    login text unique,
    password text
);

