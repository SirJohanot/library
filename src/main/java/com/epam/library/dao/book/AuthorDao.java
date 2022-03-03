package com.epam.library.dao.book;

import com.epam.library.dao.Dao;
import com.epam.library.dao.SecondaryTableDao;
import com.epam.library.entity.book.Author;
import com.epam.library.exception.DaoException;

import java.util.List;

public interface AuthorDao extends Dao<Author>, SecondaryTableDao<Author> {

    List<Author> getAuthorsAssociatedWithBookId(Long bookId) throws DaoException;

    boolean isAuthorMappedToBookInRelationTable(Long authorId, Long bookId) throws DaoException;

    void mapAuthorToBookInRelationTable(Long authorId, Long bookId) throws DaoException;

    void deleteBookMappingsFromRelationTable(Long bookId) throws DaoException;
}
