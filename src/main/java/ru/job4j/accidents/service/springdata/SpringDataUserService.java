package ru.job4j.accidents.service.springdata;

import ru.job4j.accidents.model.User;

import java.util.Optional;

/**
 * UserService - интерфейс, описывающий бизнес логику по работе с пользователями
 *
 * @author Ilya Kaltygin
 */
public interface SpringDataUserService {

    /**
     * Сохранить пользователя в базу данных
     *
     * @param user пользователь
     * @return сохраненный пользователь
     */
    User save(User user);

    /**
     * Найти пользователя по логину
     *
     * @param username логин
     * @return Optional.of(user) если пользователь найден, иначе Optional.empty()
     */
    Optional<User> findByUsername(String username);
}
