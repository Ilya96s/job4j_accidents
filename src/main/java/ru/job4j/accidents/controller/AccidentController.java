package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.springdata.SpringDataAccidentService;
import ru.job4j.accidents.service.springdata.SpringDataAccidentTypeService;
import ru.job4j.accidents.service.springdata.SpringDataRuleService;

import java.util.List;

/**
 * @author Ilya Kaltygin
 */
@Controller
@AllArgsConstructor
public class AccidentController {

    /**
     * Сервис по работе с инцидентами
     */
    private final SpringDataAccidentService springDataAccidentService;

    /**
     * Сервис по работе с типами инцидентов
     */
    private final SpringDataAccidentTypeService springDataAccidentTypeService;

    /**
     * Сервис по работе со статьями
     */
    private final SpringDataRuleService springDataRuleService;

    /**
     * Возвращает страницу для создания инцидента
     *
     * @param model модель с данными
     */
    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", springDataAccidentTypeService.findAllTypes());
        model.addAttribute("rules", springDataRuleService.findAllRules());
        return "accident/createAccident";
    }

    /**
     * Соханяет инцидент в хранилище
     *
     * @param accident инцидент
     * @param model модель с данными
     * @param rIds список идентификаторов статей
     * @return если сохраение прошло успешно, то перенапрвляет на странциу по url "/"
     */
    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident,
                       Model model,
                       @RequestParam("rIds") List<Integer> rIds) {
        if (springDataAccidentService.save(accident, rIds).isEmpty()) {
            model.addAttribute("message", "Не удалось сохранить инцидент");
            return "errors/error";
        }
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
        var accidentOptional = springDataAccidentService.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Инцидент не найден");
            return "errors/error";
        }
        model.addAttribute("accident", accidentOptional.get());
        return "accident/update";
    }

    /**
     * Обновляет инцидент
     *
     * @param accident инцидент
     * @return перенаправляет на страницу по url "/"
     */
    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident,
                         Model model) {
        if (!springDataAccidentService.update(accident.getId())) {
            model.addAttribute("message", "Не удалось обновить инцидент");
            return "errors/error";
        }
        return "redirect:/";
    }

}
