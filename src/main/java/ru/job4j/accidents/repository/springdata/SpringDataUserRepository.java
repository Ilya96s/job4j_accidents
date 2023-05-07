package ru.job4j.accidents.repository.springdata;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.User;

/**
 * UserRepository - хранилище пользователей, реализованное с помощью Spring Data Jpa
 *
 * @author Ilya Kaltygin
 */
public interface SpringDataUserRepository extends CrudRepository<User, Integer> {
}
