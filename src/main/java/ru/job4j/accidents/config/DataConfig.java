package ru.job4j.accidents.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * DataConfig - конфигурационный класс Spring Data Jpa
 *
 * @author Ilya Kaltygin
 */
@Configuration
@EnableJpaRepositories("ru.job4j.accidents.repository.springdata")
@EnableTransactionManagement
public class DataConfig {

    /**
     * Создает бин по LocalContainerEntityManagerFactoryBean
     * @param ds источник данных
     * @return объект типа LocalContainerEntityManagerFactoryBean
     */
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("ru.job4j.accidents");
        factory.setDataSource(ds);
        return factory;
    }

    /**
     * Создает бин PlatformTransactionManager
     * @param entityManagerFactory фабрика для создания экземпляров EntityManager
     * @return объект типа PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}
