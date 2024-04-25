package com.sap.testcenter.dbconfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {
    @Bean(name="mysqlDs")
    @Qualifier("mysqlDs")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.mysql")
    public DataSource mysqlDs() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name="hanaDs")
    @Qualifier("hanaDs")
    @ConfigurationProperties(prefix="spring.datasource.hana")
    public DataSource hanaDs() {
        return DataSourceBuilder.create().build();
    }
}
