package com.epam.library.dao.book;

import com.epam.library.dao.AbstractDao;
import com.epam.library.entity.book.Genre;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.GenreRowMapper;

import java.sql.Connection;
import java.util.LinkedHashMap;

public class GenreDaoImpl extends AbstractDao<Genre> implements GenreDao {

    private static final String DELETE_UNREFERENCED_GENRE_ROW_QUERY = "DELETE FROM %s g WHERE NOT EXISTS (SELECT 1 FROM %s b WHERE g.%s = b.%s );";

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
    public void deleteUnreferenced(String primaryTableName, String primaryTableColumnName) throws DaoException {
        String deleteGenreRowQuery = String.format(DELETE_UNREFERENCED_GENRE_ROW_QUERY, Genre.TABLE_NAME, primaryTableName, Genre.ID_COLUMN, primaryTableColumnName);
        executeUpdate(deleteGenreRowQuery);
    }

}
