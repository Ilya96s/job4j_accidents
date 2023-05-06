package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.springdata.AuthorityRepository;
import ru.job4j.accidents.repository.springdata.UserRepository;

/**
 * RegController - контроллер, отвечающий за обработку регистрации пользователя
 *
 * @author Ilya Kaltygin
 */
@Controller
@AllArgsConstructor
public class RegController {

    /**
     * Объект для хэширования паролей
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Хранилище пользователей
     */
    private final UserRepository userRepository;

    /**
     * Хранилище ролей
     */
    private final AuthorityRepository authorityRepository;

    /**
     * Возвращает пользователю страцниу с формой регистрации
     */
    @GetMapping("/reg")
    public String regPage() {
        return "users/reg";
    }

    /**
     * Добавляет пользователя в базу данных
     * @param user пользоавтель
     * @return перенаправляет пользователя по url "/login"
     */
    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user) {
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority(authorityRepository.findByAuthority("ROLE_USER"));
        userRepository.save(user);
        return "redirect:/login";
    }

}
