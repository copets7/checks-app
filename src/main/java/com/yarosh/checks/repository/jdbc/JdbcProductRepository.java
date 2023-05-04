package com.yarosh.checks.repository.jdbc;

import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.ProductEntity;
import com.yarosh.checks.repository.jdbc.executor.DefaultSqlExecutor;
import com.yarosh.checks.repository.jdbc.executor.SqlExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.yarosh.checks.repository.jdbc.executor.SqlExecutor.GENERATED_KEY_COLUMN_NUMBER;

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

    private final SqlExecutor<ProductEntity, Long> sqlExecutor;

    public JdbcProductRepository(DataSource dataSource) {
        this.sqlExecutor = new DefaultSqlExecutor<>(dataSource);
    }

    @Inject
    public JdbcProductRepository(SqlExecutor<ProductEntity, Long> sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    @Override
    public ProductEntity insert(ProductEntity product) {
        LOGGER.debug("JDBC SQL product inserting starts, product: {}", product);
        ProductEntity inserted = sqlExecutor.insert(SQL_INSERT, product, this::convertToParams, this::convertToEntity);
        LOGGER.debug("JDBC SQL product inserting processed, ID: {}", inserted.getId());
        LOGGER.trace("Inserted product: {}", inserted);

        return inserted;
    }

    @Override
    public Optional<ProductEntity> select(Long id) {
        LOGGER.debug("JDBC SQL product searching by id starts, id: {}", id);
        Optional<ProductEntity> selected = sqlExecutor.select(SQL_SELECT, id, this::convertToEntity);
        LOGGER.debug("JDBC SQL product searching by id processed, is present: {}", selected.isPresent());
        LOGGER.trace("Selected product: {}", selected);

        return selected;
    }

    @Override
    public List<ProductEntity> selectAll() {
        LOGGER.debug("JDBC SQL selecting all products starts");
        return sqlExecutor.selectAll(SQL_SELECT_ALL, this::convertToEntity);
    }

    @Override
    public ProductEntity update(ProductEntity product) {
        LOGGER.debug("JDBC SQL updating product starts, product: {}", product);
        ProductEntity updated = sqlExecutor.update(SQL_UPDATE, product, this::convertToParams);
        LOGGER.debug("JDBC SQL updating product processed, product: {}", updated);
        LOGGER.trace("Updated product: {}", updated);

        return updated;
    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("JDBC SQL deleting product starts, id: {}", id);
        sqlExecutor.delete(SQL_DELETE, id);
    }

    private ProductEntity convertToEntity(ResultSet resultSet) {
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
        try {
            return new ProductEntity(resultSet.getLong(GENERATED_KEY_COLUMN_NUMBER),
                    product.getDescription(),
                    product.getPrice(),
                    product.getDiscount());

        } catch (SQLException e) {
            LOGGER.error("Converting result set to product after insert failed, message: {}", e.getMessage());
            LOGGER.debug("Converting result set to product after insert failed", e);
            throw new JdbcRepositoryException("Converting result set to product after insert failed, e: {0}", e);
        }
    }

    private List<Object> convertToParams(ProductEntity product) {
        LOGGER.trace("Converting product to params starts, product: {}", product);
        List<Object> params = new ArrayList<>();
        params.add(product.getDescription());
        params.add(product.getPrice());
        params.add(product.getDiscount());

        if (product.getId() != null) {
            params.add(product.getId());
        }

        LOGGER.trace("Converted product to params returning, {}", params);
        return params;
    }
}
