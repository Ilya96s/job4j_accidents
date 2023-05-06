package ru.job4j.accidents.service.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.jdbc.JdbcAccidentTypeRepository;
import ru.job4j.accidents.service.AccidentTypeService;

import java.util.List;
import java.util.Optional;

/**
 * JdbcAccidentTypeService - релаизация среивса по работе с типами инцидентов с помощью JdbcTemplate
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
public class JdbcAccidentTypeService implements AccidentTypeService {

    /**
     * Хранилище типов инцидентов
     */
    private final JdbcAccidentTypeRepository jdbcAccidentTypeRepository;

    /**
     * Получить список всех типов инцидентов
     *
     * @return список со всеми типами инцидентов
     */
    @Override
    public List<AccidentType> findAllTypes() {
        return jdbcAccidentTypeRepository.findAllTypes();
    }

    /**
     * Найти тип инцидента по идентификатору
     *
     * @param id идентификатор типа инцидента
     * @return Optional.of(accidentType) если тип инцидента найден, иначе Optional.empty()
     */
    @Override
    public Optional<AccidentType> findTypeById(int id) {
        return jdbcAccidentTypeRepository.findTypeById(id);
    }
}
