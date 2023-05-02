package ru.job4j.service.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.repository.jdbc.JdbcAccidentRepository;
import ru.job4j.repository.jdbc.JdbcAccidentTypeRepository;
import ru.job4j.repository.jdbc.JdbcRuleRepository;
import ru.job4j.service.AccidentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * AccidentJdbcService - релаизация среивса по работе с инцидентами с помощью JdbcTemplate
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
public class JdbcAccidentService implements AccidentService {

    /**
     * Хранилище инцидентов
     */
    private final JdbcAccidentRepository jdbcAccidentRepository;

    /**
     * Хранилище типов инцидентов
     */
    private final JdbcAccidentTypeRepository jdbcAccidentTypeRepository;

    /**
     * Хранилище статей
     */
    private final JdbcRuleRepository jdbcRuleRepository;

    /**
     * Добавить инцидент в хранилище
     *
     * @param accident инцидент
     * @param rIds список идентификаторов статей
     * @return Optional.of(accident) если инцидент добавлен успешно, иначе Optional.empty()
     */
    @Override
    public Optional<Accident> save(Accident accident, List<Integer> rIds) {
        var optionalAccidentType = jdbcAccidentTypeRepository.findTypeById(accident.getType().getId());
        var rules = rIds.stream()
                .map(jdbcRuleRepository::findRuleById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        if (optionalAccidentType.isEmpty() || rules.size() != rIds.size()) {
            return Optional.empty();
        }
        accident.setType(optionalAccidentType.get());
        accident.setRules(rules);
        return jdbcAccidentRepository.save(accident);
    }

    /**
     * Получить список всех инцидентов из хранилища
     *
     * @return список всех инцидентов
     */
    @Override
    public List<Accident> findAll() {
        return jdbcAccidentRepository.findAll();
    }

    /**
     * Обновить инцидент
     *
     * @param id идентификатор инцидента
     * @return true если инцидент обновлен успешно, иначе false
     */
    @Override
    public boolean update(int id) {
        var optionalAccident = jdbcAccidentRepository.findById(id);
        if (optionalAccident.isEmpty()) {
            return false;
        }
        return jdbcAccidentRepository.update(optionalAccident.get());
    }

    /**
     * Найти инцидент по идентификатору
     *
     * @param id идентификатор инцидента
     * @return Optional.of(accident) если инцидент по заданному идентификатору найден в хранилище, иначе Optional.empty()
     */
    @Override
    public Optional<Accident> findById(int id) {
        return jdbcAccidentRepository.findById(id);
    }
}
