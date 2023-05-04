package ru.job4j.repository.hibernate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Rule;
import ru.job4j.repository.RuleRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * HbmRuleRepository - реализация хранилища статей с помощью Hibernate
 *
 * @author Ilya Kaltygin
 */
@Repository
@AllArgsConstructor
public class HbmRuleRepository implements RuleRepository {

    /**
     * Реализация паттерна Command
     */
    private final CrudRepository crudRepository;

    private static final String FIND_ALL = """
            From Rule
            """;

    private static final String FIND_BY_ID = """
            From Rule as rule
            WHERE rule.id = :id
            """;

    private static final String FIND_RULES_BY_ACCIDENT_ID = """
            SELECT r FROM Accident as a
            JOIN a.rules as r
            WHERE a.id = :id
            """;

    /**
     * Получить список всех статей
     *
     * @return список всех статей
     */
    @Override
    public List<Rule> findAllRules() {
        return crudRepository.queryAndGetList(FIND_ALL, Rule.class);
    }

    /**
     * Найти статью по ижентификатору
     *
     * @param id идентификатор статьи
     * @return Optional.of(rule) если статья найдена, иначе Optional.empty()
     */
    @Override
    public Optional<Rule> findRuleById(int id) {
        return crudRepository.queryAndGetOptional(FIND_BY_ID, Rule.class, Map.of("id", id));
    }

    /**
     * Найти статьи относящиеся к инциденту по его идентификатору
     *
     * @param accidentId идентификатор инцидента
     * @return список статей
     */
    public List<Rule> getRulesByAccidentId(int accidentId) {
        return crudRepository.queryAndGetList(FIND_RULES_BY_ACCIDENT_ID, Rule.class, Map.of("id", accidentId));
    }
}
