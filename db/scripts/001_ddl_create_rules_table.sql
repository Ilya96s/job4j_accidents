CREATE TABLE IF NOT EXISTS rules (
    id SERIAL PRIMARY KEY,
    name TEXT
);

comment on table rules is 'Таблица со статьями';
comment on column rules.id is 'Идентификатор';
comment on column rules.name is 'Название статьи';