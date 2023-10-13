create table if not exists users
(
    username varchar(50)  not null
    primary key,
    password varchar(500) not null,
    enabled  boolean      not null
    );

create table if not exists authorities
(
    username  varchar(50) not null
    constraint fk_authorities_users
    references users,
    authority varchar(50) not null
    );

create unique index if not exists ix_auth_username
    on authorities (username, authority);

create table if not exists phone_model
(
    id                       serial
    constraint phone_model_pk
    primary key,
    brand                    text                  not null,
    device                   text                  not null,
    technology               text,
    bands2g                  text,
    bands3g                  text,
    bands4g                  text,
    updated_with_external_at timestamp,
    update_with_external     boolean default true  not null,
    deleted                  boolean default false not null
);

create index if not exists phone_model_updated_with_external_at_index
    on phone_model (updated_with_external_at);

create table if not exists phone
(
    id             serial
    constraint phone_pk
    primary key,
    name           text                  not null,
    phone_model_id integer               not null
    constraint phone_phone_model_id_fk
    references phone_model,
    deleted        boolean default false not null
);

create unique index if not exists phone_name_uindex
    on phone (name)
    where (deleted = false);

create table if not exists phone_booking
(
    id         serial
    constraint phone_booking_pk
    primary key,
    username   varchar(50)             not null,
    phone_id   integer                 not null
    constraint phone_booking_phone_id_fk
    references phone,
    startpoint timestamp               not null,
    endpoint   timestamp               not null,
    deleted    boolean   default false not null,
    created_at timestamp default now() not null
    );

create index if not exists phone_booking_phone_id_startpoint_index
    on phone_booking (phone_id, startpoint);

create index if not exists phone_booking_endpoint_index
    on phone_booking (endpoint);
