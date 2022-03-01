package com.epam.library.service;

import com.epam.library.dao.book.AuthorDao;
import com.epam.library.dao.book.BookDao;
import com.epam.library.dao.book.GenreDao;
import com.epam.library.dao.book.PublisherDao;
import com.epam.library.dao.helper.DaoHelper;
import com.epam.library.dao.helper.DaoHelperFactory;
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

    private static final String COMMA_AND_WHITESPACE_REGEX = ", ";

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
            for (Book book : bookDao.getAllNotDeleted()) {
                bookList.add(shallowBookToActualBook(book, helper));
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
            Optional<Book> shallowBook = bookDao.getById(id);
            if (shallowBook.isEmpty()) {
                throw new ServiceException("The requested book does not exist");
            }
            Book book = shallowBookToActualBook(shallowBook.get(), helper);
            helper.endTransaction();
            return book;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void saveBook(Long id, String title, String authors, String genre, String publisher, Year publishmentYear, Integer amount) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();
            Genre genreEntity = getExistingGenreOrANewlySavedOne(helper, genre);
            Publisher publisherEntity = getExistingPublisherOrANewlySavedOne(helper, publisher);
            Book book = new Book(id, title, null, genreEntity, publisherEntity, publishmentYear, amount);
            BookDao bookDao = helper.createBookDao();
            bookDao.save(book);
            Optional<Book> optionalBook = bookDao.findIdenticalBook(book);
            if (optionalBook.isEmpty()) {
                throw new ServiceException("The book could not be saved to the database");
            }
            Book savedBook = optionalBook.get();
            saveAuthorsAndMapThemToBook(helper, authors, savedBook.getId());
            deleteUnreferenced(helper);
            helper.endTransaction();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBookById(Long bookId) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();
            BookDao bookDao = helper.createBookDao();
            bookDao.removeById(bookId);
            helper.endTransaction();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private Book shallowBookToActualBook(Book book, DaoHelper helper) throws DaoException, ServiceException {
        Long id = book.getId();
        String title = book.getTitle();
        AuthorDao authorDao = helper.createAuthorDao();
        List<Author> authorList = authorDao.getAuthorsAssociatedWithBookId(book.getId());
        GenreDao genreDao = helper.createGenreDao();
        Optional<Genre> optionalGenre = genreDao.getById(book.getGenre().getId());
        PublisherDao publisherDao = helper.createPublisherDao();
        Optional<Publisher> optionalPublisher = publisherDao.getById(book.getPublisher().getId());
        if (optionalGenre.isEmpty() || optionalPublisher.isEmpty()) {
            throw new ServiceException("There is an error in database content");
        }
        Genre genre = optionalGenre.get();
        Publisher publisher = optionalPublisher.get();
        Year publishmentYear = book.getPublishmentYear();
        int amount = book.getAmount();
        return new Book(id, title, authorList, genre, publisher, publishmentYear, amount);
    }

    private void saveAuthorsAndMapThemToBook(DaoHelper helper, String authors, Long bookId) throws ServiceException, DaoException {
        AuthorDao authorDao = helper.createAuthorDao();
        authorDao.deleteBookMappingsFromRelationTable(bookId);
        String[] authorNames = authors.split(COMMA_AND_WHITESPACE_REGEX);
        for (String authorName : authorNames) {
            if (authorName.length() == 0) {
                throw new ServiceException("Author name cannot be blank");
            }
            Optional<Author> optionalAuthor = authorDao.getByName(authorName);
            if (optionalAuthor.isEmpty()) {
                authorDao.save(Author.ofName(authorName));
                optionalAuthor = authorDao.getByName(authorName);
                if (optionalAuthor.isEmpty()) {
                    throw new ServiceException("Author could not be save to the database");
                }
            }
            Long authorId = optionalAuthor.get().getId();
            if (!authorDao.isAuthorMappedToBookInRelationTable(authorId, bookId)) {
                authorDao.mapAuthorToBookInRelationTable(authorId, bookId);
            }
        }
    }

    private Genre getExistingGenreOrANewlySavedOne(DaoHelper helper, String genre) throws DaoException, ServiceException {
        GenreDao genreDao = helper.createGenreDao();
        Optional<Genre> optionalGenre = genreDao.getByName(genre);
        if (optionalGenre.isEmpty()) {
            genreDao.save(Genre.ofName(genre));
            optionalGenre = genreDao.getByName(genre);
            if (optionalGenre.isEmpty()) {
                throw new ServiceException("Genre could not be save to the database");
            }
        }
        return optionalGenre.get();
    }

    private Publisher getExistingPublisherOrANewlySavedOne(DaoHelper helper, String publisher) throws DaoException, ServiceException {
        PublisherDao genreDao = helper.createPublisherDao();
        Optional<Publisher> optionalPublisher = genreDao.getByName(publisher);
        if (optionalPublisher.isEmpty()) {
            genreDao.save(Publisher.ofName(publisher));
            optionalPublisher = genreDao.getByName(publisher);
            if (optionalPublisher.isEmpty()) {
                throw new ServiceException("Publisher could not be save to the database");
            }
        }
        return optionalPublisher.get();
    }

    private void deleteUnreferenced(DaoHelper helper) throws DaoException {
        AuthorDao authorDao = helper.createAuthorDao();
        authorDao.deleteUnreferenced(Book.TABLE_NAME, Book.ID_COLUMN);
        GenreDao genreDao = helper.createGenreDao();
        genreDao.deleteUnreferenced(Book.TABLE_NAME, Book.GENRE_ID_COLUMN);
        PublisherDao publisher = helper.createPublisherDao();
        publisher.deleteUnreferenced(Book.TABLE_NAME, Book.PUBLISHER_ID_COLUMN);
    }
}
