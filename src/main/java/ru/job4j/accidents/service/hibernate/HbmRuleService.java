package ru.job4j.accidents.service.hibernate;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.hibernate.HbmRuleRepository;
import ru.job4j.accidents.service.RuleService;

import java.util.List;
import java.util.Optional;

/***
 * HbmRuleService - реализация сервиса по работе со статьями
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
@Qualifier("hbmRuleService")
public class HbmRuleService implements RuleService {

    /**
     * Хранилище статей
     */
    private final HbmRuleRepository ruleRepository;

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
