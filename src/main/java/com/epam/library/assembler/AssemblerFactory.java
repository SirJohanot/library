package com.epam.library.assembler;

import com.epam.library.dao.BookOrderDao;
import com.epam.library.dao.UserDao;
import com.epam.library.dao.book.AuthorDao;
import com.epam.library.dao.book.BookDao;
import com.epam.library.dao.book.GenreDao;
import com.epam.library.dao.book.PublisherDao;

public class AssemblerFactory {

    public BookAssembler createBookAssembler(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao, PublisherDao publisherDao) {
        return new BookAssemblerImpl(bookDao, authorDao, genreDao, publisherDao);
    }

    public BookOrderAssembler createBookOrderAssembler(BookOrderDao bookOrderDao, UserDao userDao, BookDao bookDao, AuthorDao authorDao, GenreDao genreDao, PublisherDao publisherDao) {
        BookAssembler bookAssembler = createBookAssembler(bookDao, authorDao, genreDao, publisherDao);
        return new BookOrderAssemblerImpl(bookOrderDao, bookAssembler, userDao);
    }
}
