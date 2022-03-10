package com.epam.library.service;

import com.epam.library.command.parser.AuthorsLineParser;
import com.epam.library.command.validation.Validator;
import com.epam.library.entity.book.Book;
import com.epam.library.exception.ServiceException;

import java.time.Year;
import java.util.List;

/**
 * This interface is concerned with manipulating Books in the database and performing other Book-related logic
 */

public interface BookService {

    /**
     * Gets all Books from the database
     *
     * @return List object containing Books. May be empty
     * @throws ServiceException if a DaoException occurs
     */
    List<Book> getBooks() throws ServiceException;

    /**
     * Gets the Book from the database
     *
     * @param id id of the Book in the database
     * @return the requested Book
     * @throws ServiceException if there is no Book with requested id or if a DaoException occurs
     */
    Book getBookById(Long id) throws ServiceException;

    /**
     * Inserts or updates the Book in the database
     *
     * @param id              id of Book. If null, the book is inserted into the database. If not null, the Book is updated in the database
     * @param title           title of the Book
     * @param authors         String representing authors of the Book, separated by commas
     * @param genre           genre of the Book
     * @param publisher       publisher of the Book
     * @param publishmentYear Year object representing publishment year of the Book
     * @param amount          Integer object representing the number of Book in stock
     * @throws ServiceException if a DaoException occurs
     */
    void saveBook(Long id, String title, String authors, String genre, String publisher, Year publishmentYear, Integer amount, Validator<Book> bookValidator, AuthorsLineParser authorsLineParser) throws ServiceException;

    void deleteBookById(Long bookId) throws ServiceException;
}
