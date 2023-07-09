package com.yarosh.library.repository.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryApiConfig {

    @Bean
    public ProductsColumnConverter productsColumnConverter(ObjectMapper objectMapper) {
        return new ProductsColumnConverter(objectMapper);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
