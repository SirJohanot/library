package com.epam.library.dao;

import com.epam.library.entity.Identifiable;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Identifiable> implements Dao<T> {

    private final Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    protected List<T> executeQuery(String query, RowMapper<T> rowMapper, Object... parameters) throws DaoException {
        try (PreparedStatement preparedStatement = buildPreparedStatement(query, parameters)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                T entity = rowMapper.map(resultSet);
                entities.add(entity);
            }
            return entities;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private PreparedStatement buildPreparedStatement(String query, Object... parameters) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 1; i <= parameters.length; i++) {
            preparedStatement.setObject(i, parameters[i - 1]);
        }
        return preparedStatement;
    }

    protected Optional<T> executeForSingleResult(String query, RowMapper<T> rowMapper, Object... parameters) throws DaoException {
        List<T> results = executeQuery(query, rowMapper, parameters);
        if (results.size() == 1) {
            return Optional.of(results.get(0));
        } else if (results.size() > 1) {
            throw new IllegalArgumentException("More than one result found");
        } else {
            return Optional.empty();
        }
    }

    protected abstract String getTableName();
}
