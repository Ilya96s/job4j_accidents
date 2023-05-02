CREATE TABLE IF NOT EXISTS accidents_rules (
    id SERIAL PRIMARY KEY ,
    accident_id INT REFERENCES accidents(id) NOT NULL,
    rule_id INT REFERENCES rules(id) NOT NULL
);

comment on table accidents_rules is 'Таблица для связи Many-to-many инциденты и статьи';
comment on column accidents_rules.id is 'Идентификатор';
comment on column accidents_rules.accident_id is 'Идентификатор инцидента';
comment on column accidents_rules.rule_id is 'Идентификатор статьи';