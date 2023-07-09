package com.yarosh.library.repository.spring.data;

import com.yarosh.library.repository.api.RepositoryApiConfig;
import com.yarosh.library.repository.spring.data.decorator.DiscountCardRepositoryDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RepositoryApiConfig.class)
@ComponentScan("com.yarosh.library.repository.spring.data")
public class SpringDataRepositoryConfig {

    @Bean
    public DiscountCardRepositoryDecorator discountCardRepositoryDecorator(DiscountCardSpringDataRepository discountCardRepository) {
        return new DiscountCardRepositoryDecorator(discountCardRepository);
    }
}
