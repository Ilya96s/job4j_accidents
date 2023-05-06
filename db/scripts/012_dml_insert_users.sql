insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$A1zDIIl3DIRBPdBL4ZfI8ul61K.5jLmHQnbs156G0WeEY9GiqqKvu',
        (select id from authorities where authority = 'ROLE_ADMIN'));