package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;

/**
 * RuleRepository - хранилище статей
 *
 * @author Ilya Kaltygin
 */
public interface RuleRepository {

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
