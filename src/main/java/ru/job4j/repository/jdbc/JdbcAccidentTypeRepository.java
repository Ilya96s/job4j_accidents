package ru.job4j.repository.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;
import ru.job4j.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

/**
 * JdbcAccidentTypeRepository - реализация хранилща типов инцидентов с помощью JdbcTemplate
 *
 * @author Ilya Kaltygin
 */
@Repository
@AllArgsConstructor
public class JdbcAccidentTypeRepository implements AccidentTypeRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Маппер, превращающий строку из таблицы БД в объект типа AccidentType
     */
    private final RowMapper<AccidentType> accidentTypeRowMapper = (resultSet, rowNum) -> new AccidentType(
            resultSet.getInt("id"),
            resultSet.getString("name"));

    private static final String FIND_ALL = """
            SELECT * FROM accidentTypes
            """;

    private static final String FIND_BY_ID = """
            SELECT * FROM accidentTypes
            WHERE id = ?
            """;

    /**
     * Получить список всех типов инцидентов
     *
     * @return список со всеми типами инцидентов
     */
    @Override
    public List<AccidentType> findAllTypes() {
        return jdbcTemplate.query(FIND_ALL, accidentTypeRowMapper);
    }

    /**
     * Найти тип инцидента по идентификатору
     *
     * @param id идентификатор типа инцидента
     * @return Optional.of(accidentType) если тип инцидента найден, иначе Optional.empty()
     */
    @Override
    public Optional<AccidentType> findTypeById(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, accidentTypeRowMapper, id));
    }
}
