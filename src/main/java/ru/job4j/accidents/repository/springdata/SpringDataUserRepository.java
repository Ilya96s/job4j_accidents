package ru.job4j.accidents.repository.springdata;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.User;

import java.util.Optional;

/**
 * UserRepository - хранилище пользователей, реализованное с помощью Spring Data Jpa
 *
 * @author Ilya Kaltygin
 */
public interface SpringDataUserRepository extends CrudRepository<User, Integer> {

    /**
     * Найти пользователя по логину
     *
     * @param username логин
     * @return Optional.of(user) если пользователь найден, иначе Optional.empty()
     */
    Optional<User> findByUsername(String username);
}
