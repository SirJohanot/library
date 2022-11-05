package com.patiun.library.dao.book;

import com.patiun.library.dao.AbstractDao;
import com.patiun.library.exception.DaoException;
import com.patiun.library.mapper.AuthorRowMapper;
import com.patiun.library.entity.book.Author;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class AuthorDaoImpl extends AbstractDao<Author> implements AuthorDao {

    private static final String GET_AUTHORS_ASSOCIATED_WITH_BOOK_ID_QUERY = "SELECT * FROM book b JOIN book_author ba ON ba.book_id = b.id JOIN author a ON a.id = ba.author_id WHERE b.id = ? ;";
    private static final String GET_BOOK_AUTHOR_RELATION_QUERY = "SELECT * FROM book_author ba JOIN author a ON ba.author_id = a.id WHERE a.id = ? AND ba.book_id = ? ;";
    private static final String MAP_AUTHOR_TO_BOOK_QUERY = "INSERT INTO book_author SET author_id = ? , book_id = ? ;";
    private static final String DELETE_RELATION_TABLE_ROWS_REFERENCING_BOOK = "DELETE FROM book_author WHERE book_id = ? ;";
    private static final String DELETE_BOOK_AUTHOR_ROWS_NOT_REFERENCING_AN_EXISTING_BOOK_QUERY = "DELETE FROM book_author ba WHERE NOT EXISTS (SELECT 1 FROM book b WHERE ba.book_id = b.id);";
    private static final String DELETE_AUTHOR_ROWS_NOT_REFERENCED_BY_BOOK_AUTHOR_QUERY = "DELETE FROM author a WHERE NOT EXISTS (SELECT 1 FROM book_author ba WHERE ba.author_id = a.id);";

    public AuthorDaoImpl(Connection connection) {
        super(connection, new AuthorRowMapper(), Author.TABLE_NAME);
    }

    @Override
    public List<Author> getAuthorsAssociatedWithBookId(Long bookId) throws DaoException {
        return executeQuery(GET_AUTHORS_ASSOCIATED_WITH_BOOK_ID_QUERY, bookId);
    }

    @Override
    public boolean isAuthorMappedToBookInRelationTable(Long authorId, Long bookId) throws DaoException {
        Optional<Author> optionalAuthor = executeForSingleResult(GET_BOOK_AUTHOR_RELATION_QUERY, authorId, bookId);
        return optionalAuthor.isPresent();
    }

    @Override
    public void mapAuthorToBookInRelationTable(Long authorId, Long bookId) throws DaoException {
        executeUpdate(MAP_AUTHOR_TO_BOOK_QUERY, authorId, bookId);
    }

    @Override
    public void deleteBookMappingsFromRelationTable(Long bookId) throws DaoException {
        executeUpdate(DELETE_RELATION_TABLE_ROWS_REFERENCING_BOOK, bookId);
    }

    @Override
    protected LinkedHashMap<String, Object> getMapOfColumnValues(Author entity) {
        LinkedHashMap<String, Object> valuesMap = new LinkedHashMap<>();
        valuesMap.put(Author.ID_COLUMN, entity.getId());
        valuesMap.put(Author.NAME_COLUMN, entity.getName());
        return valuesMap;
    }

    @Override
    public void deleteRedundant() throws DaoException {
        executeUpdate(DELETE_BOOK_AUTHOR_ROWS_NOT_REFERENCING_AN_EXISTING_BOOK_QUERY);
        executeUpdate(DELETE_AUTHOR_ROWS_NOT_REFERENCED_BY_BOOK_AUTHOR_QUERY);
    }

}
