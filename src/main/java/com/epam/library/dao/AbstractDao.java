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
import java.util.Map;
import java.util.Optional;

public abstract class AbstractDao<T extends Identifiable> implements Dao<T> {

    private static final String GET_BY_ID_QUERY = "SELECT * FROM ? WHERE id = ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM ?";
    private static final String REMOVE_BY_ID_QUERY = "UPDATE ? SET is_deleted = true WHERE id = ?";
    private static final String UPDATE_QUERY_BEGINNING = "UPDATE ? SET";
    private static final String INSERT_QUERY_BEGINNING = "INSERT INTO ? SET";

    private final Connection connection;
    private final RowMapper<T> rowMapper;
    private final String tableName;

    public AbstractDao(Connection connection, RowMapper<T> rowMapper, String tableName) {
        this.connection = connection;
        this.rowMapper = rowMapper;
        this.tableName = tableName;
    }

    protected List<T> executeQuery(String query, Object... parameters) throws DaoException {
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

    protected Optional<T> executeForSingleResult(String query, Object... parameters) throws DaoException {
        List<T> results = executeQuery(query, parameters);
        if (results.size() == 1) {
            return Optional.of(results.get(0));
        } else if (results.size() > 1) {
            throw new IllegalArgumentException("More than one result found");
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<T> getById(Long id) throws DaoException {
        return executeForSingleResult(GET_BY_ID_QUERY, tableName, id);
    }

    @Override
    public List<T> getAll() throws DaoException {
        return executeQuery(GET_ALL_QUERY, tableName);
    }

    @Override
    public void save(T item) throws DaoException {
        Map<String, Object> valuesMap = getMapOfColumnValues(item);
        String query = item.getId() == null ? buildInsertQuery(valuesMap) : buildUpdateQuery(valuesMap);
        try (PreparedStatement preparedStatement = generateSavePreparedStatement(query, valuesMap)) {
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void removeById(Long id) throws DaoException {
        executeQuery(REMOVE_BY_ID_QUERY, tableName, id);
    }

    private String buildUpdateQuery(Map<String, Object> valuesMap) {
        StringBuilder stringBuilder = new StringBuilder(UPDATE_QUERY_BEGINNING);
        for (String key : valuesMap.keySet()) {
            if (!key.equals("id")) {
                stringBuilder.append(" ? = ?,");
            }
        }
        stringBuilder.append(" WHERE id = ?");
        return stringBuilder.toString();
    }

    private String buildInsertQuery(Map<String, Object> valuesMap) {
        StringBuilder stringBuilder = new StringBuilder(INSERT_QUERY_BEGINNING);
        for (String key : valuesMap.keySet()) {
            if (!key.equals("id")) {
                stringBuilder.append(" ? = ?,");
            }
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private PreparedStatement generateSavePreparedStatement(String query, Map<String, Object> valuesMap) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        int preparedStatementIndex = 1;
        preparedStatement.setObject(preparedStatementIndex++, tableName);
        for (String key : valuesMap.keySet()) {
            if (!key.equals("id")) {
                preparedStatement.setObject(preparedStatementIndex++, key);
                preparedStatement.setObject(preparedStatementIndex++, valuesMap.get(key));
            }
        }
        if (valuesMap.get("id") != null) {
            preparedStatement.setObject(preparedStatementIndex, valuesMap.get("id"));
        }
        return preparedStatement;
    }

    protected abstract Map<String, Object> getMapOfColumnValues(T entity);
}
