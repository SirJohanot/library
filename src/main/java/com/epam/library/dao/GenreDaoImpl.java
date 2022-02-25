package com.epam.library.dao;

import com.epam.library.entity.book.Genre;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.GenreRowMapper;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Optional;

public class GenreDaoImpl extends AbstractDao<Genre> implements GenreDao {


    public GenreDaoImpl(Connection connection) {
        super(connection, new GenreRowMapper(), Genre.TABLE_NAME);
    }

    @Override
    protected LinkedHashMap<String, Object> getMapOfColumnValues(Genre entity) {
        LinkedHashMap<String, Object> valuesMap = new LinkedHashMap<>();
        valuesMap.put(Genre.ID_COLUMN, entity.getId());
        valuesMap.put(Genre.NAME_COLUMN, entity.getName());
        return valuesMap;
    }

    @Override
    public Optional<Genre> getGenre(Long genreId) throws DaoException {
        return getById(genreId);
    }

    @Override
    public Optional<Genre> getGenreByName(String name) throws DaoException {
        return findIdentical(Genre.ofName(name));
    }

    @Override
    public void saveGenre(Genre genre) throws DaoException {
        save(genre);
    }
}
