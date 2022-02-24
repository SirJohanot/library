package com.epam.library.dao;

import com.epam.library.entity.book.Book;
import com.epam.library.exception.DaoException;

import java.util.List;

public interface BookDao {

    List<Book> getAllNotDeleted() throws DaoException;
}
