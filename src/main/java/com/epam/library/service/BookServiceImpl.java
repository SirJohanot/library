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

    @Override
    public void editBook(Long id, String title, String authors, String genre, String publisher, String publishmentYear, int amount) throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();
            Genre genreEntity = getExistingGenreOrANewlySavedOne(helper, genre);
            Publisher publisherEntity = getExistingPublisherOrANewlySavedOne(helper, publisher);
            Year publishmentYearObject = Year.parse(publishmentYear);
            Book book = new Book(id, title, null, genreEntity, publisherEntity, publishmentYearObject, amount);
            BookDao bookDao = helper.createBookDao();
            bookDao.saveBook(book);
            saveAuthorsAndMapThemToBook(helper, authors, id);
            helper.endTransaction();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private Book shallowBookToActualBook(Book book, AuthorDao authorDao, GenreDao genreDao, PublisherDao publisherDao) throws DaoException {
        Long id = book.getId();
        String title = book.getTitle();
        List<Author> authorList = authorDao.getAuthorsAssociatedWithBookId(book.getId());
        Genre genre = genreDao.getGenre(book.getGenre().getId()).get();
        Publisher publisher = publisherDao.getPublisher(book.getPublisher().getId()).get();
        Year publishmentYear = book.getPublishmentYear();
        int amount = book.getAmount();
        return new Book(id, title, authorList, genre, publisher, publishmentYear, amount);
    }

    private void saveAuthorsAndMapThemToBook(DaoHelper helper, String authors, Long bookId) throws ServiceException, DaoException {
        String[] authorNames = authors.split(COMMA_AND_WHITESPACE_REGEX);
        for (String authorName : authorNames) {
            if (authorName.length() == 0) {
                throw new ServiceException("Author name cannot be blank");
            }
            AuthorDao authorDao = helper.createAuthorDao();
            Optional<Author> optionalAuthor = authorDao.getAuthorByName(authorName);
            if (optionalAuthor.isEmpty()) {
                authorDao.saveAuthor(Author.ofName(authorName));
                optionalAuthor = authorDao.getAuthorByName(authorName);
            }
            Long authorId = optionalAuthor.get().getId();
            if (!authorDao.isAuthorMappedToBookInRelationTable(authorId, bookId)) {
                authorDao.mapAuthorToBookInRelationTable(authorId, bookId);
            }
        }
    }

    private Genre getExistingGenreOrANewlySavedOne(DaoHelper helper, String genre) throws DaoException {
        GenreDao genreDao = helper.createGenreDao();
        Optional<Genre> optionalGenre = genreDao.getGenreByName(genre);
        if (optionalGenre.isEmpty()) {
            genreDao.saveGenre(Genre.ofName(genre));
            optionalGenre = genreDao.getGenreByName(genre);
        }
        return optionalGenre.get();
    }

    private Publisher getExistingPublisherOrANewlySavedOne(DaoHelper helper, String publisher) throws DaoException {
        PublisherDao genreDao = helper.createPublisherDao();
        Optional<Publisher> optionalPublisher = genreDao.getPublisherByName(publisher);
        if (optionalPublisher.isEmpty()) {
            genreDao.savePublisher(Publisher.ofName(publisher));
            optionalPublisher = genreDao.getPublisherByName(publisher);
        }
        return optionalPublisher.get();
    }
}
