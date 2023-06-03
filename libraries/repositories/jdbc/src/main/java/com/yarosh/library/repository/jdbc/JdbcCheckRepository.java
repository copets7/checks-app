package com.yarosh.library.repository.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yarosh.library.repository.api.CrudRepository;
import com.yarosh.library.repository.api.entity.CheckEntity;
import com.yarosh.library.repository.api.entity.DiscountCardEntity;
import com.yarosh.library.repository.api.entity.ProductEntity;
import com.yarosh.library.repository.jdbc.executor.DefaultSqlExecutor;
import com.yarosh.library.repository.jdbc.executor.SqlExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.yarosh.library.repository.jdbc.executor.SqlExecutor.GENERATED_KEY_COLUMN_NUMBER;

public class JdbcCheckRepository implements CrudRepository<CheckEntity, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcCheckRepository.class);

    private static final String SQL_INSERT = """
            INSERT INTO checks (market_name, cashier_name, date, time, products, discount_card_id, total_price)
            VALUES (?,?,?,?,?,?,?)
            """;
    private static final String SQL_SELECT = """
            SELECT c.id, c.market_name, c.cashier_name, c.date, c.time, c.products, c.discount_card_id, c.total_price,
            d.id, d.discount
            FROM checks AS c LEFT JOIN discount_cards AS d ON c.discount_card_id = d.id WHERE c.id = ?
            """;
    private static final String SQL_SELECT_ALL =
            "SELECT id, market_name, cashier_name, date, time, products, discount_card_id, total_price FROM checks";
    private static final String SQL_UPDATE = """
            UPDATE checks SET market_name = ?, cashier_name = ?, date = ?, time = ?,  products = ?, discount_card_id = ?,
            total_price = ? WHERE id = ?
            """;
    private static final String SQL_DELETE = "DELETE FROM checks WHERE id = ?";

    private static final String CHECK_ID_FIELD = "id";
    private static final String CHECK_MARKET_NAME_FIELD = "market_name";
    private static final String CHECK_CASHIER_NAME_FIELD = "cashier_name";
    private static final String CHECK_DATE_FIELD = "date";
    private static final String CHECK_TIME_FIELD = "time";
    private static final String CHECK_PRODUCTS_FIELD = "products";
    private static final String CHECK_TOTAL_PRICE_FIELD = "total_price";
    private static final String DISCOUNT_CARD_ID_FIELD = "d.id";
    private static final String DISCOUNT_CARD_DISCOUNT_FIELD="d.discount";

    private final SqlExecutor<CheckEntity, Long> sqlExecutor;
    private final ObjectMapper objectMapper;

    @Inject
    public JdbcCheckRepository(DataSource dataSource, ObjectMapper objectMapper) {
        this.sqlExecutor = new DefaultSqlExecutor<>(dataSource);
        this.objectMapper = objectMapper;
    }

    @Inject
    public JdbcCheckRepository(final SqlExecutor<CheckEntity, Long> sqlExecutor, ObjectMapper objectMapper) {
        this.sqlExecutor = sqlExecutor;
        this.objectMapper = objectMapper;
    }

    @Override
    public CheckEntity insert(CheckEntity check) {
        return sqlExecutor.insert(SQL_INSERT, check, this::convertToParams, this::convertToEntity);
    }

    @Override
    public Optional<CheckEntity> select(Long id) {
        return sqlExecutor.select(SQL_SELECT, id, this::convertToEntity);
    }

    @Override
    public List<CheckEntity> selectAll() {
        return sqlExecutor.selectAll(SQL_SELECT_ALL, this::convertToEntity);
    }

    @Override
    public CheckEntity update(CheckEntity check) {
        return sqlExecutor.update(SQL_UPDATE, check, this::convertToParams);
    }

    @Override
    public void delete(Long id) {
        sqlExecutor.delete(SQL_DELETE, id);
    }

    private CheckEntity convertToEntity(ResultSet resultSet) {
        try {
            return new CheckEntity(
                    resultSet.getLong(CHECK_ID_FIELD),
                    resultSet.getString(CHECK_MARKET_NAME_FIELD),
                    resultSet.getString(CHECK_CASHIER_NAME_FIELD),
                    resultSet.getDate(CHECK_DATE_FIELD).toLocalDate(),
                    resultSet.getTime(CHECK_TIME_FIELD).toLocalTime(),
                    convertToProductEntities(resultSet.getString(CHECK_PRODUCTS_FIELD)),
                    new DiscountCardEntity(resultSet.getLong(DISCOUNT_CARD_ID_FIELD), resultSet.getDouble(DISCOUNT_CARD_DISCOUNT_FIELD)),
                    resultSet.getDouble(CHECK_TOTAL_PRICE_FIELD)
            );
        } catch (SQLException e) {
            LOGGER.error("Converting result set to check failed, message: {}", e.getMessage());
            LOGGER.debug("Converting result set to check failed", e);
            throw new JdbcRepositoryException("Converting result set to check failed, e: {0}", e);
        }
    }

    private CheckEntity convertToEntity(ResultSet resultSet, CheckEntity check) {
        try {
            return new CheckEntity(
                    resultSet.getLong(GENERATED_KEY_COLUMN_NUMBER),
                    check.getMarketName(),
                    check.getCashierName(),
                    check.getDate(),
                    check.getTime(),
                    check.getProducts(),
                    check.getDiscountCard(),
                    check.getTotalPrice()
            );
        } catch (SQLException e) {
            LOGGER.error("Converting result set to check after insert failed, message: {}", e.getMessage());
            LOGGER.debug("Converting result set to check after insert failed", e);
            throw new JdbcRepositoryException("Converting result set to check after insert failed, e: {0}", e);
        }
    }

    private List<Object> convertToParams(CheckEntity check) {
        LOGGER.trace("Converting check to params starts, check: {}", check);
        final List<Object> params = new ArrayList<>();
        params.add(check.getMarketName());
        params.add(check.getCashierName());
        params.add(check.getDate());
        params.add(check.getTime());
        params.add(convertProductEntitiesToJson(check.getProducts()));
        params.add(check.getDiscountCard().getId());
        params.add(check.getTotalPrice());

        if (check.getId() != null) {
            params.add(check.getId());
        }

        LOGGER.trace("Converted check to params returning, {}", params);
        return params;
    }

    private List<ProductEntity> convertToProductEntities(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            LOGGER.error("Converting json to product entities failed, message: {}", e.getMessage());
            LOGGER.debug("Converting json to product entities failed", e);
            throw new JdbcRepositoryException("Converting json to product entities failed, e: {0}", e);
        }
    }

    private String convertProductEntitiesToJson(List<ProductEntity> products) {
        try{
            return objectMapper.writeValueAsString(products);
        } catch (JsonProcessingException e) {
            LOGGER.error("Converting product entities to json failed, message: {}", e.getMessage());
            LOGGER.debug("Converting product entities to json failed", e);
            throw new JdbcRepositoryException("Converting product entities to json failed, e: {0}", e);
        }
    }
}
