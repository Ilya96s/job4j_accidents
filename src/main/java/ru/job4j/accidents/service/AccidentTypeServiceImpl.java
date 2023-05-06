package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.List;
import java.util.Optional;


/**
 * AccidentTypeServiceImpl - реализация сервиса по работе с типами инцидентов
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
public class AccidentTypeServiceImpl implements AccidentTypeService {

    /**
     * Временное хранилище
     */
    private final AccidentMem accidentMem;

    /**
     * Получить список всех типов инцидентов
     *
     * @return список со всеми типами инцидентов
     */
    @Override
    public List<AccidentType> findAllTypes() {
        return accidentMem.findAllTypes();
    }

    /**
     * Найти тип инцидента по идентификатору
     *
     * @param id идентификатор типа инцидента
     * @return Optional.of(accidentType) если тип инцидента найден, иначе Optional.empty()
     */
    @Override
    public Optional<AccidentType> findTypeById(int id) {
        return accidentMem.findTypeById(id);
    }
}
