package com.bookapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.hibernate.cfg.Environment.*;

import java.util.Properties;

@Configuration
@PropertySources("classpath:db.properties")
@EnableTransactionManagement
@ComponentScans(value = {
        @ComponentScans("com.bookapi.dao"),
        @ComponentScans("com.bookapi.service")
})
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        Properties props = new Properties();

        // Setting JDBC properties
        props.put(DRIVER, env.getProperty("mysql.driver"));
        props.put(URL, env.getProperty("mysql.url"));
        props.put(USER, env.getProperty("mysql.user"));
        props.put(PASS, env.getProperty("mysql.password"));

        // Setting Hibernate properties
        props.put(SHOW_SQL, env.getProperty("hibernate.show_sql"));
        props.put(HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));

        // Setting C3PO properties
        props.put(C3P0_MIN_SIZE, env.getProperty("hibernate.c3po.min_size"));
        props.put(C3P0_MAX_SIZE, env.getProperty("hibernate.c3po.max_size"));
        props.put(C3P0_ACQUIRE_INCREMENT, env.getProperty("hibernate.c3po.acquire_increment"));
        props.put(C3P0_TIMEOUT, env.getProperty("hibernate.c3po.timeout"));
        props.put(C3P0_MAX_STATEMENTS, env.getProperty("hibernate.c3po.max_statement"));

        factoryBean.setHibernateProperties(props);
        factoryBean.setPackagesToScan("com.bookapi.model");

        return factoryBean;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }
}
