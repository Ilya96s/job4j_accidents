package ru.job4j.repository.springdata;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Accident;

/**
 * SpringDataAccidentRepository - хранилище инцидентов, реализованное с помощью Spring Data Jpa
 *
 * @author Ilya Kaltygin
 */
public interface SpringDataAccidentRepository extends CrudRepository<Accident, Integer> {

}
