package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AccidentMem - реализация хранилища инцидентов
 *
 * @author Ilya Kaltygin
 */
@ThreadSafe
@Repository
public class AccidentMem implements AccidentRepository, AccidentTypeRepository, RuleRepository {

    /**
     * AtomicInteger предоставляет атомарные операции со значением int
     */
    private AtomicInteger count = new AtomicInteger(0);

    /**
     * Временное хранилище
     */
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<Integer, Accident>();

    private final List<AccidentType> types = List.of(
            new AccidentType(1, "Две машины"),
            new AccidentType(2, "Машина и человек"),
            new AccidentType(3, "Машина и велосипед"),
            new AccidentType(4, "Одна машина"));

    private final List<Rule> rules = List.of(
            new Rule(1, "Статья. 1"),
            new Rule(2, "Статья. 2"),
            new Rule(3, "Статья. 3")
    );

    public AccidentMem() {
        var accident1 = Accident
                .builder()
                .name("Петр Петров")
                .text("Превышение скорости")
                .address("Красный проспект 3")
                .type(findTypeById(4).get())
                .build();
        var accident2 = Accident
                .builder()
                .name("Иван Иванов")
                .text("Проезд на красный свет светофора")
                .address("Блюхера 12")
                .type(findTypeById(4).get())
                .build();
        var accident3 = Accident
                .builder()
                .name("Олег Олегов")
                .text("Проезд через двойную сплошную")
                .address("Мичурина 31")
                .type(findTypeById(4).get())
                .build();
        save(accident1);
        save(accident2);
        save(accident3);
    }

    /**
     * Добавить инцидент в хранилище
     *
     * @param accident инцидент
     * @return Optional.of(accident) если инцидент добавлен успешно, иначе Optional.empty()
     */
    @Override
    public Optional<Accident> save(Accident accident) {
        var accidentId = count.getAndIncrement();
        accident.setId(accidentId);
        return Optional.ofNullable(accidents.putIfAbsent(accidentId, accident));
    }

    /**
     * Получить список всех инцидентов из хранилища
     *
     * @return список всех инцидентов
     */
    public List<Accident> findAll() {
        return accidents.values().stream().toList();
    }

    /**
     * Обновить инцидент
     *
     * @param accident инцидент
     * @return true если инцидент обновлен успешно, иначе false
     */
    @Override
    public boolean update(Accident accident) {
        return accidents.replace(accident.getId(), accidents.get(accident.getId()), accident);
    }

    /**
     * Найти инцидент по идентификатору
     *
     * @param id идентификатор инцидента
     * @return Optional.of(accident) если инцидент по заданному идентификатору найден в хранилище, иначе Optional.empty()
     */
    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    /**
     * Получить список всех типов инцидентов
     *
     * @return список со всеми типами инцидентов
     */
    @Override
    public List<AccidentType> findAllTypes() {
        return types;
    }

    /**
     * Найти тип инцидента по идентификатору
     *
     * @param id идентификатор типа инцидента
     * @return Optional.of(accidentType) если тип инцидента найден, иначе Optional.empty()
     */
    @Override
    public Optional<AccidentType> findTypeById(int id) {
        return Optional.ofNullable(types.get(id - 1));
    }

    /**
     * Получить список всех статей
     *
     * @return список всех статей
     */
    @Override
    public List<Rule> findAllRules() {
        return rules;
    }

    /**
     * Найти статью по ижентификатору
     *
     * @param id идентификатор статьи
     * @return Optional.of(rule) если статья найдена, иначе Optional.empty()
     */
    @Override
    public Optional<Rule> findRuleById(int id) {
        return Optional.ofNullable(rules.get(id - 1));
    }
}
