package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * HbmAccidentRepository - реализация хранилища инцидентов с помощью Hibernate
 *
 * @author Ilya Kaltygin
 */
@Repository
@AllArgsConstructor
public class HbmAccidentRepository implements AccidentRepository {

    /**
     * Реализация паттерна Command
     */
    private final CrudRepository crudRepository;

    private static final String FIND_ALL = """
            From Accident as ac
            LEFT JOIN FETCH ac.type
            LEFT JOIN FETCH ac.rules
            """;

    private static final String FIND_BY_ID = """
            From Accident as ac
            WHERE ac.id = :id
            """;

    /**
     * Добавить инцидент в хранилище
     *
     * @param accident инцидент
     * @return Optional.of(accident) если инцидент добавлен успешно, иначе Optional.empty()
     */
    @Override
    public Optional<Accident> save(Accident accident) {
        crudRepository.run(session -> session.save(accident));
        return Optional.of(accident);
    }

    /**
     * Получить список всех инцидентов из хранилища
     *
     * @return список всех инцидентов
     */
    @Override
    public List<Accident> findAll() {
        return crudRepository.queryAndGetList(FIND_ALL, Accident.class);
    }

    /**
     * Обновить инцидент
     *
     * @param accident инцидент
     * @return true если инцидент обновлен успешно, иначе false
     */
    @Override
    public boolean update(Accident accident) {
        return crudRepository.executeAndGetBoolean(accident);
    }

    /**
     * Найти инцидент по идентификатору
     *
     * @param id идентификатор инцидента
     * @return Optional.of(accident) если инцидент по заданному идентификатору найден в хранилище, иначе Optional.empty()
     */
    @Override
    public Optional<Accident> findById(int id) {
        return crudRepository.queryAndGetOptional(FIND_BY_ID, Accident.class, Map.of("id", id));
    }
}
