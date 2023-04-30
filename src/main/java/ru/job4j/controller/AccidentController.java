package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.model.Accident;
import ru.job4j.service.AccidentService;

import java.util.Optional;

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
     * @return перенапрвляет на странциу по url "/"
     */
    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidentService.save(accident);
        return "redirect:/";
    }

    /**
     * Возвращает страницу для редактирования инцидента
     *
     * @param id    идентификатор инцидента
     * @param model модель с данными
     * @return если инцидент по заданному идентификатору не найден, то возвращает страницу error, иначе update
     */
    @GetMapping("/formUpdateAccident")
    public String update(@RequestParam("id") int id, Model model) {
        var accidentOptional = accidentService.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Инцидент не найден");
            return "errors/error";
        }
        model.addAttribute("accident", accidentOptional.get());
        return "accident/update";
    }

    /**
     * Обновляет инцидент
     * @param accident инцидент
     * @return перенаправляет на страницу по url "/"
     */
    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        accidentService.update(accident);
        return "redirect:/";
    }

}
