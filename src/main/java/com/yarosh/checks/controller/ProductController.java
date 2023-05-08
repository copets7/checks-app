package com.yarosh.checks.controller;

import com.yarosh.checks.controller.dto.ProductDto;
import com.yarosh.checks.controller.view.ProductView;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.ProductId;
import com.yarosh.checks.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final CrudService<Product, ProductId> productService;

    public ProductController(CrudService<Product, ProductId> productService) {
        this.productService = productService;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity<ProductDto> add(@RequestBody ProductDto productDto) {
        Product product = new Product(
                Optional.empty(),
                productDto.getDescription(),
                Optional.empty(),
                productDto.getPrice(),
                productDto.getDiscount());

        productService.add(product);

        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Stream<ProductView> product = productService.get(new ProductId(id))
                .stream()
                .map(p -> new ProductView(
                        p.getDescription(),
                        p.getPrice(),
                        p.getDiscount()));

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        List<ProductView> products = productService
                .getAll()
                .stream()
                .map(product ->
                        new ProductView(
                                product.getDescription(),
                                product.getPrice(),
                                product.getDiscount())
                ).toList();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(path = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ProductDto> update(@RequestBody ProductDto productDto, @PathVariable Long id) {

        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

}
