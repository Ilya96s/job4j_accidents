package ru.job4j.service.springdata;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.model.AccidentType;
import ru.job4j.repository.springdata.SpringDataAccidentTypeRepository;
import ru.job4j.service.AccidentTypeService;

import java.util.List;
import java.util.Optional;

/**
 * SpringDataAccidentTypeService - реализация сервиса по работе с типами инцидентов
 */
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class SpringDataAccidentTypeService implements AccidentTypeService {

    /**
     * Хранилище типов инцидентов
     */
    private final SpringDataAccidentTypeRepository accidentTypeRepository;

    /**
     * Получить список всех типов инцидентов
     *
     * @return список со всеми типами инцидентов
     */
    @Override
    public List<AccidentType> findAllTypes() {
        return (List<AccidentType>) accidentTypeRepository.findAll();
    }

    /**
     * Найти тип инцидента по идентификатору
     *
     * @param id идентификатор типа инцидента
     * @return Optional.of(accidentType) если тип инцидента найден, иначе Optional.empty()
     */
    @Override
    public Optional<AccidentType> findTypeById(int id) {
        return accidentTypeRepository.findById(id);
    }
}
