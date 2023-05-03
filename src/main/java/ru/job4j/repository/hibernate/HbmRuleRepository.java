package ru.job4j.repository.hibernate;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Rule;
import ru.job4j.repository.RuleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * HbmRuleRepository - реализация хранилища статей с помощью Hibernate
 *
 * @author Ilya Kaltygin
 */
@Repository
@AllArgsConstructor
public class HbmRuleRepository implements RuleRepository {

    /**
     * Используется для получения объектов Session.
     * Отвечает за считывание параметров конфигурации Hibernate и подключение к базе данных.
     */
    private final SessionFactory sessionFactory;

    private static final Logger LOG = LoggerFactory.getLogger(HbmRuleRepository.class);

    private static final String FIND_ALL = """
            From Rule
            """;

    private static final String FIND_BY_ID = """
            From Rule as rule
            WHERE rule.id = :id
            """;

    /**
     * Получить список всех статей
     *
     * @return список всех статей
     */
    @Override
    public List<Rule> findAllRules() {
        var session = sessionFactory.openSession();
        Transaction tx = null;
        List<Rule> rules = new ArrayList<>();
        try {
            tx = session.getTransaction();
            rules = session.createQuery(FIND_ALL, Rule.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error("Exception in the findAllRules() method", e);
        } finally {
            session.close();
        }
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
        var session = sessionFactory.openSession();
        Transaction tx = null;
        Optional<Rule> optionalRule = Optional.empty();
        try {
            tx = session.getTransaction();
            session.createQuery(FIND_BY_ID, Rule.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error("Exception in the findRuleById(int id) method", e);
        } finally {
            session.close();
        }
        return optionalRule;
    }
}
