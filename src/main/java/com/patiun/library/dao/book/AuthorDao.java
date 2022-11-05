package com.patiun.library.dao.book;

import com.patiun.library.dao.SecondaryTableDao;
import com.patiun.library.exception.DaoException;
import com.patiun.library.entity.book.Author;

import java.util.List;

/**
 * This interface is concerned with manipulating, analyzing and getting Book-To-Author relations of the database
 */

public interface AuthorDao extends SecondaryTableDao<Author> {

    /**
     * Returns authors associated with a Book
     *
     * @param bookId id of book in the database
     * @return List<Author> object, containing Authors mapped to the bookId. May be empty
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    List<Author> getAuthorsAssociatedWithBookId(Long bookId) throws DaoException;

    /**
     * Checks if the Author and Book are related in the database
     *
     * @param authorId id of Author
     * @param bookId   id of Book
     * @return true if the Book and Author are mapped to one another in the relation table
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    boolean isAuthorMappedToBookInRelationTable(Long authorId, Long bookId) throws DaoException;

    /**
     * Maps Author to Book in database's relation table
     *
     * @param authorId id of Author in the database
     * @param bookId   id of Book in the database
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    void mapAuthorToBookInRelationTable(Long authorId, Long bookId) throws DaoException;

    /**
     * Unmaps all Authors from Book in the database
     *
     * @param bookId id of Book in the database
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    void deleteBookMappingsFromRelationTable(Long bookId) throws DaoException;
}
