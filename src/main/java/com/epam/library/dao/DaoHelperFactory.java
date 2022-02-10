package com.epam.library.dao;

import com.epam.library.connection.ConnectionPool;
import com.epam.library.exception.DaoException;

import java.io.IOException;
import java.sql.SQLException;

public class DaoHelperFactory {

    public DaoHelper createHelper() throws DaoException {
        try {
            return new DaoHelper(ConnectionPool.getInstance());
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DaoException(e);
        }
    }
}
