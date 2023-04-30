package ru.job4j.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;

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
public class AccidentMem implements AccidentRepository {

    /**
     * AtomicInteger предоставляет атомарные операции со значением int
     */
    private AtomicInteger count = new AtomicInteger(0);

    /**
     * Временное хранилище
     */
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<Integer, Accident>();

    public AccidentMem() {
        var accident1 = Accident
                .builder()
                .name("Петр Петров")
                .text("Превышение скорости")
                .address("Красный проспект 3")
                .build();
        var accident2 = Accident
                .builder()
                .name("Иван Иванов")
                .text("Проезд на красный свет светофора")
                .address("Блюхера 12")
                .build();
        var accident3 = Accident
                .builder()
                .name("Олег Олегов")
                .text("Проезд через двойную сплошную")
                .address("Мичурина 31")
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
}
