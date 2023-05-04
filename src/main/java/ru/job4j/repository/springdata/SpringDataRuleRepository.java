package ru.job4j.repository.springdata;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Rule;

import java.util.List;

/**
 * SprigDataRuleRepository - хранилище статей, реализованное с помощью Spring Data Jpa
 *
 * @author Ilya Kaltygin
 */
public interface SpringDataRuleRepository extends CrudRepository<Rule, Integer> {

    /**
     * Получить список всех статей
     *
     * @return список всех статей
     */
    List<Rule> findAll();

    /**
     * Найти статьи по идентификаторам
     *
     * @param rIds сисок идентификаторов
     * @return список статей
     */
    List<Rule> findByIdIn(List<Integer> rIds);
}
