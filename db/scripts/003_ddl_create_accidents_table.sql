CREATE TABLE IF NOT EXISTS accidents (
    id SERIAL PRIMARY KEY,
    name TEXT,
    text TEXT,
    address TEXT,
    type_id INT REFERENCES accidentTypes(id) NOT NULL
);

comment on table accidents is 'Таблица с инцидентами';
comment on column accidents.id is 'Идентификатор';
comment on column accidents.name is 'Название инцидента';
comment on column accidents.text is 'Описание инцидента';
comment on column accidents.address is 'Адрес инцидента';
comment on column accidents.type_id is 'Идентификатор типа инцидента';