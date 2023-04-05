package com.yarosh.checks.repository.jdbc;

import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.DiscountCardEntity;
import com.yarosh.checks.repository.jdbc.executor.SqlExecutor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private static final int GENERATED_KEY_COLUMN_NUMBER = 1;

    private final DataSource dataSource;
    private final SqlExecutor<DiscountCardEntity, Long> sqlExecutor;

    public JdbcDiscountCardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.sqlExecutor = new SqlExecutor<>(dataSource, this::convertToEntity, this::convertToParams);
    }

    @Override
    public DiscountCardEntity insert(DiscountCardEntity card) {
        return sqlExecutor.insert(SQL_INSERT, card, this::convertToEntity);
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
        return sqlExecutor.update(SQL_UPDATE, card);
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

    private DiscountCardEntity convertToEntity(ResultSet resultSet, DiscountCardEntity card) {
        try {
            return new DiscountCardEntity(resultSet.getLong(GENERATED_KEY_COLUMN_NUMBER), card.getDiscount());
        } catch (SQLException e) {
            throw new JdbcRepositoryException("Converting result set to discount card after insert failed, e: {0}", e);
        }
    }

    private List<Object> convertToParams(DiscountCardEntity card) {
        List<Object> params = new ArrayList<>();
        params.add(card.getDiscount());

        if (card.getId() != null) {
            params.add(card.getId());
        }

        return params;
    }
}