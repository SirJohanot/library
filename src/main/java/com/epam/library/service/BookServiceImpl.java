package com.epam.library.service;

import com.epam.library.dao.BookDao;
import com.epam.library.dao.daohelper.DaoHelper;
import com.epam.library.dao.daohelper.DaoHelperFactory;
import com.epam.library.entity.book.Book;
import com.epam.library.exception.DaoException;
import com.epam.library.exception.ServiceException;

import java.util.List;

public class BookServiceImpl implements BookService {

    private final DaoHelperFactory daoHelperFactory;

    public BookServiceImpl(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    @Override
    public List<Book> getBooks() throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();
            BookDao dao = helper.createBookDao();
            List<Book> bookList = dao.getAllNotDeleted();
            //TODO: GET ASSOCIATED AUTHORS
            helper.endTransaction();
            return bookList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }
}
