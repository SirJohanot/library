package com.epam.library.service;

import com.epam.library.entity.book.Book;
import com.epam.library.exception.ServiceException;
import com.epam.library.exception.ValidationException;
import com.epam.library.parser.AuthorsLineParser;
import com.epam.library.specification.Specification;
import com.epam.library.validation.Validator;

import java.time.Year;
import java.util.List;

/**
 * This interface is concerned with manipulating Books in the database and performing other Book-related logic
 */

public interface BookService {

    /**
     * Get all Books that fit the specification from the database
     *
     * @param bookSpecification specification to get the books by
     * @return A List object containing the found books
     * @throws ServiceException if a DaoException occurs
     */
    List<Book> getSpecifiedBooks(Specification<Book> bookSpecification) throws ServiceException;

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
     * @param id                id of Book. If null, the book is inserted into the database. If not null, the Book is updated in the database
     * @param title             title of the Book
     * @param authors           String representing authors of the Book, separated by commas
     * @param genre             genre of the Book
     * @param publisher         publisher of the Book
     * @param publishmentYear   Year object representing publishment year of the Book
     * @param amount            Integer object representing the number of Book in stock
     * @param bookValidator     Validator<Book> object which will be used to validate the Book before saving it
     * @param authorsLineParser AuthorsLineParser which will be used to divide the authors line into a List of Author objects
     * @throws ServiceException    if a DaoException occurs
     * @throws ValidationException if the Book with the passed parameters is not deemed valid
     */
    void saveBook(Long id, String title, String authors, String genre, String publisher, Year publishmentYear, Integer amount, Validator<Book> bookValidator, AuthorsLineParser authorsLineParser) throws ServiceException, ValidationException;

    /**
     * Deletes a Book from the database
     *
     * @param bookId id of book to be deleted
     * @throws ServiceException if a DaoException occurs
     */
    void deleteBookById(Long bookId) throws ServiceException;
}
