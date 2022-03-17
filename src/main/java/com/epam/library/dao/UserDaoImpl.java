package com.epam.library.dao;

import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.UserRole;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.UserRowMapper;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private static final String FIND_BY_LOGIN_AND_PASSWORD_QUERY = "SELECT * FROM user WHERE login = ? AND password = MD5(?) ;";
    private static final String FIND_BY_LOGIN_QUERY = "SELECT * FROM user WHERE login = ? ;";
    private static final String UPDATE_USER_BLOCKED_STATE_QUERY = "UPDATE user SET is_blocked = ? WHERE id = ? ;";
    private static final String SIGN_UP_QUERY = "INSERT INTO user SET login = ?, password = MD5(?), name = ?, surname = ?, role = ? ;";

    public UserDaoImpl(Connection connection) {
        super(connection, new UserRowMapper(), User.TABLE_NAME);
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException {
        return executeForSingleResult(
                FIND_BY_LOGIN_AND_PASSWORD_QUERY,
                login,
                password);
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        return executeForSingleResult(FIND_BY_LOGIN_QUERY, login);
    }

    @Override
    public void saveWithPassword(User user, String password) throws DaoException {
        String login = user.getLogin();
        String name = user.getName();
        String surname = user.getSurname();
        UserRole role = user.getRole();
        String roleLine = role.toString().toLowerCase();
        executeUpdate(SIGN_UP_QUERY, login, password, name, surname, roleLine);
    }

    @Override
    public void updateUserBlocked(Long id, boolean newValue) throws DaoException {
        executeUpdate(UPDATE_USER_BLOCKED_STATE_QUERY, newValue, id);
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
