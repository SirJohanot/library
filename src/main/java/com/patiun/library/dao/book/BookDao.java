package com.patiun.library.dao.book;

import com.patiun.library.dao.Dao;
import com.patiun.library.entity.book.Book;
import com.patiun.library.exception.DaoException;

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

}
