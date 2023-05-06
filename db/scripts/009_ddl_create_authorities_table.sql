CREATE TABLE authorities
(
    id        serial primary key,
    authority VARCHAR(50) NOT NULL unique
);

comment on table authorities is 'Роли пользователей';
comment on column authorities.id is 'Идентификатор';
comment on column authorities.authority is 'Роль';