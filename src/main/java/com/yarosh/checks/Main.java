package com.yarosh.checks;

import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.DiscountCardId;
import com.yarosh.checks.domain.id.ProductId;
import com.yarosh.checks.repository.jdbc.JdbcConfig;
import com.yarosh.checks.service.CrudService;
import com.yarosh.checks.service.DiscountCardService;
import com.yarosh.checks.service.ProductService;
import com.yarosh.checks.service.ServiceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class, ServiceConfig.class);
        CrudService<Product, ProductId> productService = context.getBean(ProductService.class);
        CrudService<DiscountCard, DiscountCardId> discountCardService = context.getBean(DiscountCardService.class);
      //  CrudService<Check, CheckId> checkService = context.getBean(CheckService.class);

      //  productService.add(new Product(Optional.empty(),"BREAD", Optional.ofNullable(2),23.7,10));
        productService.getAll().forEach(System.out::println);
    }
}
