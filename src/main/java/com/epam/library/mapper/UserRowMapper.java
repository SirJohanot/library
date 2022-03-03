package com.epam.library.mapper;

import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(User.ID_COLUMN);
        String login = resultSet.getString(User.LOGIN_COLUMN);
        String name = resultSet.getString(User.NAME_COLUMN);
        String surname = resultSet.getString(User.SURNAME_COLUMN);

        String roleLowercaseLine = resultSet.getString(User.ROLE_COLUMN);
        String roleUppercaseLine = roleLowercaseLine.toUpperCase();
        UserRole role = UserRole.valueOf(roleUppercaseLine);

        Boolean isBlocked = resultSet.getBoolean(User.BLOCKED_COLUMN);
        
        return new User(id, login, name, surname, role, isBlocked);
    }
}
