package com.yarosh.checks.repository.jdbc;

import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.DiscountCardEntity;
import com.yarosh.checks.repository.pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcDiscountCardRepository implements CrudRepository<DiscountCardEntity, Long> {

    private static final String SQL_SELECT_ALL = "SELECT id, discount FROM discount_cards";

    private static final String DISCOUNT_CARDS_ID_FIELD = "id";
    private static final String DISCOUNT_CARDS_DISCOUNT_FIELD = "discount";

    private final DatabaseConnectionPool connectionPool;

    public JdbcDiscountCardRepository(DatabaseConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public DiscountCardEntity insert(DiscountCardEntity card) {
        return null;
    }

    @Override
    public Optional<DiscountCardEntity> find(Long id) {
        return Optional.empty();
    }

    @Override
    public List<DiscountCardEntity> findAll() {
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL)
        ) {
            List<DiscountCardEntity> cards = new ArrayList<>();
            while (resultSet.next()) {
                DiscountCardEntity card = new DiscountCardEntity(
                        resultSet.getLong(DISCOUNT_CARDS_ID_FIELD),
                        resultSet.getDouble(DISCOUNT_CARDS_DISCOUNT_FIELD)
                );
                cards.add(card);
            }
            return cards;
        } catch (SQLException e) {
            throw new JdbcRepositoryException("SQL select all query failed, e: {0}", e);
        }
    }

    @Override
    public DiscountCardEntity update(DiscountCardEntity card) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
