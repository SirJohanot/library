package com.epam.library.dao;

import com.epam.library.entity.book.Author;
import com.epam.library.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    List<Author> getAuthorsAssociatedWithBookId(Long bookId) throws DaoException;

    Optional<Author> getAuthorByName(String name) throws DaoException;

    void saveAuthor(Author author) throws DaoException;

    boolean isAuthorMappedToBookInRelationTable(Long authorId, Long bookId) throws DaoException;

    void mapAuthorToBookInRelationTable(Long authorId, Long bookId) throws DaoException;
}
