package com.epam.library.dao;

import com.epam.library.entity.book.Publisher;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.PublisherRowMapper;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Optional;

public class PublisherDaoImpl extends AbstractDao<Publisher> implements PublisherDao {

    private static final String DELETE_UNREFERENCED_PUBLISHER_ROW_QUERY = "DELETE FROM %s p WHERE NOT EXISTS (SELECT 1 FROM %s b WHERE p.%s = b.%s );";

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
    public Optional<Publisher> getByName(String name) throws DaoException {
        return findIdentical(Publisher.ofName(name));
    }

    @Override
    public void deleteUnreferenced(String primaryTableName, String primaryTableColumnName) throws DaoException {
        String deleteGenreRowQuery = String.format(DELETE_UNREFERENCED_PUBLISHER_ROW_QUERY, Publisher.TABLE_NAME, primaryTableName, Publisher.ID_COLUMN, primaryTableColumnName);
        executeUpdate(deleteGenreRowQuery);
    }
}
