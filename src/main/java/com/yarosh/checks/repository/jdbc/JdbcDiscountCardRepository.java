package com.yarosh.checks.repository.jdbc;

import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.DiscountCardEntity;
import com.yarosh.checks.repository.jdbc.executor.DefaultSqlExecutor;
import com.yarosh.checks.repository.jdbc.executor.SqlExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.yarosh.checks.repository.jdbc.executor.SqlExecutor.GENERATED_KEY_COLUMN_NUMBER;

public class JdbcDiscountCardRepository implements CrudRepository<DiscountCardEntity, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcDiscountCardRepository.class);

    private static final String SQL_INSERT = "INSERT INTO discount_cards (discount) VALUES (?)";
    private static final String SQL_SELECT = "SELECT id, discount FROM discount_cards WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT id, discount FROM discount_cards";
    private static final String SQL_UPDATE = "UPDATE discount_cards SET discount = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM discount_cards WHERE id = ?";

    private static final String DISCOUNT_CARDS_ID_FIELD = "id";
    private static final String DISCOUNT_CARDS_DISCOUNT_FIELD = "discount";

    private final SqlExecutor<DiscountCardEntity, Long> sqlExecutor;

    public JdbcDiscountCardRepository(DataSource dataSource) {
        this.sqlExecutor = new DefaultSqlExecutor<>(dataSource);
    }

    public JdbcDiscountCardRepository(SqlExecutor<DiscountCardEntity, Long> sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    @Override
    public DiscountCardEntity insert(DiscountCardEntity card) {
        LOGGER.debug("JDBC SQL card inserting starts, card: {}", card);
        DiscountCardEntity inserted = sqlExecutor.insert(SQL_INSERT, card, this::convertToParams, this::convertToEntity);
        LOGGER.debug("JDBC SQL card inserting processed, ID: {}", inserted.getId());
        LOGGER.trace("Inserted discount card: {}", inserted);

        return inserted;
    }

    @Override
    public Optional<DiscountCardEntity> select(Long id) {
        LOGGER.debug("JDBC SQL discount card searching by id starts, id: {}", id);
        Optional<DiscountCardEntity> selected = sqlExecutor.select(SQL_SELECT, id, this::convertToEntity);
        LOGGER.debug("JDBC SQL discount card searching by id processed, is present: {}", selected.isPresent());
        LOGGER.trace("Selected discount card: {}", selected);

        return selected;
    }

    @Override
    public List<DiscountCardEntity> selectAll() {
        LOGGER.debug("JDBC SQL selecting all discount cards starts");
        return sqlExecutor.selectAll(SQL_SELECT_ALL, this::convertToEntity);
    }

    @Override
    public DiscountCardEntity update(DiscountCardEntity card) {
        LOGGER.debug("JDBC SQL updating discount card starts, discount card: {}", card);
        DiscountCardEntity updated = sqlExecutor.update(SQL_UPDATE, card, this::convertToParams);
        LOGGER.debug("JDBC SQL updating discount card processed, product: {}", updated);
        LOGGER.trace("Updated discount card: {}", updated);

        return updated;
    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("JDBC SQL deleting discount card starts, id: {}", id);
        sqlExecutor.delete(SQL_DELETE, id);
    }

    private DiscountCardEntity convertToEntity(ResultSet resultSet) {
        try {
            return new DiscountCardEntity(
                    resultSet.getLong(DISCOUNT_CARDS_ID_FIELD),
                    resultSet.getDouble(DISCOUNT_CARDS_DISCOUNT_FIELD)
            );
        } catch (SQLException e) {
            LOGGER.error("Converting result set to discount card after insert failed, message: {}", e.getMessage());
            LOGGER.debug("Converting result set to discount card after insert failed", e);
            throw new JdbcRepositoryException("Converting result set to discount card failed, e: {0}", e);
        }
    }

    private DiscountCardEntity convertToEntity(ResultSet resultSet, DiscountCardEntity card) {
        try {
            return new DiscountCardEntity(resultSet.getLong(GENERATED_KEY_COLUMN_NUMBER), card.getDiscount());
        } catch (SQLException e) {
            LOGGER.error("Converting result set to discount card after insert failed, message: {}", e.getMessage());
            LOGGER.debug("Converting result set to discount card after insert failed", e);
            throw new JdbcRepositoryException("Converting result set to discount card after insert failed, e: {0}", e);
        }
    }

    private List<Object> convertToParams(DiscountCardEntity card) {
        LOGGER.trace("Converting card to params starts, card: {}", card);
        List<Object> params = new ArrayList<>();
        params.add(card.getDiscount());

        if (card.getId() != null) {
            params.add(card.getId());
        }

        LOGGER.trace("Converted card to params returning, {}", params);
        return params;
    }
}