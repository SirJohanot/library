package com.epam.library.dao;

import com.epam.library.entity.book.Author;
import com.epam.library.entity.book.Book;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.RowMapper;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorDaoImpl extends AbstractDao<Author> implements AuthorDao {

    private static final String RELATION_TABLE_NAME = "book_author";
    private static final String RELATION_TABLE_AUTHOR_ID_COLUMN = "author_id";
    private static final String RELATION_TABLE_BOOK_ID_COLUMN = "book_id";

    private static final String GET_AUTHORS_ASSOCIATED_WITH_BOOK_ID_QUERY = "SELECT * FROM ? a JOIN ? ba ON ba.?=a.? JOIN ? b ON b.?=ba.?";

    public AuthorDaoImpl(Connection connection, RowMapper<Author> rowMapper, String tableName) {
        super(connection, rowMapper, tableName);
    }

    @Override
    public List<Author> getAuthorsAssociatedWithBookId(Long bookId) throws DaoException {
        return executeQuery(GET_AUTHORS_ASSOCIATED_WITH_BOOK_ID_QUERY,
                Author.TABLE_NAME,
                RELATION_TABLE_NAME,
                RELATION_TABLE_AUTHOR_ID_COLUMN,
                Author.ID_COLUMN,
                Book.TABLE_NAME,
                Book.ID_COLUMN,
                RELATION_TABLE_BOOK_ID_COLUMN);
    }

    @Override
    protected Map<String, Object> getMapOfColumnValues(Author entity) {
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(Author.ID_COLUMN, entity.getId());
        valuesMap.put(Author.NAME_COLUMN, entity.getName());
        return valuesMap;
    }
}
