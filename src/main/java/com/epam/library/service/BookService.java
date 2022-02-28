package com.epam.library.service;

import com.epam.library.entity.book.Book;
import com.epam.library.exception.ServiceException;

import java.util.List;

public interface BookService {

    List<Book> getBooks() throws ServiceException;

    Book getBookById(String idLine) throws ServiceException;

    void saveBook(String idLine, String title, String authors, String genre, String publisher, String publishmentYear, String amountLine) throws ServiceException;

    void deleteBookById(String bookIdLine) throws ServiceException;
}
