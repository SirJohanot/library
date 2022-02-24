package com.epam.library.dao;

import com.epam.library.entity.User;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.UserRowMapper;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private static final String FIND_BY_LOGIN_AND_PASSWORD = "SELECT * FROM user WHERE login = ? AND password = MD5(?);";

    public UserDaoImpl(Connection connection) {
        super(connection, new UserRowMapper(), User.TABLE_NAME);
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException {
        return executeForSingleResult(
                FIND_BY_LOGIN_AND_PASSWORD,
                login,
                password);
    }

    @Override
    protected Map<String, Object> getMapOfColumnValues(User entity) {
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(User.ID_COLUMN, entity.getId());
        valuesMap.put(User.NAME_COLUMN, entity.getName());
        valuesMap.put(User.SURNAME_COLUMN, entity.getSurname());
        valuesMap.put(User.ROLE_COLUMN, entity.getRole().toString().toLowerCase());
        return valuesMap;
    }
}
