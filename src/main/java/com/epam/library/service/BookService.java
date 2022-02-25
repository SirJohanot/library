package com.epam.library.service;

import com.epam.library.entity.book.Book;
import com.epam.library.exception.ServiceException;

import java.util.List;

public interface BookService {

    List<Book> getBooks() throws ServiceException;

    Book getBookById(Long id) throws ServiceException;
}
