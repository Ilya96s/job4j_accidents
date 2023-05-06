package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * AccidentService - сервис, описывающий логику работы
 *
 * @author Ilya Kaltygin
 */
@ThreadSafe
@Service
@AllArgsConstructor
public class AccidentServiceImpl implements AccidentService {

    /**
     * Временное хранилище
     */
    private final AccidentMem accidentMem;

//    /**
//     * Хранилище типов инцидентов
//     */
//    private final AccidentMem accidentTypeRepository;
//
//    /**
//     * Хранилище статей
//     */
//    private final AccidentMem ruleRepository;

    /**
     * Добавить инцидент в хранилище
     *
     * @param accident инцидент
     * @param rIds список идентификаторов статей
     * @return Optional.of(accident) если инцидент добавлен успешно, иначе Optional.empty()
     */
    @Override
    public Optional<Accident> save(Accident accident, List<Integer> rIds) {
        var optionalAccidentType = accidentMem.findTypeById(accident.getType().getId());
        var rules = rIds.stream()
                .map(accidentMem::findRuleById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        if (optionalAccidentType.isEmpty() || rules.size() != rIds.size()) {
            return Optional.empty();
        }
        accident.setType(optionalAccidentType.get());
        accident.setRules(rules);
        return accidentMem.save(accident);
    }

    /**
     * Получить список всех инцидентов из хранилища
     *
     * @return список всех инцидентов
     */
    @Override
    public List<Accident> findAll() {
        return accidentMem.findAll();
    }

    /**
     * Обновить инцидент
     *
     * @param id идентификатор инцидента
     * @return true если инцидент обновлен успешно, иначе false
     */
    @Override
    public boolean update(int id) {
        var optionalAccident = accidentMem.findById(id);
        if (optionalAccident.isEmpty()) {
            return false;
        }
        return accidentMem.update(optionalAccident.get());
    }

    /**
     * Найти инцидент по идентификатору
     *
     * @param id идентификатор инцидента
     * @return Optional.of(accident) если инцидент по заданному идентификатору найден в хранилище, иначе Optional.empty()
     */
    @Override
    public Optional<Accident> findById(int id) {
        return accidentMem.findById(id);
    }
}
