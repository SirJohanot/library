package com.patiun.library.mapper;

import com.patiun.library.entity.book.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorRowMapper implements RowMapper<Author> {

    @Override
    public Author map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Author.ID_COLUMN);

        String name = resultSet.getString(Author.NAME_COLUMN);

        return new Author(id, name);
    }
}
