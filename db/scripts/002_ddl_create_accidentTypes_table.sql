CREATE TABLE IF NOT EXISTS accidentTypes (
    id SERIAL PRIMARY KEY,
    name TEXT
);

comment on table accidentTypes is 'Таблица с типами инцидентов';
comment on column accidentTypes.id is 'Идентификатор';
comment on column accidentTypes.name is 'Тип инцидента';