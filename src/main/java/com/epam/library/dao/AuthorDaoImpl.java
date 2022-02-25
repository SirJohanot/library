package com.epam.library.dao;

import com.epam.library.entity.book.Author;
import com.epam.library.entity.book.Book;
import com.epam.library.exception.DaoException;
import com.epam.library.mapper.AuthorRowMapper;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class AuthorDaoImpl extends AbstractDao<Author> implements AuthorDao {

    private static final String RELATION_TABLE_NAME = "book_author";
    private static final String RELATION_TABLE_AUTHOR_ID_COLUMN = "author_id";
    private static final String RELATION_TABLE_BOOK_ID_COLUMN = "book_id";

    private static final String GET_AUTHORS_ASSOCIATED_WITH_BOOK_ID_QUERY = "SELECT * FROM %s b JOIN %s ba ON ba.%s = b.%s JOIN %s a ON a.%s = ba.%s WHERE b.%s = ? ;";
    private static final String GET_BOOK_AUTHOR_RELATION_QUERY = "SELECT * FROM %s ba JOIN %s a ON ba.%s = a.%s WHERE %s = ? AND %s = ? ;";
    private static final String MAP_AUTHOR_TO_BOOK_QUERY = "INSERT INTO %s SET %s = ? , %s = ? ; SELECT * FROM %s ;";

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
    public Optional<Author> getAuthorByName(String name) throws DaoException {
        return findIdentical(Author.ofName(name));
    }

    @Override
    public void saveAuthor(Author author) throws DaoException {
        save(author);
    }

    @Override
    public boolean isAuthorMappedToBookInRelationTable(Long authorId, Long bookId) throws DaoException {
        String query = String.format(GET_BOOK_AUTHOR_RELATION_QUERY, RELATION_TABLE_NAME, Author.TABLE_NAME, RELATION_TABLE_AUTHOR_ID_COLUMN, Author.ID_COLUMN, RELATION_TABLE_AUTHOR_ID_COLUMN, RELATION_TABLE_BOOK_ID_COLUMN);
        Optional<Author> optionalAuthor = executeForSingleResult(query, authorId, bookId);
        return optionalAuthor.isPresent();
    }

    @Override
    public void mapAuthorToBookInRelationTable(Long authorId, Long bookId) throws DaoException {
        String query = String.format(MAP_AUTHOR_TO_BOOK_QUERY, RELATION_TABLE_NAME, RELATION_TABLE_AUTHOR_ID_COLUMN, RELATION_TABLE_BOOK_ID_COLUMN, Author.TABLE_NAME);
        executeQuery(query, authorId, bookId);
    }

    @Override
    protected LinkedHashMap<String, Object> getMapOfColumnValues(Author entity) {
        LinkedHashMap<String, Object> valuesMap = new LinkedHashMap<>();
        valuesMap.put(Author.ID_COLUMN, entity.getId());
        valuesMap.put(Author.NAME_COLUMN, entity.getName());
        return valuesMap;
    }
}
