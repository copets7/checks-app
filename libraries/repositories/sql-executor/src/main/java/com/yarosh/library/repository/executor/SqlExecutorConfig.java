package com.yarosh.library.repository.executor;

import com.yarosh.library.repository.api.entity.CheckEntity;
import com.yarosh.library.repository.api.entity.DiscountCardEntity;
import com.yarosh.library.repository.api.entity.ProductEntity;
import com.yarosh.library.repository.pool.ConnectionPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Configuration
@Import(ConnectionPoolConfig.class)
public class SqlExecutorConfig {

    @Bean
    public SqlExecutor<CheckEntity, Long> checkSqlExecutor(DataSource dataSource) {
        return new DefaultSqlExecutor<>(dataSource);
    }

    @Bean
    public SqlExecutor<DiscountCardEntity, Long> discountCardSqlExecutor(DataSource dataSource) {
        return new DefaultSqlExecutor<>(dataSource);
    }

    @Bean
    public SqlExecutor<ProductEntity, Long> productSqlExecutor(DataSource dataSource) {
        return new DefaultSqlExecutor<>(dataSource);
    }
}
