package ru.job4j.accidents.service.springdata;

import ru.job4j.accidents.model.Authority;

import java.util.Optional;

/**
 * AuthorityService - интерфейс, описывающий бизнес логику по работе с ролями пользователей
 *
 * @author Ilya Kaltygin
 */
public interface SpringDataAuthorityService {

    /**
     * Найти роль по названию
     *
     * @param authority название роли
     * @return объект типа Authority
     */
    Optional<Authority> findByAuthority(String authority);
}
