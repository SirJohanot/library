package com.patiun.library.dao.helper;

import com.patiun.library.connection.ConnectionPool;
import com.patiun.library.connection.ProxyConnection;
import com.patiun.library.dao.BookOrderDao;
import com.patiun.library.dao.BookOrderDaoImpl;
import com.patiun.library.dao.UserDao;
import com.patiun.library.dao.UserDaoImpl;
import com.company.library.dao.book.*;
import com.patiun.library.exception.DaoException;
import com.patiun.library.dao.book.*;

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
