package ru.job4j.service.springdata;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.model.Accident;
import ru.job4j.model.Rule;
import ru.job4j.repository.springdata.SpringDataAccidentRepository;
import ru.job4j.repository.springdata.SpringDataAccidentTypeRepository;
import ru.job4j.repository.springdata.SpringDataRuleRepository;
import ru.job4j.service.AccidentService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * SpringDataAccidentService - реализация сервиса по работе с инцидентами
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class SpringDataAccidentService implements AccidentService {

    /**
     * Хранилище инцидентов
     */
    private final SpringDataAccidentRepository accidentRepository;

    private final SpringDataRuleRepository ruleRepository;

    private final SpringDataAccidentTypeRepository accidentTypeRepository;

    /**
     * Добавить инцидент в хранилище
     *
     * @param accident инцидент
     * @param rulesIds список идентификаторов статей
     * @return Optional.of(accident) если инцидент добавлен успешно, иначе Optional.empty()
     */
    @Override
    @Transactional
    public Optional<Accident> save(Accident accident, List<Integer> rulesIds) {
        var optionalAccidentType = accidentTypeRepository.findById(accident.getType().getId());
        var rules = new HashSet<>(ruleRepository.findByIdIn(rulesIds));
        if (optionalAccidentType.isEmpty() || rules.size() != rulesIds.size()) {
            return Optional.empty();
        }
        accident.setType(optionalAccidentType.get());
        accident.setRules(rules);
        return Optional.of(accidentRepository.save(accident));
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
     * @param id идентификатор инцидента
     * @return true если инцидент обновлен успешно, иначе false
     */
    @Override
    @Transactional
    public boolean update(int id) {
        var optionalAccident = accidentRepository.findById(id);
        if (optionalAccident.isEmpty()) {
            return false;
        }
        accidentRepository.save(optionalAccident.get());
        return true;
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
