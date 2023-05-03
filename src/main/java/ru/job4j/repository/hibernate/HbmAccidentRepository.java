package ru.job4j.repository.hibernate;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;
import ru.job4j.repository.AccidentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * HbmAccidentRepository - реализация хранилища инцидентов с помощью Hibernate
 *
 * @author Ilya Kaltygin
 */
@Repository
@AllArgsConstructor
public class HbmAccidentRepository implements AccidentRepository {

    /**
     * Используется для получения объектов Session.
     * Отвечает за считывание параметров конфигурации Hibernate и подключение к базе данных.
     */
    private final SessionFactory sessionFactory;

    private static final Logger LOG = LoggerFactory.getLogger(HbmAccidentRepository.class);

    private static final String FIND_ALL = """
            From Accident as ac
            LEFT JOIN FETCH ac.type
            LEFT JOIN FETCH ac.rules
            """;

    private static final String FIND_BY_ID = """
            From Accident as ac
            WHERE ac.id = :id
            """;

    /**
     * Добавить инцидент в хранилище
     *
     * @param accident инцидент
     * @return Optional.of(accident) если инцидент добавлен успешно, иначе Optional.empty()
     */
    @Override
    public Optional<Accident> save(Accident accident) {
        var session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            session.save(accident);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error("Exception in the save(Accident accident) method", e);
        } finally {
            session.close();
        }
        return Optional.of(accident);
    }

    /**
     * Получить список всех инцидентов из хранилища
     *
     * @return список всех инцидентов
     */
    @Override
    public List<Accident> findAll() {
        var session = sessionFactory.openSession();
        List<Accident> accidents = new ArrayList<>();
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            accidents = session.createQuery(FIND_ALL, Accident.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error("Exception in the findAll() method", e);
        } finally {
            session.close();
        }
        return accidents;
    }

    /**
     * Обновить инцидент
     *
     * @param accident инцидент
     * @return true если инцидент обновлен успешно, иначе false
     */
    @Override
    public boolean update(Accident accident) {
        var session = sessionFactory.openSession();
        boolean rsl = false;
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            session.update(accident);
            tx.commit();
            rsl = true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error("Exception in the update(Accident accident) method", e);
        } finally {
            session.close();
        }
        return rsl;
    }

    /**
     * Найти инцидент по идентификатору
     *
     * @param id идентификатор инцидента
     * @return Optional.of(accident) если инцидент по заданному идентификатору найден в хранилище, иначе Optional.empty()
     */
    @Override
    public Optional<Accident> findById(int id) {
        var session = sessionFactory.openSession();
        Optional<Accident> optionalAccident = Optional.empty();
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            optionalAccident = session.createQuery(FIND_BY_ID, Accident.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error("Exception in the findById(int id) method", e);
        } finally {
            session.close();
        }
        return optionalAccident;
    }
}
