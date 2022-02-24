package com.epam.library.dao.daohelper;

import com.epam.library.connection.ConnectionPool;
import com.epam.library.connection.ProxyConnection;
import com.epam.library.dao.BookDao;
import com.epam.library.dao.BookDaoImpl;
import com.epam.library.dao.UserDao;
import com.epam.library.dao.UserDaoImpl;
import com.epam.library.exception.DaoException;

import java.sql.SQLException;

public class DaoHelper implements AutoCloseable {

    private final ProxyConnection connection;

    public DaoHelper(ConnectionPool pool) {
        this.connection = pool.getConnection();
    }

    public UserDao createUserDao() {
        return new UserDaoImpl(connection);
    }

    public BookDao createBookDao() {
        return new BookDaoImpl(connection);
    }

    @Override
    public void close() throws DaoException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void endTransaction() throws DaoException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
