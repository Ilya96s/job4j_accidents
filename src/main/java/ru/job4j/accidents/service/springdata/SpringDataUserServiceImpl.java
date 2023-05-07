package ru.job4j.accidents.service.springdata;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.springdata.SpringDataAuthorityRepository;
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

    private static final Logger LOG = LoggerFactory.getLogger(SpringDataUserServiceImpl.class);

    /**
     * Объект для хэширования паролей
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Хранилище пользователей
     */
    private final SpringDataUserRepository springDataUserRepository;

    /**
     * Хранилище ролей
     */
    private final SpringDataAuthorityRepository springDataAuthorityRepository;

    /**
     * Сохранить пользователя в базу данных
     *
     * @param user пользователь
     * @return Optional.of(user) если пользователь успешно сохранен, иначе Optional.empty()
     */
    @Override
    public Optional<User> save(User user) {
        Optional<User> rsl = Optional.empty();
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority(springDataAuthorityRepository.findByAuthority("ROLE_USER").get());
        try {
            springDataUserRepository.save(user);
            rsl = Optional.of(user);
        } catch (Exception e) {
            LOG.error("Exception in the save(User user) method", e);
        }
        return rsl;
    }
}
