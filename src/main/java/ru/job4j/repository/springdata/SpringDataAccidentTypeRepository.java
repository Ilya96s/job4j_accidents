package ru.job4j.repository.springdata;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.AccidentType;

/**
 * SpringDataAccidentTypeRepository - хранилище статей, реализованное с помощью Spring Data Jpa
 *
 * @author Ilya Kaltygin
 */
public interface SpringDataAccidentTypeRepository extends CrudRepository<AccidentType, Integer> {
}
