package com.epam.library.dao;

import com.epam.library.entity.book.Author;
import com.epam.library.entity.book.Book;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.AuthorRowMapper;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorDaoImpl extends AbstractDao<Author> implements AuthorDao {

    private static final String RELATION_TABLE_NAME = "book_author";
    private static final String RELATION_TABLE_AUTHOR_ID_COLUMN = "author_id";
    private static final String RELATION_TABLE_BOOK_ID_COLUMN = "book_id";

    private static final String GET_AUTHORS_ASSOCIATED_WITH_BOOK_ID_QUERY = "SELECT * FROM %s b JOIN %s ba ON ba.%s=b.%s JOIN %s a ON a.%s=ba.%s WHERE b.%s = ? ;";

    public AuthorDaoImpl(Connection connection) {
        super(connection, new AuthorRowMapper(), Author.TABLE_NAME);
    }

    @Override
    public List<Author> getAuthorsAssociatedWithBookId(Long bookId) throws DaoException {
        String query = String.format(GET_AUTHORS_ASSOCIATED_WITH_BOOK_ID_QUERY,
                Book.TABLE_NAME,
                RELATION_TABLE_NAME,
                RELATION_TABLE_BOOK_ID_COLUMN,
                Book.ID_COLUMN,
                Author.TABLE_NAME,
                Author.ID_COLUMN,
                RELATION_TABLE_AUTHOR_ID_COLUMN,
                Book.ID_COLUMN);
        return executeQuery(query, bookId);
    }

    @Override
    protected Map<String, Object> getMapOfColumnValues(Author entity) {
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(Author.ID_COLUMN, entity.getId());
        valuesMap.put(Author.NAME_COLUMN, entity.getName());
        return valuesMap;
    }
}
