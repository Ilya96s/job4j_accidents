package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

/**
 * AccidentTypeRepository - хранилище типов инцидентов
 *
 * @author Ilya Kaltygin
 */
public interface AccidentTypeRepository {

    /**
     * Получить список всех типов инцидентов
     *
     * @return список со всеми типами инцидентов
     */
    List<AccidentType> findAllTypes();

    /**
     * Найти тип инцидента по идентификатору
     *
     * @param id идентификатор типа инцидента
     * @return Optional.of(accidentType) если тип инцидента найден, иначе Optional.empty()
     */
    Optional<AccidentType> findTypeById(int id);
}
