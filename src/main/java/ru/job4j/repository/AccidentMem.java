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
        accidents.put(1, new Accident(1, "Петр Петров", "Превышение скорости", "Красный Проспект, 3"));
        accidents.put(2, new Accident(2, "Иван Иванов", "Проезд на красный свет светофора", "Блюхера, 12"));
        accidents.put(3, new Accident(3, "Олег Олегов", "Проезд через двойную сплошную", "Мичурина, 31"));
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
