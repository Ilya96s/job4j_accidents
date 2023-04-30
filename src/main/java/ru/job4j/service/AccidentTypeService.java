package ru.job4j.service;

import ru.job4j.model.AccidentType;

import java.util.List;
import java.util.Optional;

/**
 * AccidentTypeService - сервис, описывающий бизнес логику работы с типами инцидентов
 *
 * @author Ilya Kaltygin
 */
public interface AccidentTypeService {

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
