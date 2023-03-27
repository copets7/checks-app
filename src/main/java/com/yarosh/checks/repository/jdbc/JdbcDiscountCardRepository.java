package com.yarosh.checks.repository.jdbc;

import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.DiscountCardEntity;
import com.yarosh.checks.repository.pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcDiscountCardRepository implements CrudRepository<DiscountCardEntity, Long> {

    private static final String SQL_SELECT_ALL = "SELECT id, discount FROM discount_cards";
    private static final String SQL_INSERT = "INSERT INTO discount_cards (discount) VALUES (?)";
    private static final String SQL_FIND_BY_ID = "SELECT id, discount FROM discount_cards WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE discount_cards SET discount = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM discount_cards WHERE id = ?";

    private static final String DISCOUNT_CARDS_ID_FIELD = "id";
    private static final String DISCOUNT_CARDS_DISCOUNT_FIELD = "discount";

    private static final int NO_ROWS_AFFECTED = 0;

    private final DatabaseConnectionPool connectionPool;

    public JdbcDiscountCardRepository(DatabaseConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public DiscountCardEntity insert(DiscountCardEntity card) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            prepareStatement.setDouble(1, card.getDiscount());
            prepareStatement.executeUpdate();

            try (ResultSet resultSet = prepareStatement.getGeneratedKeys()) {
                return new DiscountCardEntity(resultSet.getLong(DISCOUNT_CARDS_ID_FIELD), card.getDiscount());
            }
        } catch (SQLException e) {
            throw new JdbcRepositoryException("SQL insert query failed, e: {0}", e);
        }
    }

    @Override
    public Optional<DiscountCardEntity> find(Long id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)
        ) {
            DiscountCardEntity card = null;
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    card = new DiscountCardEntity(
                            resultSet.getLong(DISCOUNT_CARDS_ID_FIELD),
                            resultSet.getDouble(DISCOUNT_CARDS_DISCOUNT_FIELD)
                    );
                }
            }

            return Optional.ofNullable(card);
        } catch (SQLException e) {
            throw new JdbcRepositoryException("SQL find by id query failed, e: {0}", e);
        }
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
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)
        ) {
            statement.setDouble(1, card.getDiscount());
            statement.setLong(2, card.getId());

            if (statement.executeUpdate() == NO_ROWS_AFFECTED) {
                throw new JdbcRepositoryException("SQL update query failed, there is no ID {0} in discount cards table", card.getId());
            }

            return card;
        } catch (SQLException e) {
            throw new JdbcRepositoryException("SQL update query failed, e: {0}", e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE)
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new JdbcRepositoryException("SQL delete query failed, e: {0}", e);
        }
    }
}