package com.epam.library.service;

import com.epam.library.dao.AuthorDao;
import com.epam.library.dao.BookDao;
import com.epam.library.dao.GenreDao;
import com.epam.library.dao.PublisherDao;
import com.epam.library.dao.daohelper.DaoHelper;
import com.epam.library.dao.daohelper.DaoHelperFactory;
import com.epam.library.entity.book.Author;
import com.epam.library.entity.book.Book;
import com.epam.library.entity.book.Genre;
import com.epam.library.entity.book.Publisher;
import com.epam.library.exception.DaoException;
import com.epam.library.exception.ServiceException;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final DaoHelperFactory daoHelperFactory;

    public BookServiceImpl(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    @Override
    public List<Book> getBooks() throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();
            List<Book> bookList = new ArrayList<>();
            BookDao bookDao = helper.createBookDao();
            AuthorDao authorDao = helper.createAuthorDao();
            GenreDao genreDao = helper.createGenreDao();
            PublisherDao publisherDao = helper.createPublisherDao();
            for (Book book : bookDao.getAllNotDeleted()) {
                bookList.add(shallowBookToActualBook(book, authorDao, genreDao, publisherDao));
            }
            helper.endTransaction();
            return bookList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Book getBookById(Long id) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();
            BookDao bookDao = helper.createBookDao();
            Optional<Book> shallowBook = bookDao.getNotDeletedBookById(id);
            AuthorDao authorDao = helper.createAuthorDao();
            GenreDao genreDao = helper.createGenreDao();
            PublisherDao publisherDao = helper.createPublisherDao();
            Book book = shallowBookToActualBook(shallowBook.get(), authorDao, genreDao, publisherDao);
            helper.endTransaction();
            return book;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private Book shallowBookToActualBook(Book book, AuthorDao authorDao, GenreDao genreDao, PublisherDao publisherDao) throws DaoException {
        Long id = book.getId();
        String title = book.getTitle();
        List<Author> authorList = authorDao.getAuthorsAssociatedWithBookId(book.getId());
        Genre genre = genreDao.getGenreOfBook(book).get();
        Publisher publisher = publisherDao.getPublisherOfBook(book).get();
        Year publishmentYear = book.getPublishmentYear();
        int amount = book.getAmount();
        return new Book(id, title, authorList, genre, publisher, publishmentYear, amount);
    }
}
