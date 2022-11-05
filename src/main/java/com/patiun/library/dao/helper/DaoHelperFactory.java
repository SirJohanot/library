package com.patiun.library.dao.helper;

import com.patiun.library.connection.ConnectionPool;
import com.patiun.library.exception.DaoException;

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
