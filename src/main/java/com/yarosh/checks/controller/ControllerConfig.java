package com.yarosh.checks.controller;

import com.yarosh.checks.controller.dto.ProductDto;
import com.yarosh.checks.controller.util.Converter;
import com.yarosh.checks.controller.util.ProductConverter;
import com.yarosh.checks.controller.view.ProductView;
import com.yarosh.checks.domain.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Bean
    public Converter<ProductDto, ProductView, Product> productControllerConverter() {
        return new ProductConverter();
    }
}
