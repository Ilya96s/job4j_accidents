package ru.job4j.accidents.repository.springdata;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;

/**
 * SpringDataAccidentTypeRepository - хранилище статей, реализованное с помощью Spring Data Jpa
 *
 * @author Ilya Kaltygin
 */
public interface SpringDataAccidentTypeRepository extends CrudRepository<AccidentType, Integer> {

    /**
     * Получить список всех типов инцидентов
     *
     * @return список со всеми типами инцидентов
     */
    public List<AccidentType> findAll();
}
