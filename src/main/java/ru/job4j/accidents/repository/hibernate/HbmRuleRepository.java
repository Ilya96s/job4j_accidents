package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

    private static final String FIND_RULES_BY_IDS = """
            FROM Rule as rule
            WHERE rule.id IN :rIds
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
     * Найти статьи по идентификаторам
     *
     * @param rIds список идентификаторов
     * @return список статей
     */
    public Set<Rule> findRulesByIds(List<Integer> rIds) {
        return crudRepository.queryAndGetList(FIND_RULES_BY_IDS, Rule.class, Map.of("rIds", rIds));
    }
}
