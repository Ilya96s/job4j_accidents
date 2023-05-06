package ru.job4j.accidents.service.springdata;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.repository.springdata.SpringDataAuthorityRepository;

import java.util.Optional;

/**
 * SpringDataAuthorityServiceImpl - реализация сервиса по работе с ролями пользователей
 *
 * @author Ilya KAltygin
 */
@Service
@AllArgsConstructor
public class SpringDataAuthorityServiceImpl implements SpringDataAuthorityService {

    /**
     * Хранилище ролей
     */
    private final SpringDataAuthorityRepository springDataAuthorityRepository;

    /**
     * Найти роль по названию
     *
     * @param authority название роли
     * @return объект типа Authority
     */
    @Override
    public Optional<Authority> findByAuthority(String authority) {
        return springDataAuthorityRepository.findByAuthority(authority);
    }
}
