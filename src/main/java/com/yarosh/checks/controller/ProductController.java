package com.yarosh.checks.controller;

import com.yarosh.checks.controller.dto.ProductDto;
import com.yarosh.checks.controller.util.Converter;
import com.yarosh.checks.controller.view.ProductView;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.ProductId;
import com.yarosh.checks.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final CrudService<Product, ProductId> productService;
    private final Converter<ProductDto, ProductView, Product> productConverter;

    public ProductController(CrudService<Product, ProductId> productService, Converter<ProductDto, ProductView, Product> productConverter) {
        this.productService = productService;
        this.productConverter = productConverter;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity<ProductView> add(final @RequestBody ProductDto productDto) {
        final Product product = productConverter.dtoConvertToDomain(productDto);
        final ProductView productView = productConverter.dtoConvertToView(productDto);

        productService.add(product);

        return new ResponseEntity<>(productView, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductView> getById(final @PathVariable("id") Long id) {
        Optional<ProductView> productView = productService.get(new ProductId(id))
                .map(productConverter::domainConvertToView);

        return productView.isPresent()
                ? new ResponseEntity<>(productView.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<ProductView>> getAll() {
        List<ProductView> products = productService.getAll()
                .stream()
                .map(productConverter::domainConvertToView)
                .toList();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public ResponseEntity<ProductView> update(final @RequestBody ProductDto productDto) {
        final Product product = productConverter.dtoConvertToDomain(productDto);
        final ProductView productView = productConverter.dtoConvertToView(productDto);

        productService.update(product);

        return new ResponseEntity<>(productView, HttpStatus.OK);
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ProductView> delete(final @PathVariable Long id) {

        productService.delete(new ProductId(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
