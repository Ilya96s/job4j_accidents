package ru.job4j.accidents.service.hibernate;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.hibernate.HbmAccidentTypeRepository;
import ru.job4j.accidents.service.AccidentTypeService;

import java.util.List;
import java.util.Optional;

/**
 * HbmAccidentTypeService - реализация сервиса по работе с типами инцидентов
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
@Qualifier("hbmAccidentTypeService")
public class HbmAccidentTypeService implements AccidentTypeService {

    /**
     * Хранилище типов инцидентов
     */
    private final HbmAccidentTypeRepository accidentTypeRepository;

    /**
     * Получить список всех типов инцидентов
     *
     * @return список со всеми типами инцидентов
     */
    @Override
    public List<AccidentType> findAllTypes() {
        return accidentTypeRepository.findAllTypes();
    }

    /**
     * Найти тип инцидента по идентификатору
     *
     * @param id идентификатор типа инцидента
     * @return Optional.of(accidentType) если тип инцидента найден, иначе Optional.empty()
     */
    @Override
    public Optional<AccidentType> findTypeById(int id) {
        return accidentTypeRepository.findTypeById(id);
    }
}
