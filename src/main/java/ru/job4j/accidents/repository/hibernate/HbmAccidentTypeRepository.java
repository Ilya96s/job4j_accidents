package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * HbmAccidentTypeRepository - реализация хранилища типов инцидентов с помощью Hibernate
 *
 * @author Ilya Kaltygin
 */
@Repository
@AllArgsConstructor
public class HbmAccidentTypeRepository implements AccidentTypeRepository {

    /**
     * Реализация паттерна Command
     */
    private final CrudRepository crudRepository;

    private static final String FIND_ALL = """
            From AccidentType
            """;

    private static final String FIND_BY_ID = """
            From AccidentType as at
            WHERE at.id = :id
            """;

    /**
     * Получить список всех типов инцидентов
     *
     * @return список со всеми типами инцидентов
     */
    @Override
    public List<AccidentType> findAllTypes() {
        return crudRepository.queryAndGetList(FIND_ALL, AccidentType.class);
    }

    /**
     * Найти тип инцидента по идентификатору
     *
     * @param id идентификатор типа инцидента
     * @return Optional.of(accidentType) если тип инцидента найден, иначе Optional.empty()
     */
    @Override
    public Optional<AccidentType> findTypeById(int id) {
        return crudRepository.queryAndGetOptional(FIND_BY_ID, AccidentType.class, Map.of("id", id));
    }
}
