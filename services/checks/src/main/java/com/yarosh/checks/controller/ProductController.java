package com.yarosh.checks.controller;

import com.yarosh.checks.controller.dto.ProductDto;
import com.yarosh.checks.controller.util.ApiDtoConverter;
import com.yarosh.checks.controller.util.PaginationDtoConverter;
import com.yarosh.checks.controller.view.ContentPageView;
import com.yarosh.checks.controller.view.ProductView;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.ProductId;
import com.yarosh.checks.domain.pagination.ContentPage;
import com.yarosh.checks.domain.pagination.ContentPageRequest;
import com.yarosh.checks.service.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1.0/product")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final CrudService<Product, ProductId> productService;
    private final ApiDtoConverter<ProductDto, ProductView, Product> productApiDtoConverter;
    private final PaginationDtoConverter paginationDtoConverter;

    public ProductController(final CrudService<Product, ProductId> productService,
                             final ApiDtoConverter<ProductDto, ProductView, Product> productApiDtoConverter,
                             final PaginationDtoConverter paginationDtoConverter) {
        this.productService = productService;
        this.productApiDtoConverter = productApiDtoConverter;
        this.paginationDtoConverter = paginationDtoConverter;
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
    public ResponseEntity<ContentPageView<ProductView>> getAll(
            final @RequestParam(name = "page") Integer page,
            final @RequestParam(name = "size") Integer size,
            final @RequestParam(name = "column", required = false) String column,
            final @RequestParam(name = "isDesc", required = false) Boolean isDesc
    ) {
        LOGGER.info("Calling getAll products started, page: {}, size: {}, column: {}, isDesc: {}", page, size, column, isDesc);

        final ContentPageRequest pageRequest = paginationDtoConverter.convertToContentPageRequest(page, size, column, isDesc);
        final ContentPage<Product> pageContent = productService.getAll(pageRequest);

        final ContentPageView<ProductView> pageView = paginationDtoConverter.convertToContentPageView(pageContent,
                products -> products.stream().map(productApiDtoConverter::convertDomainToView).toList()
        );

        LOGGER.debug("Products page view detailed printing: {}", pageView);

        return new ResponseEntity<>(pageView, HttpStatus.OK);
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
