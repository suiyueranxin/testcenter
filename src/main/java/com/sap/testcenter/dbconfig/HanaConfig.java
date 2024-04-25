package com.sap.testcenter.dbconfig;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactoryHana",
        transactionManagerRef="transactionManagerHana",
        basePackages= {"com.sap.testcenter.hanadao"})
public class HanaConfig {
    @Autowired
    @Qualifier("hanaDs")
    private DataSource hanaDs;
    
    @Bean(name="entityManagerHana")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryHana(builder).getObject().createEntityManager();
    }
    
    @Bean(name="entityManagerFactoryHana")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryHana(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(hanaDs)
                .properties(getVendorProperties(hanaDs))
                .packages("com.sap.testcenter.hanapojo")
                .persistenceUnit("hana")
                .build();
    }
    
    @Autowired  
    private JpaProperties jpaProperties;  
    private Map getVendorProperties(DataSource dataSource) {  
        return jpaProperties.getHibernateProperties(dataSource);  
    }
    
    @Bean(name = "transactionManagerHana")  
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {  
        return new JpaTransactionManager(entityManagerFactoryHana(builder).getObject());  
    }  
}
