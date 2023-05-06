package ru.job4j.accidents.repository.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * JdbcRuleRepository - реализация хранилища статей с помощью JdbcTemplate
 *
 * @author Ilya Kaltygin
 */
@Repository
@AllArgsConstructor
public class JdbcRuleRepository implements RuleRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Маппер, превращающий строку из таблицы БД в объект типа Rule
     */
    private final RowMapper<Rule> ruleRowMapper = (resultSet, rowNum) -> new Rule(
            resultSet.getInt("id"),
            resultSet.getString("name"));

    private static final String FIND_ALL = """
            SELECT * FROM rules
            """;

    private static final String FIND_BY_ID = """
            SELECT * FROM rules
            WHERE id = ?
            """;

    private static final String FIND_RULES_BY_ACCIDENT_ID = """
            SELECT * FROM rules
            WHERE id IN (SELECT rule_id FROM accidents_rules WHERE accident_id = ?)
            """;

    /**
     * Получить список всех статей
     *
     * @return список всех статей
     */
    @Override
    public List<Rule> findAllRules() {
        return jdbcTemplate.query(FIND_ALL, ruleRowMapper);
    }

    /**
     * Найти статью по ижентификатору
     *
     * @param id идентификатор статьи
     * @return Optional.of(rule) если статья найдена, иначе Optional.empty()
     */
    @Override
    public Optional<Rule> findRuleById(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, ruleRowMapper, id));
    }

    /**
     * Найти статьи относящиеся к инциденту по его идентификатору
     *
     * @param accidentId идентификатор инцидента
     * @return список статей
     */
    public Set<Rule> getRulesByAccidentId(int accidentId) {
        return new HashSet<>(jdbcTemplate.query(FIND_RULES_BY_ACCIDENT_ID, ruleRowMapper, accidentId));
    }
}
