package com.epam.library.dao;

import com.epam.library.entity.book.Book;
import com.epam.library.entity.book.Genre;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.GenreRowMapper;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GenreDaoImpl extends AbstractDao<Genre> implements GenreDao {


    public GenreDaoImpl(Connection connection) {
        super(connection, new GenreRowMapper(), Genre.TABLE_NAME);
    }

    @Override
    protected Map<String, Object> getMapOfColumnValues(Genre entity) {
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(Genre.ID_COLUMN, entity.getId());
        valuesMap.put(Genre.NAME_COLUMN, entity.getName());
        return valuesMap;
    }

    @Override
    public Optional<Genre> getGenreOfBook(Book book) throws DaoException {
        return getById(book.getGenre().getId());
    }
}
