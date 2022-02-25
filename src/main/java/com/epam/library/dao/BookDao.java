package com.epam.library.dao;

import com.epam.library.entity.book.Book;
import com.epam.library.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    List<Book> getAllNotDeleted() throws DaoException;

    Optional<Book> getNotDeletedBookById(Long id) throws DaoException;

    void saveBook(Book b) throws DaoException;
}
