package ru.job4j.repository.jdbc;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.repository.AccidentRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * AccidentJdbcTemplate - реализация хранилища инцидентов с помощью JdbcTemplate
 *
 * @author Ilya Kaltygin
 */
@Repository
@AllArgsConstructor
public class JdbcAccidentRepository implements AccidentRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Хранилище типов инцидентов
     */
    private final JdbcAccidentTypeRepository accidentTypeRepository;

    /**
     * Хранилище статей
     */
    private final JdbcRuleRepository jdbcRuleRepository;

    private static final Logger LOG = LoggerFactory.getLogger(JdbcAccidentRepository.class);

    /**
     * Маппер, превращающий строку из таблицы БД в объект типа Accident
     */
    private final RowMapper<Accident> accidentRowMapper = (resultSet, rowNun) -> Accident.builder()
            .id(resultSet.getInt("id"))
            .name(resultSet.getString("name"))
            .text(resultSet.getString("text"))
            .address(resultSet.getString("address"))
            .type(getAccidentType(resultSet).get())
            .rules(getRules(resultSet.getInt("id")))
            .build();

    public static final String SAVE_ACCIDENT = """
            INSERT INTO accidents (name, text, address, type_id )
            VALUES (?, ?, ?, ?)
            """;

    private static final String FIND_BY_ID = """
            SELECT * FROM accidents
            WHERE id = ?
            """;

    private static final String FIND_ALL = """
            SELECT * FROM accidents
            """;

    private static final String INSERT_INTO_ACCIDENTS_RULES = """
            INSERT INTO accidents_rules (accident_id, rule_id)
            VALUES (?, ?)
            """;

    private static final String UPDATE_ACCIDENT = """
            UPDATE accidents
            SET name = ?, text = ?, address = ?, type_id = ?
            WHERE id = ?
            """;
    private static final String UPDATE_ACCIDENTS_RULES = """
            UPDATE accidents_rules
            SET accident_id = ?, rule_id = ?
            WHERE id = ?
            """;


    /**
     * Добавить инцидент в хранилище
     *
     * @param accident инцидент
     * @return Optional.of(accident) если инцидент добавлен успешно, иначе Optional.empty()
     */
    @Override
    public Optional<Accident> save(Accident accident) {
        Optional<Accident> optionalAccident = Optional.empty();
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SAVE_ACCIDENT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, accident.getName());
                ps.setString(2, accident.getText());
                ps.setString(3, accident.getAddress());
                ps.setInt(4, accident.getType().getId());
                return ps;
            }, keyHolder);
            accident.setId(keyHolder.getKey().intValue());
            accident.getRules().forEach(
                    rule -> jdbcTemplate.update(INSERT_INTO_ACCIDENTS_RULES, accident.getId(), rule.getId())
            );
            optionalAccident = Optional.of(accident);
        } catch (Exception e) {
            LOG.error("Error in save(Accident accident) method", e);
        }
        return optionalAccident;
    }

    /**
     * Получить список всех инцидентов из хранилища
     *
     * @return список всех инцидентов
     */
    @Override
    public List<Accident> findAll() {
        return jdbcTemplate.query(FIND_ALL, accidentRowMapper);
    }

    /**
     * Обновить инцидент
     *
     * @param accident инцидент
     * @return true если инцидент обновлен успешно, иначе false
     */
    @Override
    public boolean update(Accident accident) {
        var result = jdbcTemplate.update(
                UPDATE_ACCIDENT,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId()
        );
        accident.getRules().forEach(
                rule -> jdbcTemplate.update(
                        UPDATE_ACCIDENTS_RULES,
                        accident.getId(),
                        rule.getId()
                )
        );
        return result > 0;
    }

    /**
     * Найти инцидент по идентификатору
     *
     * @param id идентификатор инцидента
     * @return Optional.of(accident) если инцидент по заданному идентификатору найден в хранилище, иначе Optional.empty()
     */
    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, accidentRowMapper, id));
    }

    /**
     * Найти тип инцидента
     *
     * @param rs объект ResultSet
     * @return Optional.of(accidentType) если тип инцидента найден, иначе Optional.empty()
     * @throws SQLException
     */
    private Optional<AccidentType> getAccidentType(ResultSet rs) throws SQLException {
        return accidentTypeRepository.findTypeById(rs.getInt("type_id"));
    }

    /**
     * Найти статьи относящиеся к инциденту по его идентификатору
     *
     * @param accidentId идентификатор инцидента
     * @return множество статей
     */
    private Set<Rule> getRules(int accidentId) {
        return new HashSet<>(jdbcRuleRepository.getRulesByAccidentId(accidentId));
    }
}
