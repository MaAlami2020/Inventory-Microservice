package com.example.webapp1a;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "adminEntityManagerFactory", transactionManagerRef = "adminTransactionManager",
basePackages = {"com.example.webapp1a.repository.admin"})
public class PostgreSQLConfig {
    
    @Autowired
    private Environment env;

    @Bean(name = "adminDataSource")
    public DataSource adminDataSource(){
        return DataSourceBuilder.create().driverClassName("org.postgresql.Driver")
                                                        .url("jdbc:postgresql://localhost:5432/postgres")
                                                        .username("postgres")
                                                        .password("mariamparo9")
                                                        .build();
    }

    //@Primary
    @Bean(name = "adminEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(adminDataSource());
        em.setPackagesToScan("com.example.webapp1a.model.admin");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("postgre.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.show-sql", env.getProperty("postgre.jpa.show-sql"));
        properties.put("hibernate.dialect", env.getProperty("postgre.jpa.database-platform"));
        //properties.put("database-platform", env.getProperty("postgre.jpa.database-platform"));

        em.setJpaPropertyMap(properties);

        return em;
    }

    //@Primary
    @Bean(name = "adminTransactionManager")
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }
}
