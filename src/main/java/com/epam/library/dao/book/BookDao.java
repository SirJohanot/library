package com.epam.library.dao.book;

import com.epam.library.dao.Dao;
import com.epam.library.entity.book.Book;
import com.epam.library.exception.DaoException;

import java.util.List;

/**
 * This interface is concerned with manipulating a Book's amount in the database and getting not deleted books from the database
 */

public interface BookDao extends Dao<Book> {

    /**
     * Changes Book's amount in the database based on inputted value
     *
     * @param bookId id of the Book in the database
     * @param value  what number to change the amount by. May be negative to subtract from the amount
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    void tweakAmount(Long bookId, int value) throws DaoException;

    /**
     * Searches for and returns all the database's books which are not deleted
     *
     * @return List Object containing all Books which have the field is_deleted as false. May be empty if there are no Books in the database or if all Books are is_deleted=true
     * @throws DaoException if there were errors connecting to the database or while executing an SQL script
     */
    List<Book> getAllNotDeleted() throws DaoException;

}
