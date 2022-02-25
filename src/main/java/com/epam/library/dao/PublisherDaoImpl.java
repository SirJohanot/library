package com.epam.library.dao;

import com.epam.library.entity.book.Book;
import com.epam.library.entity.book.Publisher;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.PublisherRowMapper;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PublisherDaoImpl extends AbstractDao<Publisher> implements PublisherDao {

    public PublisherDaoImpl(Connection connection) {
        super(connection, new PublisherRowMapper(), Publisher.TABLE_NAME);
    }

    @Override
    protected Map<String, Object> getMapOfColumnValues(Publisher entity) {
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(Publisher.ID_COLUMN, entity.getId());
        valuesMap.put(Publisher.NAME_COLUMN, entity.getName());
        return valuesMap;
    }

    @Override
    public Optional<Publisher> getPublisherOfBook(Book book) throws DaoException {
        return getById(book.getPublisher().getId());
    }
}
