package com.epam.library.mapper;

import com.epam.library.entity.book.Publisher;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublisherRowMapper implements RowMapper<Publisher> {
    @Override
    public Publisher map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Publisher.ID_COLUMN);

        String name = resultSet.getString(Publisher.NAME_COLUMN);

        return new Publisher(id, name);
    }
}
