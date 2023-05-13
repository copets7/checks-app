package com.yarosh.checks.controller;

import com.yarosh.checks.controller.dto.ProductDto;
import com.yarosh.checks.controller.util.ApiDtoConverter;
import com.yarosh.checks.controller.view.ProductView;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.ProductId;
import com.yarosh.checks.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final CrudService<Product, ProductId> productService;
    private final ApiDtoConverter<ProductDto, ProductView, Product> productApiDtoConverter;

    public ProductController(CrudService<Product, ProductId> productService,
                             ApiDtoConverter<ProductDto, ProductView, Product> productApiDtoConverter) {
        this.productService = productService;
        this.productApiDtoConverter = productApiDtoConverter;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity<ProductView> add(final @RequestBody ProductDto productDto) {
        final Product product = productApiDtoConverter.convertDtoToDomain(productDto);
        final Product created = productService.add(product);

        return new ResponseEntity<>(productApiDtoConverter.convertDomainToView(created), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductView> getById(final @PathVariable("id") Long id) {
        return productService.get(new ProductId(id))
                .map(productApiDtoConverter::convertDomainToView)
                .map(view -> new ResponseEntity<>(view, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<ProductView>> getAll() {
        List<ProductView> products = productService.getAll()
                .stream()
                .map(productApiDtoConverter::convertDomainToView)
                .toList();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public ResponseEntity<ProductView> update(final @RequestBody ProductDto productDto) {
        final Product product = productApiDtoConverter.convertDtoToDomain(productDto);
        final Product updated = productService.update(product);

        return new ResponseEntity<>(productApiDtoConverter.convertDomainToView(updated), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<ProductView> delete(final @PathVariable Long id) {
        productService.delete(new ProductId(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
