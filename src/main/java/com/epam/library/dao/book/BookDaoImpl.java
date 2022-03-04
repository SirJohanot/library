package com.epam.library.dao.book;

import com.epam.library.dao.AbstractDao;
import com.epam.library.entity.book.Book;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.BookRowMapper;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;

public class BookDaoImpl extends AbstractDao<Book> implements BookDao {

    private static final String UPDATE_IS_DELETED_TRUE_QUERY = "UPDATE %s SET is_deleted = true WHERE id = ? ;";
    private static final String GET_ALL_NOT_DELETED_QUERY = "SELECT * FROM %s WHERE is_deleted = false ; ";
    private static final String TWEAK_AMOUNT_QUERY = "UPDATE %s SET amount = amount + (?) ;";

    public BookDaoImpl(Connection connection) {
        super(connection, new BookRowMapper(), Book.TABLE_NAME);
    }

    @Override
    protected LinkedHashMap<String, Object> getMapOfColumnValues(Book entity) {
        LinkedHashMap<String, Object> valuesMap = new LinkedHashMap<>();
        valuesMap.put(Book.ID_COLUMN, entity.getId());
        valuesMap.put(Book.TITLE_COLUMN, entity.getTitle());
        valuesMap.put(Book.GENRE_ID_COLUMN, entity.getGenre().getId());
        valuesMap.put(Book.PUBLISHER_ID_COLUMN, entity.getPublisher().getId());
        valuesMap.put(Book.PUBLISHMENT_YEAR_COLUMN, entity.getPublishmentYear().getValue());
        valuesMap.put(Book.AMOUNT_COLUMN, entity.getAmount());
        return valuesMap;
    }

    @Override
    public void removeById(Long id) throws DaoException {
        String query = String.format(UPDATE_IS_DELETED_TRUE_QUERY, Book.TABLE_NAME);
        executeUpdate(query, id);
    }

    @Override
    public void tweakAmount(Long bookId, int value) throws DaoException {
        String query = String.format(TWEAK_AMOUNT_QUERY, Book.TABLE_NAME);
        executeUpdate(query, value);
    }

    @Override
    public List<Book> getAllNotDeleted() throws DaoException {
        String query = String.format(GET_ALL_NOT_DELETED_QUERY, Book.TABLE_NAME);
        return executeQuery(query);
    }
}
