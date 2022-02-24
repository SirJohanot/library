package com.epam.library.dao;

import com.epam.library.entity.book.Book;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.BookRowMapper;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDaoImpl extends AbstractDao<Book> implements BookDao {

    private static final String GET_ALL_NOT_DELETED_QUERY = "SELECT * FROM ? WHERE id_deleted=false";

    public BookDaoImpl(Connection connection) {
        super(connection, new BookRowMapper(), Book.TABLE_NAME);
    }

    @Override
    public List<Book> getAllNotDeleted() throws DaoException {
        return executeQuery(GET_ALL_NOT_DELETED_QUERY, Book.TABLE_NAME);
    }

    @Override
    protected Map<String, Object> getMapOfColumnValues(Book entity) {
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(Book.ID_COLUMN, entity.getId());
        valuesMap.put(Book.TITLE_COLUMN, entity.getTitle());
        valuesMap.put(Book.GENRE_ID_COLUMN, entity.getGenre().getId());
        valuesMap.put(Book.PUBLISHER_ID_COLUMN, entity.getPublisher().getId());
        valuesMap.put(Book.PUBLISHMENT_YEAR_COLUMN, entity.getPublishmentYear());
        valuesMap.put(Book.AMOUNT_COLUMN, entity.getAmount());
        return valuesMap;
    }
}
