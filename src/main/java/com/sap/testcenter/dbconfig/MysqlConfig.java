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
        entityManagerFactoryRef="entityManagerFactoryMysql",
        transactionManagerRef="transactionManagerMysql",
        basePackages= {"com.sap.testcenter.dao"})
public class MysqlConfig {
    @Autowired
    @Qualifier("mysqlDs")
    private DataSource mysqlDs;
    
    @Primary
    @Bean(name="entityManagerMysql")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryMysql(builder).getObject().createEntityManager();
    }
    
    @Primary
    @Bean(name="entityManagerFactoryMysql")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryMysql(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(mysqlDs)
                .properties(getVendorProperties(mysqlDs))
                .packages("com.sap.testcenter.pojo")
                .persistenceUnit("mysql")
                .build();
    }
    
    @Autowired  
    private JpaProperties jpaProperties;  
    private Map getVendorProperties(DataSource dataSource) {  
        return jpaProperties.getHibernateProperties(dataSource);  
    }
    
    @Primary  
    @Bean(name = "transactionManagerMysql")  
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {  
        return new JpaTransactionManager(entityManagerFactoryMysql(builder).getObject());  
    }  
}
