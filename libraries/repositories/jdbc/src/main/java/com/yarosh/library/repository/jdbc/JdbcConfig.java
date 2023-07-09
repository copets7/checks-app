package com.yarosh.library.repository.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yarosh.library.repository.api.CrudRepository;
import com.yarosh.library.repository.api.ProductsColumnConverter;
import com.yarosh.library.repository.api.RepositoryApiConfig;
import com.yarosh.library.repository.api.entity.CheckEntity;
import com.yarosh.library.repository.api.entity.DiscountCardEntity;
import com.yarosh.library.repository.api.entity.ProductEntity;
import com.yarosh.library.repository.executor.SqlExecutor;
import com.yarosh.library.repository.executor.SqlExecutorConfig;
import com.yarosh.library.repository.pool.ConnectionPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RepositoryApiConfig.class, ConnectionPoolConfig.class, SqlExecutorConfig.class})
public class JdbcConfig {

    @Bean
    public CrudRepository<CheckEntity, Long> checkRepository(SqlExecutor<CheckEntity, Long> sqlExecutor, ProductsColumnConverter productsColumnConverter) {
        return new JdbcCheckRepository(sqlExecutor, productsColumnConverter);
    }

    @Bean
    public CrudRepository<DiscountCardEntity, Long> discountCardRepository(SqlExecutor<DiscountCardEntity, Long> sqlExecutor) {
        return new JdbcDiscountCardRepository(sqlExecutor);
    }

    @Bean
    public CrudRepository<ProductEntity, Long> productRepository(SqlExecutor<ProductEntity, Long> sqlExecutor) {
        return new JdbcProductRepository(sqlExecutor);
    }

    @Bean
    public ProductsColumnConverter productsColumnConverter(ObjectMapper objectMapper) {
        return new ProductsColumnConverter(objectMapper);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
