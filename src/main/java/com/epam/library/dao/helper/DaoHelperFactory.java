package com.epam.library.dao.helper;

import com.epam.library.connection.ConnectionPool;
import com.epam.library.exception.DaoException;

import java.sql.SQLException;

public class DaoHelperFactory {

    public DaoHelper createHelper() throws DaoException {
        try {
            return new DaoHelper(ConnectionPool.getInstance());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
