package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.springdata.SpringDataAuthorityService;
import ru.job4j.accidents.service.springdata.SpringDataUserService;

import java.util.Optional;

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
     * Сервис по работе с пользователями
     */
    private final SpringDataUserService springDataUserService;

    /**
     * Сервис по работе с ролями
     */
    private final SpringDataAuthorityService springDataAuthorityService;

    /**
     * Возвращает пользователю страцниу с формой регистрации
     *
     * @param login параметр запроса login (если логин, который ввел пользователь уже существует в базе данных)
     * @return страница с формой регистрации
     */
    @GetMapping("/reg")
    public String regPage(@RequestParam(value = "login", required = false) String login,
                          Model model) {
        if (login != null) {
            model.addAttribute("errorMessage", String.format("Пользователь с логином %s уже существует", login));
        }
        return "users/reg";
    }

    /**
     * Добавляет пользователя в базу данных
     *
     * @param user пользоавтель
     * @return перенаправляет пользователя по url "/login" если пользователь успешно зарегистрирован.
     * Если пользователь ввел логин,который уже существует в базе данных, то происходит перенаправление по url "/reg" с параметром "login=%s", где %s введенный пользователем логин.
     */
    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user) {
        var optionalUser = springDataUserService.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            return String.format("redirect:/reg?login=%s", user.getUsername());
        }
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority(springDataAuthorityService.findByAuthority("ROLE_USER").get());
        springDataUserService.save(user);
        return "redirect:/login";
    }

}
