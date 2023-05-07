package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.springdata.SpringDataUserService;

/**
 * RegController - контроллер, отвечающий за обработку регистрации пользователя
 *
 * @author Ilya Kaltygin
 */
@Controller
@AllArgsConstructor
public class RegController {

    /**
     * Сервис по работе с пользователями
     */
    private final SpringDataUserService springDataUserService;

    /**
     * Возвращает пользователю страцниу с формой регистрации
     *
     * @param error параметр запроса error (если не удалось зарегистрировать пользователя)
     * @return страница с формой регистрации
     */
    @GetMapping("/reg")
    public String regPage(@RequestParam(value = "error", required = false) String error,
                          Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Не удалось зарегистрировать пользователя");
        }
        return "users/reg";
    }

    /**
     * Добавляет пользователя в базу данных
     *
     * @param user пользоавтель
     * @return перенаправляет пользователя по url "/login" если пользователь успешно зарегистрирован, иначе перенаправляет по url "/reg" с параметром "?error=true"
     */
    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user) {
        var optionalUser = springDataUserService.save(user);
        if (optionalUser.isEmpty()) {
            return "redirect:/reg?error=true";
        }
        return "redirect:/login";
    }

}
