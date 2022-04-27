package com.company.library.mapper;

import com.company.library.entity.book.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Genre.ID_COLUMN);

        String name = resultSet.getString(Genre.NAME_COLUMN);

        return new Genre(id, name);
    }
}
