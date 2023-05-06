package ru.job4j.accidents.service.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.jdbc.JdbcRuleRepository;
import ru.job4j.accidents.service.RuleService;

import java.util.List;
import java.util.Optional;

/**
 * JdbcRuleService - релаизация среивса по работе со статьями с помощью JdbcTemplate
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
public class JdbcRuleService implements RuleService {

    /**
     * Хранилище статей
     */
    private final JdbcRuleRepository jdbcRuleRepository;

    /**
     * Получить список всех статей
     *
     * @return список всех статей
     */
    @Override
    public List<Rule> findAllRules() {
        return jdbcRuleRepository.findAllRules();
    }

    /**
     * Найти статью по идентификатору
     *
     * @param id идентификатор статьи
     * @return Optional.of(rule) если статья найдена, иначе Optional.empty()
     */
    @Override
    public Optional<Rule> findRuleById(int id) {
        return jdbcRuleRepository.findRuleById(id);
    }
}
