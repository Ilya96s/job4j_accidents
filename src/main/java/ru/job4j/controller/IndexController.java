package ru.job4j.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.service.ImplAccidentService;

/**
 * IndexController - контроллер для главной страницы
 *
 * @author Ilya Kaltygin
 */
@ThreadSafe
@Controller
@AllArgsConstructor
public class IndexController {

    private final ImplAccidentService implAccidentService;

    /**
     * Возвращает страницу с отображением всех заявлений
     *
     * @param model модель с данными
     * @return страница со всеми заявлениями
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        model.addAttribute("accidents", implAccidentService.findAll());
        return "index";
    }
}
