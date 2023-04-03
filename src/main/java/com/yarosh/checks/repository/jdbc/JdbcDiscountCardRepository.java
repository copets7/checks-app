package com.yarosh.checks.repository.jdbc;

import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.DiscountCardEntity;
import com.yarosh.checks.repository.jdbc.executor.SqlExecutor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcDiscountCardRepository implements CrudRepository<DiscountCardEntity, Long> {

    private static final String SQL_INSERT = "INSERT INTO discount_cards (discount) VALUES (?)";
    private static final String SQL_SELECT = "SELECT id, discount FROM discount_cards WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT id, discount FROM discount_cards";
    private static final String SQL_UPDATE = "UPDATE discount_cards SET discount = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM discount_cards WHERE id = ?";

    private static final String DISCOUNT_CARDS_ID_FIELD = "id";
    private static final String DISCOUNT_CARDS_DISCOUNT_FIELD = "discount";

    private final DataSource dataSource;
    private final SqlExecutor<DiscountCardEntity, Long> sqlExecutor;

    public JdbcDiscountCardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.sqlExecutor = new SqlExecutor<>(dataSource, this::convertToEntity);
    }

    @Override
    public DiscountCardEntity insert(DiscountCardEntity card) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            prepareStatement.setDouble(1, card.getDiscount());
            prepareStatement.executeUpdate();

            try (ResultSet resultSet = prepareStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return new DiscountCardEntity(resultSet.getLong(1), card.getDiscount());
                }

                throw new JdbcRepositoryException("ResultSet is empty after inserting, product: {0}", card);
            }
        } catch (SQLException e) {
            throw new JdbcRepositoryException("SQL insert query failed, e: {0}", e);
        }
    }

    @Override
    public Optional<DiscountCardEntity> select(Long id) {
        return sqlExecutor.select(SQL_SELECT, id);
    }

    @Override
    public List<DiscountCardEntity> selectAll() {
        return sqlExecutor.selectAll(SQL_SELECT_ALL);
    }

    @Override
    public DiscountCardEntity update(DiscountCardEntity card) {
        return sqlExecutor.update(SQL_UPDATE, card, this::convertToParams);
    }

    @Override
    public void delete(Long id) {
        sqlExecutor.delete(SQL_DELETE, id);
    }

    private DiscountCardEntity convertToEntity(ResultSet resultSet) {
        try {
            return new DiscountCardEntity(
                    resultSet.getLong(DISCOUNT_CARDS_ID_FIELD),
                    resultSet.getDouble(DISCOUNT_CARDS_DISCOUNT_FIELD)
            );
        } catch (SQLException e) {
            throw new JdbcRepositoryException("Converting result set to discount card failed, e: {0}", e);
        }
    }

    private List<Object> convertToParams(DiscountCardEntity card) {
        List<Object> params = new ArrayList<>();
        params.add(card.getDiscount());
        params.add(card.getId());

        return params;
    }
}