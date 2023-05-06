package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

/**
 * AccidentRepository - хранилище инцидентов
 *
 * @author Ilya Kaltygin
 */
public interface AccidentRepository {

    /**
     * Добавить инцидент в хранилище
     *
     * @param accident инцидент
     * @return Optional.of(accident) если инцидент добавлен успешно, иначе Optional.empty()
     */
    Optional<Accident> save(Accident accident);

    /**
     * Получить список всех инцидентов из хранилища
     *
     * @return список всех инцидентов
     */
    List<Accident> findAll();

    /**
     * Обновить инцидент
     *
     * @param accident инцидент
     * @return true если инцидент обновлен успешно, иначе false
     */
    boolean update(Accident accident);

    /**
     * Найти инцидент по идентификатору
     *
     * @param id идентификатор инцидента
     * @return Optional.of(accident) если инцидент по заданному идентификатору найден в хранилище, иначе Optional.empty()
     */
    Optional<Accident> findById(int id);
}
