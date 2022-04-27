package com.company.library.dao.book;

import com.company.library.dao.AbstractDao;
import com.company.library.exception.DaoException;
import com.company.library.mapper.GenreRowMapper;
import com.company.library.entity.book.Genre;

import java.sql.Connection;
import java.util.LinkedHashMap;

public class GenreDaoImpl extends AbstractDao<Genre> implements GenreDao {

    private static final String DELETE_UNREFERENCED_GENRE_ROWS_QUERY = "DELETE FROM genre g WHERE NOT EXISTS (SELECT 1 FROM book b WHERE g.id = b.genre_id );";

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
    public void deleteRedundant() throws DaoException {
        executeUpdate(DELETE_UNREFERENCED_GENRE_ROWS_QUERY);
    }

}
