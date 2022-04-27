package com.company.library.dao.helper;

import com.company.library.connection.ConnectionPool;
import com.company.library.connection.ProxyConnection;
import com.company.library.dao.BookOrderDao;
import com.company.library.dao.BookOrderDaoImpl;
import com.company.library.dao.UserDao;
import com.company.library.dao.UserDaoImpl;
import com.company.library.dao.book.*;
import com.company.library.exception.DaoException;

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
