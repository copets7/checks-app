package com.yarosh.library.repository.pool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ConnectionPoolConfig {

    @Bean
    public DataSource dataSource(@Value("${connection.pool.url}") String url,
                                 @Value("${connection.pool.username}") String username,
                                 @Value("${connection.pool.password}") String password,
                                 @Value("${connection.pool.size}") int size) {
        return new SynchronizedDatabaseConnectionPool(new DatabaseConnectionPool(url, username, password, size));
    }
}
