package ru.job4j.service;

import ru.job4j.model.Rule;

import java.util.List;
import java.util.Optional;

/**
 * RuleServiceImpl - реализация сервиса по работе со статьями
 *
 * @author Ilya Kaltygin
 */
public class RuleServiceImpl implements RuleService {

    /**
     * Получить список всех статей
     *
     * @return список всех статей
     */
    @Override
    public List<Rule> findAllRules() {
        return null;
    }

    /**
     * Найти статью по ижентификатору
     *
     * @param id идентификатор статьи
     * @return Optional.of(rule) если статья найдена, иначе Optional.empty()
     */
    @Override
    public Optional<Rule> findRuleById(int id) {
        return Optional.empty();
    }
}
