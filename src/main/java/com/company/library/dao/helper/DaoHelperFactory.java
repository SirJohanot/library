package com.company.library.dao.helper;

import com.company.library.connection.ConnectionPool;
import com.company.library.exception.DaoException;

import java.sql.SQLException;

public class DaoHelperFactory {

    public DaoHelper createHelper() throws DaoException {
        try {
            return new DaoHelper(ConnectionPool.getInstance());
        } catch (SQLException | InterruptedException e) {
            throw new DaoException(e);
        }
    }
}
