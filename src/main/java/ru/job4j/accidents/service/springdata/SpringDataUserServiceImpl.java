package ru.job4j.accidents.service.springdata;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.springdata.SpringDataUserRepository;

import java.util.Optional;

/**
 * SpringDataUserServiceImpl - реализация сервсиа по работе с пользователями
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
public class SpringDataUserServiceImpl implements SpringDataUserService {

    /**
     * Хранилище пользователей
     */
    private final SpringDataUserRepository springDataUserRepository;

    /**
     * Сохранить пользователя в базу данных
     *
     * @param user пользователь
     * @return сохраненный пользователь
     */
    @Override
    public User save(User user) {
        return springDataUserRepository.save(user);
    }

    /**
     * Найти пользователя по логину
     *
     * @param username логин
     * @return Optional.of(user) если пользователь найден, иначе Optional.empty()
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return springDataUserRepository.findByUsername(username);
    }
}
