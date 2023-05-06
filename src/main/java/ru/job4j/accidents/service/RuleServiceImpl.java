package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.List;
import java.util.Optional;

/**
 * RuleServiceImpl - реализация сервиса по работе со статьями
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
public class RuleServiceImpl implements RuleService {

    /**
     * Временное хранилище
     */
    private final AccidentMem ruleRepository;

    /**
     * Получить список всех статей
     *
     * @return список всех статей
     */
    @Override
    public List<Rule> findAllRules() {
        return ruleRepository.findAllRules();
    }

    /**
     * Найти статью по ижентификатору
     *
     * @param id идентификатор статьи
     * @return Optional.of(rule) если статья найдена, иначе Optional.empty()
     */
    @Override
    public Optional<Rule> findRuleById(int id) {
        return ruleRepository.findRuleById(id);
    }
}
