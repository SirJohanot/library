package com.epam.library.dao;

import com.epam.library.entity.book.Book;
import com.epam.library.entity.book.Publisher;
import com.epam.library.exception.DaoException;

import java.util.Optional;

public interface PublisherDao {

    Optional<Publisher> getPublisherOfBook(Book book) throws DaoException;
}
