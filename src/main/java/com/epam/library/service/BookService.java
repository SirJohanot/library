package com.epam.library.service;

import com.epam.library.entity.book.Book;
import com.epam.library.exception.ServiceException;

import java.util.List;

public interface BookService {

    List<Book> getBooks() throws ServiceException;

    Book getBookById(Long id) throws ServiceException;

    void saveBook(Long id, String title, String authors, String genre, String publisher, String publishmentYear, int amount) throws ServiceException;

    void deleteBookById(Long bookId) throws ServiceException;
}
