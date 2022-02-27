package com.epam.library.dao;

import com.epam.library.entity.book.Author;
import com.epam.library.exception.DaoException;

import java.util.List;

public interface AuthorDao extends Dao<Author>, SecondaryTableDao<Author>, NamedEntityDao<Author> {

    List<Author> getAuthorsAssociatedWithBookId(Long bookId) throws DaoException;

    boolean isAuthorMappedToBookInRelationTable(Long authorId, Long bookId) throws DaoException;

    void mapAuthorToBookInRelationTable(Long authorId, Long bookId) throws DaoException;

    void deleteBookMappingsFromRelationTable(Long bookId) throws DaoException;
}
