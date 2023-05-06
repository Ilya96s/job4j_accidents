package ru.job4j.accidents.repository.springdata;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Authority;

import java.util.Optional;

/**
 * AuthorityRepository - хранилище ролей, реализованное с помощью Spring Data Jpa
 *
 * @author Ilya Kaltygin
 */
public interface SpringDataAuthorityRepository extends CrudRepository<Authority, Integer> {

    /**
     * Найти роль по названию
     *
     * @param authority название роли
     * @return объект типа Authority
     */
    Optional<Authority> findByAuthority(String authority);
}
