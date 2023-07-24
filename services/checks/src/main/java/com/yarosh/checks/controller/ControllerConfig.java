package com.yarosh.checks.controller;

import com.yarosh.checks.controller.dto.DiscountCardDto;
import com.yarosh.checks.controller.util.ApiDtoConverter;
import com.yarosh.checks.controller.util.DiscountCardApiDtoConverter;
import com.yarosh.checks.controller.util.PaginationDtoConverter;
import com.yarosh.checks.controller.util.ProductApiDtoConverter;
import com.yarosh.checks.controller.util.ProductApiDtoConverterImpl;
import com.yarosh.checks.controller.view.DiscountCardView;
import com.yarosh.checks.domain.DiscountCard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableOpenApi
@EnableSwagger2
@EnableWebMvc
public class ControllerConfig implements WebMvcConfigurer {

    @Bean
    public PaginationDtoConverter paginationDtoConverter() {
        return new PaginationDtoConverter();
    }

    @Bean
    public ProductApiDtoConverter productApiDtoConverter() {
        return new ProductApiDtoConverterImpl();
    }

    @Bean
    public ApiDtoConverter<DiscountCardDto, DiscountCardView, DiscountCard> discountCardApiDtoConverter() {
        return new DiscountCardApiDtoConverter();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Checks REST API",
                "This API is using by checks machines",
                "1.0.0",
                "",
                new Contact("Andrey Yarosh", "https://github.com/copets7", "andreyarosh7@gmail.com"),
                "Apache license",
                "https://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList()
        );
    }
}
