package ru.job4j.repository.hibernate;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;
import ru.job4j.repository.AccidentTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * HbmAccidentTypeRepository - реализация хранилища типов инцидентов с помощью Hibernate
 *
 * @author Ilya Kaltygin
 */
@Repository
@AllArgsConstructor
public class HbmAccidentTypeRepository implements AccidentTypeRepository {

    /**
     * Используется для получения объектов Session.
     * Отвечает за считывание параметров конфигурации Hibernate и подключение к базе данных.
     */
    private final SessionFactory sessionFactory;

    private static final Logger LOG = LoggerFactory.getLogger(HbmAccidentTypeRepository.class);

    private static final String FIND_ALL = """
            From AccidentType
            """;

    private static final String FIND_BY_ID = """
            From AccidentType as at
            WHERE at.id = :id
            """;

    /**
     * Получить список всех типов инцидентов
     *
     * @return список со всеми типами инцидентов
     */
    @Override
    public List<AccidentType> findAllTypes() {
        var session = sessionFactory.openSession();
        Transaction tx = null;
        List<AccidentType> accidentTypes = new ArrayList<>();
        try {
            tx = session.getTransaction();
            accidentTypes = session.createQuery(FIND_ALL, AccidentType.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error("Exception in the findAllTypes() method", e);
        } finally {
            session.close();
        }
        return accidentTypes;
    }

    /**
     * Найти тип инцидента по идентификатору
     *
     * @param id идентификатор типа инцидента
     * @return Optional.of(accidentType) если тип инцидента найден, иначе Optional.empty()
     */
    @Override
    public Optional<AccidentType> findTypeById(int id) {
        var session = sessionFactory.openSession();
        Transaction tx = null;
        Optional<AccidentType> optionalAccidentType = Optional.empty();
        try {
            tx = session.getTransaction();
            optionalAccidentType = session.createQuery(FIND_BY_ID, AccidentType.class).uniqueResultOptional();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error("Exception in the findTypeById(int id) method", e);
        } finally {
            session.close();
        }
        return optionalAccidentType;
    }
}
