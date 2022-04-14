package com.epam.library.dao.helper;

import com.epam.library.connection.ConnectionPool;
import com.epam.library.connection.ProxyConnection;
import com.epam.library.dao.BookOrderDao;
import com.epam.library.dao.BookOrderDaoImpl;
import com.epam.library.dao.UserDao;
import com.epam.library.dao.UserDaoImpl;
import com.epam.library.dao.book.*;
import com.epam.library.exception.DaoException;

import java.sql.SQLException;

public class DaoHelper implements AutoCloseable {

    private final ProxyConnection connection;

    public DaoHelper(ConnectionPool pool) throws InterruptedException {
        this.connection = pool.getConnection();
    }

    public UserDao createUserDao() {
        return new UserDaoImpl(connection);
    }

    public AuthorDao createAuthorDao() {
        return new AuthorDaoImpl(connection);
    }

    public BookDao createBookDao() {
        return new BookDaoImpl(connection);
    }

    public GenreDao createGenreDao() {
        return new GenreDaoImpl(connection);
    }

    public PublisherDao createPublisherDao() {
        return new PublisherDaoImpl(connection);
    }

    public BookOrderDao createBookOrderDao() {
        return new BookOrderDaoImpl(connection);
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
