package com.yarosh.checks.service;

import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.ProductId;
import com.yarosh.checks.domain.pagination.ContentPage;
import com.yarosh.checks.domain.pagination.ContentPageRequest;
import com.yarosh.checks.service.util.converter.BidirectionalConverter;
import com.yarosh.checks.service.util.converter.PaginationConverter;
import com.yarosh.library.repository.api.CrudRepository;
import com.yarosh.library.repository.api.entity.ProductEntity;
import com.yarosh.library.repository.api.pagination.RepositoryPage;
import com.yarosh.library.repository.api.pagination.RepositoryPageRequest;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ProductService implements CrudService<Product, ProductId> {

    private final CrudRepository<ProductEntity, Long> productRepository;
    private final BidirectionalConverter<Product, ProductEntity> productConverter;
    private final PaginationConverter paginationConverter;

    @Inject
    public ProductService(final CrudRepository<ProductEntity, Long> productRepository,
                          final BidirectionalConverter<Product, ProductEntity> productConverter,
                          final PaginationConverter paginationConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.paginationConverter = paginationConverter;
    }

    @Override
    public Product add(Product product) {
        return upsert(productRepository::insert, product);
    }

    @Override
    public Product getOrThrow(ProductId id) {
        return get(id).orElseThrow(ObjectNotFoundException.supplier(id.value(), "Product"));
    }

    @Override
    public Optional<Product> get(ProductId id) {
        return productRepository.select(id.value())
                .map(productConverter::convertToDomain);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.selectAll()
                .stream()
                .map(productConverter::convertToDomain)
                .toList();
    }

    @Override
    public ContentPage<Product> getAll(ContentPageRequest pageRequest) {
        final RepositoryPageRequest databasePageRequest = paginationConverter.convertToRepositoryPageRequest(pageRequest);
        final RepositoryPage<ProductEntity> databasePage = productRepository.selectAll(databasePageRequest);

        return paginationConverter.convertToContentPage(
                databasePage,
                products -> products.stream().map(productConverter::convertToDomain).toList(),
                pageRequest.pageNumber() + 1
        );
    }

    @Override
    public Product update(Product product) {
        return upsert(productRepository::update, product);
    }

    @Override
    public void delete(ProductId id) {
        productRepository.delete(id.value());
    }

    private Product upsert(Function<ProductEntity, ProductEntity> upsert, Product product) {
        final ProductEntity upsertedProduct = upsert.apply(productConverter.convertToEntity(product));
        return productConverter.convertToDomain(upsertedProduct);
    }
}
