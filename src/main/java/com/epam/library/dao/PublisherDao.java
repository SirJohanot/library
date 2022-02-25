package com.epam.library.dao;

import com.epam.library.entity.book.Publisher;
import com.epam.library.exception.DaoException;

import java.util.Optional;

public interface PublisherDao {

    Optional<Publisher> getPublisher(Long publisherId) throws DaoException;

    Optional<Publisher> getPublisherByName(String name) throws DaoException;

    void savePublisher(Publisher publisher) throws DaoException;
}
