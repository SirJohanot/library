package com.epam.library.dao.book;

import com.epam.library.dao.AbstractDao;
import com.epam.library.entity.book.Publisher;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.PublisherRowMapper;

import java.sql.Connection;
import java.util.LinkedHashMap;

public class PublisherDaoImpl extends AbstractDao<Publisher> implements PublisherDao {

    private static final String DELETE_UNREFERENCED_PUBLISHER_ROWS_QUERY = "DELETE FROM publisher p WHERE NOT EXISTS (SELECT 1 FROM book b WHERE p.id = b.publisher_id );";

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
    public void deleteRedundant() throws DaoException {
        executeUpdate(DELETE_UNREFERENCED_PUBLISHER_ROWS_QUERY);
    }

}
