package com.epam.library.service.shallowentityfiller;

import com.epam.library.dao.UserDao;
import com.epam.library.dao.book.AuthorDao;
import com.epam.library.dao.book.BookDao;
import com.epam.library.dao.book.GenreDao;
import com.epam.library.dao.book.PublisherDao;
import com.epam.library.entity.BookOrder;
import com.epam.library.entity.book.Book;

public class ShallowEntityFillerFactory {

    public ShallowEntityFiller<Book> createBookFiller(AuthorDao authorDao, GenreDao genreDao, PublisherDao publisherDao) {
        return new ShallowBookFiller(authorDao, genreDao, publisherDao);
    }

    public ShallowEntityFiller<BookOrder> createBookOrderFiller(UserDao userDao, BookDao bookDao, ShallowEntityFiller<Book> bookFiller) {
        return new ShallowBookOrderFiller(userDao, bookDao, bookFiller);
    }
}
