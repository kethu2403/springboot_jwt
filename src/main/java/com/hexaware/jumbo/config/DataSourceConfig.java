package com.hexaware.jumbo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DataSourceConfiguration.
 */
@Configuration
public class DataSourceConfig {

    /**
     * DataSource URL.
    */
    @Value("${data.source.url:defaultValue}")
    private String dataSourceUrl;
    /**
     * @return datasource.
     */
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url(dataSourceUrl);
        dataSourceBuilder.username("jumbo");
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }
}
