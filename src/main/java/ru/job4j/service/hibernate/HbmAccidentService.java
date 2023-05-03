package ru.job4j.service.hibernate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.repository.hibernate.HbmAccidentRepository;
import ru.job4j.repository.hibernate.HbmAccidentTypeRepository;
import ru.job4j.repository.hibernate.HbmRuleRepository;
import ru.job4j.service.AccidentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * HbmAccidentService - реализация сервиса по работе с инцидентами
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
public class HbmAccidentService implements AccidentService {

    /**
     * Хранилище инцидентов
     */
    private final HbmAccidentRepository accidentRepository;

    /**
     * Хранилище типов инцидентов
     */
    private final HbmAccidentTypeRepository accidentTypeRepository;

    private final HbmRuleRepository ruleRepository;


    /**
     * Добавить инцидент в хранилище
     *
     * @param accident инцидент
     * @param rulesIds список идентификаторов статей
     * @return Optional.of(accident) если инцидент добавлен успешно, иначе Optional.empty()
     */
    @Override
    public Optional<Accident> save(Accident accident, List<Integer> rulesIds) {
        var optionalAccidentType = accidentTypeRepository.findTypeById(accident.getType().getId());
        var rules = rulesIds.stream()
                .map(ruleRepository::findRuleById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        if (optionalAccidentType.isEmpty() || rules.size() != rulesIds.size()) {
            return Optional.empty();
        }
        accident.setType(optionalAccidentType.get());
        accident.setRules(rules);
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
     * @param id идентификатор инцидента
     * @return true если инцидент обновлен успешно, иначе false
     */
    @Override
    public boolean update(int id) {
        var optionalAccident = accidentRepository.findById(id);
        if (optionalAccident.isEmpty()) {
            return false;
        }
        return accidentRepository.update(optionalAccident.get());
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
