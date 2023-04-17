package com.yarosh.checks.repository.jdbc;

import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.DiscountCardEntity;
import com.yarosh.checks.repository.entity.ProductEntity;
import com.yarosh.checks.repository.jdbc.executor.DefaultSqlExecutor;
import com.yarosh.checks.repository.jdbc.executor.SqlExecutor;
import com.yarosh.checks.repository.pool.DatabaseConnectionPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class JdbcConfig {

    @Bean
    public CrudRepository<DiscountCardEntity, Long> discountCardRepository(SqlExecutor<DiscountCardEntity, Long> sqlExecutor) {
        return new JdbcDiscountCardRepository(sqlExecutor);
    }

    @Bean
    public SqlExecutor<DiscountCardEntity, Long> discountCardSqlExecutor(DataSource dataSource) {
        return new DefaultSqlExecutor<>(dataSource);
    }

    @Bean
    public CrudRepository<ProductEntity, Long> productRepository(SqlExecutor<ProductEntity, Long> sqlExecutor) {
        return new JdbcProductRepository(sqlExecutor);
    }

    @Bean
    public SqlExecutor<ProductEntity, Long> productSqlExecutor(DataSource dataSource) {
        return new DefaultSqlExecutor<>(dataSource);
    }

    @Bean
    public DataSource dataSource(@Value("${connection.pool.url}") String url,
                                 @Value("${connection.pool.username}") String username,
                                 @Value("${connection.pool.password}") String password,
                                 @Value("${connection.pool.size}") int size) {
        return new DatabaseConnectionPool(url, username, password, size);
    }
}
