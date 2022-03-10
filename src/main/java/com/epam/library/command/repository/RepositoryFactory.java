package com.epam.library.command.repository;

import com.epam.library.dao.BookOrderDao;
import com.epam.library.dao.UserDao;
import com.epam.library.dao.book.AuthorDao;
import com.epam.library.dao.book.BookDao;
import com.epam.library.dao.book.GenreDao;
import com.epam.library.dao.book.PublisherDao;

public class RepositoryFactory {

    public BookRepository createBookRepository(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao, PublisherDao publisherDao) {
        return new BookRepositoryImpl(bookDao, authorDao, genreDao, publisherDao);
    }

    public BookOrderRepository createBookOrderRepository(BookOrderDao bookOrderDao, UserDao userDao, BookDao bookDao, AuthorDao authorDao, GenreDao genreDao, PublisherDao publisherDao) {
        BookRepository bookRepository = createBookRepository(bookDao, authorDao, genreDao, publisherDao);
        return new BookOrderRepositoryImpl(bookOrderDao, bookRepository, userDao);
    }
}
