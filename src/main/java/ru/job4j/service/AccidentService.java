package ru.job4j.service;

import ru.job4j.model.Accident;

import java.util.List;
import java.util.Optional;

/**
 * AccidentService - интерфейс, описывающий бизнес логику по работу с инцидентами
 *
 * @author Ilya Kaltygin
 */
public interface AccidentService {

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
     * @param id идентификатор инцидента
     * @return true если инцидент обновлен успешно, иначе false
     */
    boolean update(int id);

    /**
     * Найти инцидент по идентификатору
     *
     * @param id идентификатор инцидента
     * @return Optional.of(accident) если инцидент по заданному идентификатору найден в хранилище, иначе Optional.empty()
     */
    Optional<Accident> findById(int id);
}
