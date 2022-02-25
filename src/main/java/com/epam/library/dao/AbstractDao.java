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

    private static final String GET_BY_ID_QUERY = "SELECT * FROM %s WHERE id = ? ;";
    private static final String GET_ALL_QUERY = "SELECT * FROM %s ;";
    private static final String REMOVE_BY_ID_QUERY = "UPDATE %s SET is_deleted = true WHERE id = ? ;";
    private static final String UPDATE_QUERY_BEGINNING = "UPDATE %s SET";
    private static final String INSERT_QUERY_BEGINNING = "INSERT INTO %s SET";
    private static final String GET_ALL_WHERE_QUERY_BEGINNING = "SELECT * FROM %s WHERE";

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
            return extractResultsFromResultSet(resultSet);
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

    public List<T> findIdentical(T item) throws DaoException {
        Map<String, Object> valuesMap = getMapOfColumnValues(item);
        String getAllWhereQueryWithTableName = String.format(GET_ALL_WHERE_QUERY_BEGINNING, tableName);
        String query = addNonIdValuesToQuery(valuesMap, getAllWhereQueryWithTableName);
        try (PreparedStatement preparedStatement = generatePreparedStatementFromValuesMap(query, valuesMap)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractResultsFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<T> getById(Long id) throws DaoException {
        String query = String.format(GET_BY_ID_QUERY, tableName);
        return executeForSingleResult(query, id);
    }

    @Override
    public List<T> getAll() throws DaoException {
        String query = String.format(GET_ALL_QUERY, tableName);
        return executeQuery(query);
    }

    @Override
    public void save(T item) throws DaoException {
        Map<String, Object> valuesMap = getMapOfColumnValues(item);
        String query = item.getId() == null ? addNonIdValuesToQuery(valuesMap, INSERT_QUERY_BEGINNING) : buildUpdateQuery(valuesMap);
        try (PreparedStatement preparedStatement = generatePreparedStatementFromValuesMap(query, valuesMap)) {
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void removeById(Long id) throws DaoException {
        String query = String.format(REMOVE_BY_ID_QUERY, tableName);
        executeQuery(query, id);
    }

    private String buildUpdateQuery(Map<String, Object> valuesMap) {
        String updateQueryBeginningWithTableName = String.format(UPDATE_QUERY_BEGINNING, tableName);
        StringBuilder stringBuilder = new StringBuilder(updateQueryBeginningWithTableName);
        for (String key : valuesMap.keySet()) {
            if (!key.equals("id")) {
                stringBuilder.append(" ? = ?,");
            }
        }
        stringBuilder.append(" WHERE id = ? ;");
        return stringBuilder.toString();
    }

    private String addNonIdValuesToQuery(Map<String, Object> valuesMap, String query) {
        StringBuilder stringBuilder = new StringBuilder(query);
        for (String key : valuesMap.keySet()) {
            if (!key.equals("id")) {
                stringBuilder.append(" ? = ?,");
            }
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append(" ;");
        return stringBuilder.toString();
    }

    private PreparedStatement generatePreparedStatementFromValuesMap(String query, Map<String, Object> valuesMap) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        int preparedStatementIndex = 1;
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

    private List<T> extractResultsFromResultSet(ResultSet resultSet) throws SQLException {
        List<T> entities = new ArrayList<>();
        while (resultSet.next()) {
            T entity = rowMapper.map(resultSet);
            entities.add(entity);
        }
        return entities;
    }

    protected abstract Map<String, Object> getMapOfColumnValues(T entity);
}
