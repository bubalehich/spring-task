create table public.audit_log
(
    id        serial primary key,
    clazz     varchar(255),
    entity_id integer,
    operation varchar(255),
    timestamp timestamp
);

create table public.gift_certificates
(
    id               serial primary key,
    name             varchar(255),
    create_date      varchar(255),
    description      varchar(255),
    duration         integer          not null,
    last_update_date varchar(255),
    price            double precision not null
);

create table public.tags
(
    id   serial primary key,
    name varchar(255)
);

create table public.gift_certificate_tag
(
    tag_id              integer not null
        constraint fkmr23931dgei7nmo4f7of22y5i
            references public.tags,
    gift_certificate_id integer not null
        constraint fknc8h0nbf6mrgvowcw9uqfrayd
            references public.gift_certificates,
    primary key (gift_certificate_id, tag_id)
);

create table public.users
(
    id         serial primary key,
    first_name varchar(255),
    last_name  varchar(255)
);

create table public.orders
(
    id                  serial primary key,
    cost                double precision not null,
    purchase_date       varchar(255),
    gift_certificate_id integer
        constraint fkl0qgtheicxemx7dc9262tp5fy
            references public.gift_certificates,
    user_id             integer
        constraint fk32ql8ubntj5uh44ph9659tiih
            references public.users
);
