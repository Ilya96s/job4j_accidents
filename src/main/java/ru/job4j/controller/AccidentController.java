package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.model.Accident;
import ru.job4j.service.AccidentService;

/**
 * @author Ilya Kaltygin
 */
@Controller
@AllArgsConstructor
public class AccidentController {

    /**
     * Сервис по работе с инцидентами
     */
    private final AccidentService accidentService;

    /**
     * Возвращает странциу для создания инцидента
     */
    @GetMapping("/createAccident")
    public String viewCreateAccident() {
        return "createAccident";
    }

    /**
     * Соханяет инцидент в хранилище
     *
     * @param accident инцидент
     * @return перенапрвляет на странциу по url "/index"
     */
    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidentService.save(accident);
        return "redirect:/index";
    }

}
