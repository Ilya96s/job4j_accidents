CREATE TABLE users
(
    id           serial primary key,
    username     VARCHAR(50)  NOT NULL unique,
    password     VARCHAR(100) NOT NULL,
    enabled      boolean default true,
    authority_id int          not null references authorities (id)
);

comment on table users is 'Пользователи';
comment on column users.id is 'Идентификатор';
comment on column users.username is 'логин';
comment on column users.password is 'Пароль';
comment on column users.enabled is 'Статус активности';
comment on column users.authority_id is 'Идентификатор роли';