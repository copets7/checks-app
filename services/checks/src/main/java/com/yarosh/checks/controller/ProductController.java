package com.yarosh.checks.controller;

import com.yarosh.checks.controller.dto.ProductDto;
import com.yarosh.checks.controller.util.ApiDtoConverter;
import com.yarosh.checks.controller.view.ProductView;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.ProductId;
import com.yarosh.checks.service.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1.0/product")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final CrudService<Product, ProductId> productService;
    private final ApiDtoConverter<ProductDto, ProductView, Product> productApiDtoConverter;

    public ProductController(final CrudService<Product, ProductId> productService,
                             final ApiDtoConverter<ProductDto, ProductView, Product> productApiDtoConverter) {
        this.productService = productService;
        this.productApiDtoConverter = productApiDtoConverter;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity<ProductView> add(final @RequestBody ProductDto productDto) {
        LOGGER.info("Calling add product started, parameter: {}", productDto);
        LOGGER.debug("Product parameter: {}", productDto);

        final Product product = productApiDtoConverter.convertDtoToDomain(productDto);
        final Product created = productService.add(product);
        final ProductView view = productApiDtoConverter.convertDomainToView(created);

        LOGGER.info("Calling add product successfully ended for product, value: {}", view.id());
        LOGGER.debug("Saved product view detailed printing: {}", view);

        return new ResponseEntity<>(view, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductView> getById(final @PathVariable("id") Long id) {
        LOGGER.info("Calling getById product started for value: {}", id);

        return productService.get(new ProductId(id))
                .map(productApiDtoConverter::convertDomainToView)
                .map(view -> new ResponseEntity<>(view, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<ProductView>> getAll() {
        LOGGER.info("Calling getAll products started");

        List<ProductView> products = productService.getAll()
                .stream()
                .map(productApiDtoConverter::convertDomainToView)
                .toList();

        LOGGER.debug("Product views detailed printing: {}", products);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public ResponseEntity<ProductView> update(final @RequestBody ProductDto productDto) {
        LOGGER.info("Calling update product started for product with value: {}", productDto.id());
        LOGGER.debug("Product parameter: {}", productDto);

        final Product product = productApiDtoConverter.convertDtoToDomain(productDto);
        final Product updated = productService.update(product);
        final ProductView view = productApiDtoConverter.convertDomainToView(updated);

        LOGGER.info("Calling updated product successfully ended for product, value: {}", view.id());
        LOGGER.debug("Updated product view detailed printing: {}", view);

        return new ResponseEntity<>(view, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<ProductView> delete(final @PathVariable Long id) {
        LOGGER.info("Calling delete product started for product, value: {}", id);
        productService.delete(new ProductId(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
