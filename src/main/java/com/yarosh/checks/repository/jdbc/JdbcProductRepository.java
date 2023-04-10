package com.yarosh.checks.repository.jdbc;

import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.ProductEntity;
import com.yarosh.checks.repository.jdbc.executor.SqlExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcProductRepository implements CrudRepository<ProductEntity, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcProductRepository.class);

    private static final String SQL_INSERT = "INSERT INTO products (description, price, discount) VALUES (?,?,?)";
    private static final String SQL_SELECT = "SELECT id, description, price, discount FROM products WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT id, description, price, discount FROM products";
    private static final String SQL_UPDATE = "UPDATE products SET description = ?, price = ?, discount = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM products WHERE id = ?";

    private static final String PRODUCT_ID_FIELD = "id";
    private static final String PRODUCT_DESCRIPTION_FIELD = "description";
    private static final String PRODUCT_PRICE_FIELD = "price";
    private static final String PRODUCT_DISCOUNT_FIELD = "discount";

    private final DataSource dataSource;
    private final SqlExecutor<ProductEntity, Long> sqlExecutor;
    private static final int GENERATED_KEY_COLUMN_NUMBER = 1;

    public JdbcProductRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.sqlExecutor = new SqlExecutor<>(dataSource, this::convertToEntity, this::convertToParams);
    }

    @Override
    public ProductEntity insert(ProductEntity product) {
        LOGGER.info("JdbcProductRepository starts inserting product");
        return sqlExecutor.insert(SQL_INSERT, product, this::convertToEntity);
    }

    @Override
    public Optional<ProductEntity> select(Long id) {
        LOGGER.info("JdbcProductRepository starts searching product by id");
        return sqlExecutor.select(SQL_SELECT, id);
    }

    @Override
    public List<ProductEntity> selectAll() {
        LOGGER.info("JdbcProductRepository starts selecting all products");
        return sqlExecutor.selectAll(SQL_SELECT_ALL);
    }

    @Override
    public ProductEntity update(ProductEntity product) {
        LOGGER.info("JdbcProductRepository starts updating product");
        return sqlExecutor.update(SQL_UPDATE, product);
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("JdbcProductRepository starts deleting product");
        sqlExecutor.delete(SQL_DELETE, id);
    }

    private ProductEntity convertToEntity(ResultSet resultSet) {
        LOGGER.debug("JdbcProductRepository starts convert Result set to product, result set: {}", resultSet);
        try {
            return new ProductEntity(
                    resultSet.getLong(PRODUCT_ID_FIELD),
                    resultSet.getString(PRODUCT_DESCRIPTION_FIELD),
                    resultSet.getDouble(PRODUCT_PRICE_FIELD),
                    resultSet.getDouble(PRODUCT_DISCOUNT_FIELD)
            );

        } catch (SQLException e) {
            LOGGER.error("Converting result set to product failed, message: {}", e.getMessage());
            LOGGER.debug("Converting result set to product failed", e);
            throw new JdbcRepositoryException("Converting result set to product failed, e: {0}", e);
        }
    }

    private ProductEntity convertToEntity(ResultSet resultSet, ProductEntity product) {
        LOGGER.debug("JdbcProductRepository starts convert Result set to product, result set: {}", resultSet);
        try {
            return new ProductEntity(resultSet.getLong(GENERATED_KEY_COLUMN_NUMBER),
                    product.getDescription(),
                    product.getPrice(),
                    product.getDiscount());

        } catch (SQLException e) {
            LOGGER.error("Converting result set to product after insert failed, message: {}", e.getMessage());
            LOGGER.debug("Converting result set to product after insert failed, {0}", e);
            throw new JdbcRepositoryException("Converting result set to product after insert failed, e: {0}", e);
        }
    }

    private List<Object> convertToParams(ProductEntity product) {
        LOGGER.debug("JdbcProductRepository starts convert product to params, product: {}", product);
        List<Object> params = new ArrayList<>();
        params.add(product.getDescription());
        params.add(product.getPrice());
        params.add(product.getDiscount());

        if (product.getId() != null) {
            params.add(product.getId());
        }

        LOGGER.debug("JdbcProductRepository starts return params, {}", params);
        return params;
    }
}
