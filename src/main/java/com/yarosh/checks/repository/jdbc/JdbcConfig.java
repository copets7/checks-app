package com.yarosh.checks.repository.jdbc;

import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.DiscountCardEntity;
import com.yarosh.checks.repository.entity.ProductEntity;
import com.yarosh.checks.repository.jdbc.executor.DefaultSqlExecutor;
import com.yarosh.checks.repository.jdbc.executor.SqlExecutor;
import com.yarosh.checks.repository.pool.DatabaseConnectionPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
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
    public DataSource dataSource() {
        try (DatabaseConnectionPool pool = new DatabaseConnectionPool("jdbc:mysql://localhost:3306/checks", "root", "70286_CopetS", 5)) {
            return pool;
        }
    }
}
