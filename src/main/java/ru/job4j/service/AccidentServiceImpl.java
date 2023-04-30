package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.repository.AccidentRepository;

import java.util.List;
import java.util.Optional;

/**
 * AccidentService - сервис, описывающий логику работы
 *
 * @author Ilya Kaltygin
 */
@ThreadSafe
@Service
public class AccidentServiceImpl implements AccidentService {

    /**
     * Хранилище инцидентов
     */
    private AccidentRepository accidentRepository;

    /**
     * Добавить инцидент в хранилище
     *
     * @param accident инцидент
     * @return Optional.of(accident) если инцидент добавлен успешно, иначе Optional.empty()
     */
    @Override
    public Optional<Accident> save(Accident accident) {
        return accidentRepository.save(accident);
    }

    /**
     * Получить список всех инцидентов из хранилища
     *
     * @return список всех инцидентов
     */
    @Override
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    /**
     * Обновить инцидент
     *
     * @param accident инцидент
     * @return true если инцидент обновлен успешно, иначе false
     */
    @Override
    public boolean update(Accident accident) {
        return accidentRepository.update(accident);
    }

    /**
     * Найти инцидент по идентификатору
     *
     * @param id идентификатор инцидента
     * @return Optional.of(accident) если инцидент по заданному идентификатору найден в хранилище, иначе Optional.empty()
     */
    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }
}
