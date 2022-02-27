package com.epam.library.dao;

import com.epam.library.entity.book.Book;
import com.epam.library.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BookDao extends Dao<Book> {

    Optional<Book> findIdenticalBook(Book book) throws DaoException;

    List<Book> getAllNotDeleted() throws DaoException;

}
