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
        var accident1 = new Accident();
        accident1.setName("Петр Петров");
        accident1.setText("Превышение скорости");
        accident1.setAddress("Красный проспект 3");
        var accident2 = new Accident();
        accident2.setName("Иван Иванов");
        accident2.setText("Проезд на красный свет светофора");
        accident2.setAddress("Блюхера 12");
        var accident3 = new Accident();
        accident3.setName("Олег Олегов");
        accident3.setText("Проезд через двойную сплошную");
        accident3.setAddress("Мичурина 31");
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
}
