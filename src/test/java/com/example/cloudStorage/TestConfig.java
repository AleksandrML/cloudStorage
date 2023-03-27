package com.example.cloudStorage;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class TestConfig {

    @Bean
    public DataSource dataSource() {
        return Mockito.mock(DataSource.class);
    }

}
