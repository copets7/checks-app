package com.yarosh.checks.controller.config;

import com.yarosh.checks.controller.dto.DiscountCardDto;
import com.yarosh.checks.controller.util.ApiDtoConverter;
import com.yarosh.checks.controller.util.DiscountCardApiDtoConverter;
import com.yarosh.checks.controller.util.PaginationDtoConverter;
import com.yarosh.checks.controller.util.ProductApiDtoConverter;
import com.yarosh.checks.controller.util.ProductApiDtoConverterImpl;
import com.yarosh.checks.controller.view.DiscountCardView;
import com.yarosh.checks.domain.DiscountCard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableOpenApi
@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = "com.yarosh.library.authentication.jwt.controller")
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
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()))
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

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}
