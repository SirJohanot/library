package com.epam.library.dao;

import com.epam.library.entity.User;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.UserRowMapper;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private static final String FIND_BY_LOGIN_AND_PASSWORD = "SELECT * FROM %s WHERE %s = ? AND password = MD5(?) ;";

    public UserDaoImpl(Connection connection) {
        super(connection, new UserRowMapper(), User.TABLE_NAME);
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException {
        String query = String.format(FIND_BY_LOGIN_AND_PASSWORD, User.TABLE_NAME, User.LOGIN_COLUMN);
        return executeForSingleResult(
                query,
                login,
                password);
    }

    @Override
    protected LinkedHashMap<String, Object> getMapOfColumnValues(User entity) {
        LinkedHashMap<String, Object> valuesMap = new LinkedHashMap<>();
        valuesMap.put(User.ID_COLUMN, entity.getId());
        valuesMap.put(User.NAME_COLUMN, entity.getName());
        valuesMap.put(User.SURNAME_COLUMN, entity.getSurname());
        valuesMap.put(User.ROLE_COLUMN, entity.getRole().toString().toLowerCase());
        valuesMap.put(User.BLOCKED_COLUMN, entity.isBlocked());
        return valuesMap;
    }
}
