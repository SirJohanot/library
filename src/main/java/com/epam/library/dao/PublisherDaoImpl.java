package com.epam.library.dao;

import com.epam.library.entity.book.Publisher;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.PublisherRowMapper;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Optional;

public class PublisherDaoImpl extends AbstractDao<Publisher> implements PublisherDao {

    public PublisherDaoImpl(Connection connection) {
        super(connection, new PublisherRowMapper(), Publisher.TABLE_NAME);
    }

    @Override
    protected LinkedHashMap<String, Object> getMapOfColumnValues(Publisher entity) {
        LinkedHashMap<String, Object> valuesMap = new LinkedHashMap<>();
        valuesMap.put(Publisher.ID_COLUMN, entity.getId());
        valuesMap.put(Publisher.NAME_COLUMN, entity.getName());
        return valuesMap;
    }

    @Override
    public Optional<Publisher> getPublisher(Long publisherId) throws DaoException {
        return getById(publisherId);
    }

    @Override
    public Optional<Publisher> getPublisherByName(String name) throws DaoException {
        return findIdentical(Publisher.ofName(name));
    }

    @Override
    public void savePublisher(Publisher publisher) throws DaoException {
        save(publisher);
    }
}
