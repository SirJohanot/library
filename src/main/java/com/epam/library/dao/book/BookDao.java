package com.epam.library.dao.book;

import com.epam.library.dao.Dao;
import com.epam.library.entity.book.Book;
import com.epam.library.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BookDao extends Dao<Book> {

    Optional<Book> findIdenticalBook(Book book) throws DaoException;

    void tweakAmount(Long bookId, int value) throws DaoException;

    List<Book> getAllNotDeleted() throws DaoException;

}
