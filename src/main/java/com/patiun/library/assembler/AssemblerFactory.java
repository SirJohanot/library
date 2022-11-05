package com.patiun.library.assembler;

import com.patiun.library.dao.BookOrderDao;
import com.patiun.library.dao.book.BookDao;
import com.patiun.library.dao.book.GenreDao;
import com.patiun.library.dao.UserDao;
import com.patiun.library.dao.book.AuthorDao;
import com.patiun.library.dao.book.PublisherDao;

public class AssemblerFactory {

    public BookAssembler createBookAssembler(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao, PublisherDao publisherDao) {
        return new BookAssemblerImpl(bookDao, authorDao, genreDao, publisherDao);
    }

    public BookOrderAssembler createBookOrderAssembler(BookOrderDao bookOrderDao, UserDao userDao, BookDao bookDao, AuthorDao authorDao, GenreDao genreDao, PublisherDao publisherDao) {
        BookAssembler bookAssembler = createBookAssembler(bookDao, authorDao, genreDao, publisherDao);
        return new BookOrderAssemblerImpl(bookOrderDao, bookAssembler, userDao);
    }
}
