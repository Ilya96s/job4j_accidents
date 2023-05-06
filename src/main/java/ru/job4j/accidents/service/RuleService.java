package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;

/**
 * RuleService - сервис, описывающий бизнес логику по работе со статьями
 *
 * @author Ilya Kaltygin
 */
public interface RuleService {

    /**
     * Получить список всех статей
     *
     * @return список всех статей
     */
    List<Rule> findAllRules();

    /**
     * Найти статью по ижентификатору
     *
     * @param id идентификатор статьи
     * @return Optional.of(rule) если статья найдена, иначе Optional.empty()
     */
    Optional<Rule> findRuleById(int id);
}
