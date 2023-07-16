package com.yarosh.library.repository.spring.data;

import com.yarosh.library.repository.api.RepositoryApiConfig;
import com.yarosh.library.repository.api.entity.CheckEntity;
import com.yarosh.library.repository.api.entity.DiscountCardEntity;
import com.yarosh.library.repository.api.entity.ProductEntity;
import com.yarosh.library.repository.spring.data.decorator.SpringDataRepositoryDecorator;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import(RepositoryApiConfig.class)
@ComponentScan("com.yarosh.library.repository.spring.data")
@EnableJpaRepositories("com.yarosh.library.repository.spring.data")
@EntityScan("com.yarosh.library.repository.api.entity")
public class SpringDataRepositoryConfig {

    @Bean
    public SpringDataRepositoryDecorator<DiscountCardEntity, Long> discountCardRepositoryDecorator(
            DiscountCardSpringDataRepository discountCardRepository
    ) {
        return new SpringDataRepositoryDecorator<>(discountCardRepository);
    }

    @Bean
    public SpringDataRepositoryDecorator<ProductEntity, Long> productRepositoryDecorator(
            ProductSpringDataRepository productRepository
    ) {
        return new SpringDataRepositoryDecorator<>(productRepository);
    }

    @Bean
    public SpringDataRepositoryDecorator<CheckEntity, Long> checkRepositoryDecorator(
            CheckSpringDataRepository checkRepository
    ) {
        return new SpringDataRepositoryDecorator<>(checkRepository);
    }
}
