package ru.job4j.accidents.service.springdata;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.springdata.SpringDataRuleRepository;
import ru.job4j.accidents.service.RuleService;

import java.util.List;
import java.util.Optional;

/**
 * SpringDataRuleService - реализация сервиса по работе со статьями
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
@Transactional
public class SpringDataRuleService implements RuleService {

    /**
     * Хранилище статей
     */
    private final SpringDataRuleRepository ruleRepository;

    /**
     * Получить список всех статей
     *
     * @return список всех статей
     */
    @Override
    public List<Rule> findAllRules() {
        return ruleRepository.findAll();
    }

    /**
     * Найти статью по ижентификатору
     *
     * @param id идентификатор статьи
     * @return Optional.of(rule) если статья найдена, иначе Optional.empty()
     */
    @Override
    public Optional<Rule> findRuleById(int id) {
        return ruleRepository.findById(id);
    }
}
