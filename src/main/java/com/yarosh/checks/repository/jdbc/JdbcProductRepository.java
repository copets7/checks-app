package com.yarosh.checks.repository.jdbc;

import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.ProductEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcProductRepository implements CrudRepository<ProductEntity, Long> {

    private static final String SQL_INSERT = "INSERT INTO products (description, price, discount) VALUES (?,?,?)";
    private static final String SQL_FIND_BY_ID = "SELECT id, description, price, discount FROM products WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT id, description, price, discount FROM products";
    private static final String SQL_UPDATE = "UPDATE products SET description = ?, price = ?, discount = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM products WHERE id = ?";

    private static final String PRODUCT_ID_FIELD = "id";
    private static final String PRODUCT_DESCRIPTION_FIELD = "description";
    private static final String PRODUCT_PRICE_FIELD = "price";
    private static final String PRODUCT_DISCOUNT_FIELD = "discount";

    private static final int NO_ROWS_AFFECTED = 0;

    private final DataSource dataSource;

    public JdbcProductRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ProductEntity insert(ProductEntity product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, product.getDescription());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setDouble(3, product.getDiscount());
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return new ProductEntity(resultSet.getLong(1), product.getDescription(), product.getPrice(), product.getDiscount());
                }

                throw new JdbcRepositoryException("ResultSet is empty after inserting, product: {0}", product);
            }
        } catch (SQLException e) {
            throw new JdbcRepositoryException("SQL insert query failed, e: {0}", e);
        }
    }

    @Override
    public Optional<ProductEntity> find(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID);
        ) {
            ProductEntity product = null;
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    product = new ProductEntity(resultSet.getLong(PRODUCT_ID_FIELD),
                            resultSet.getString(PRODUCT_DESCRIPTION_FIELD),
                            resultSet.getDouble(PRODUCT_PRICE_FIELD),
                            resultSet.getDouble(PRODUCT_DISCOUNT_FIELD)
                    );
                }
            }

            return Optional.ofNullable(product);
        } catch (SQLException e) {
            throw new JdbcRepositoryException("SQL find by id query failed, e: {0}", e);
        }
    }

    @Override
    public List<ProductEntity> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL)
        ) {
            List<ProductEntity> products = new ArrayList<>();
            while (resultSet.next()) {
                ProductEntity product = new ProductEntity(resultSet.getLong(PRODUCT_ID_FIELD),
                        resultSet.getString(PRODUCT_DESCRIPTION_FIELD),
                        resultSet.getDouble(PRODUCT_PRICE_FIELD),
                        resultSet.getDouble(PRODUCT_DISCOUNT_FIELD)
                );
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new JdbcRepositoryException("SQL find all query failed, e: {0}", e);
        }
    }

    @Override
    public ProductEntity update(ProductEntity product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
        ) {
            preparedStatement.setString(1, product.getDescription());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setDouble(3, product.getDiscount());
            preparedStatement.setLong(4, product.getId());

            if (preparedStatement.executeUpdate() == NO_ROWS_AFFECTED) {
                throw new JdbcRepositoryException("SQL update query failed, there is no ID {0} in discount cards table", product.getId());
            }

            return product;
        } catch (SQLException e) {
            throw new JdbcRepositoryException("SQL update query failed, e: {0}", e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new JdbcRepositoryException("SQL delete query failed, e: {0}", e);
        }
    }
}
