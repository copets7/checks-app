package com.yarosh.library.repository.jdbc;

import com.yarosh.library.repository.api.CrudRepository;
import com.yarosh.library.repository.api.ProductsColumnConverter;
import com.yarosh.library.repository.api.entity.CheckEntity;
import com.yarosh.library.repository.api.entity.DiscountCardEntity;
import com.yarosh.library.repository.api.pagination.RepositoryPage;
import com.yarosh.library.repository.api.pagination.RepositoryPageRequest;
import com.yarosh.library.repository.executor.DefaultSqlExecutor;
import com.yarosh.library.repository.executor.SqlExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.yarosh.library.repository.executor.SqlExecutor.GENERATED_KEY_COLUMN_NUMBER;

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
    private static final String SQL_SELECT_ALL = """ 
            SELECT c.id, c.market_name, c.cashier_name, c.date, c.time, c.products, c.discount_card_id, c.total_price,d.id, d.discount
            FROM checks AS c JOIN discount_cards AS d ON c.discount_card_id = d.id ORDER BY c.id DESC
            """;
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
    private static final String DISCOUNT_CARDS_ID_FIELD = "d.id";
    private static final String DISCOUNT_CARDS_DISCOUNT_FIELD = "d.discount";

    private final SqlExecutor<CheckEntity, Long> sqlExecutor;
    private final ProductsColumnConverter productsColumnConverter;

    @Inject
    public JdbcCheckRepository(final DataSource dataSource, final ProductsColumnConverter productsColumnConverter) {
        this.sqlExecutor = new DefaultSqlExecutor<>(dataSource);
        this.productsColumnConverter = productsColumnConverter;
    }

    @Inject
    public JdbcCheckRepository(final SqlExecutor<CheckEntity, Long> sqlExecutor, final ProductsColumnConverter productsColumnConverter) {
        this.sqlExecutor = sqlExecutor;
        this.productsColumnConverter = productsColumnConverter;
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
    public RepositoryPage<CheckEntity> selectAll(RepositoryPageRequest request) {
        return null;
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
                    productsColumnConverter.convertToEntityAttribute(resultSet.getString(CHECK_PRODUCTS_FIELD)),
                    new DiscountCardEntity(resultSet.getLong(DISCOUNT_CARDS_ID_FIELD), resultSet.getDouble(DISCOUNT_CARDS_DISCOUNT_FIELD)),
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
        params.add(productsColumnConverter.convertToDatabaseColumn(check.getProducts()));
        params.add(check.getDiscountCard().getId());
        params.add(check.getTotalPrice());

        if (check.getId() != null) {
            params.add(check.getId());
        }

        LOGGER.trace("Converted check to params returning, {}", params);
        return params;
    }
}
