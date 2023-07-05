package com.yarosh.library.user.repository;

import com.yarosh.library.repository.executor.DefaultSqlExecutor;
import com.yarosh.library.repository.executor.SqlExecutor;
import com.yarosh.library.user.repository.entity.RoleEntity;
import com.yarosh.library.user.repository.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class UserRepositoryImpl implements UserRepository<UserEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private static final String SQL_SELECT_BY_USERNAME = """
            SELECT u.id, u.username, u.firstname, u.lastname, u.password, r.id, r.name FROM users as u
            JOIN roles as r on JSON_CONTAINS(roles, CAST(r.id as JSON))
            WHERE u.username = ?
            """;

    private static final String USER_ID_FIELD = "u.id";
    private static final String USERNAME_FIELD = "u.username";
    private static final String FIRSTNAME_FIELD = "u.firstname";
    private static final String LASTNAME_FIELD = "u.lastname";
    private static final String PASSWORD_FIELD = "u.password";
    private static final String ROLE_ID_FIELD = "r.id";
    private static final String ROLE_NAME_FIELD = "r.name";

    private final SqlExecutor<UserEntity, Long> sqlExecutor;

    @Inject
    public UserRepositoryImpl(SqlExecutor<UserEntity, Long> sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    @Inject
    public UserRepositoryImpl(DataSource dataSource) {
        this.sqlExecutor = new DefaultSqlExecutor<>(dataSource);
    }

    @Override
    public Optional<UserEntity> findByName(String name) {
        return sqlExecutor.execute(
                SQL_SELECT_BY_USERNAME,
                List.of(name),
                preparedStatement -> performFunctionForFindByName().apply(preparedStatement)
        );
    }

    private Function<PreparedStatement, UserEntity> performFunctionForFindByName() {
        return preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                LOGGER.trace("SQL result set converting starts, metadata: {}", resultSet.getMetaData());

                UserEntity user = null;
                while (resultSet.next()) {

                    if (user == null) {
                        user = new UserEntity(
                                resultSet.getLong(USER_ID_FIELD),
                                resultSet.getString(USERNAME_FIELD),
                                resultSet.getString(FIRSTNAME_FIELD),
                                resultSet.getString(LASTNAME_FIELD),
                                resultSet.getString(PASSWORD_FIELD),
                                new ArrayList<>()
                        );
                    }

                    user.getRoles().add(new RoleEntity(resultSet.getLong(ROLE_ID_FIELD), resultSet.getString(ROLE_NAME_FIELD)));
                }

                return user;
            } catch (SQLException e) {
                LOGGER.error("Performing result set for SQL select failed, message: {}", e.getMessage());
                LOGGER.debug("Performing result set for SQL select failed", e);
                throw new UserRepositoryException("Performing result set for SQL select failed, e: {0}", e);
            }
        };
    }
}
