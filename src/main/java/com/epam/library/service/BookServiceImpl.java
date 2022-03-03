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
import com.epam.library.service.shallowentityfiller.ShallowEntityFiller;
import com.epam.library.service.shallowentityfiller.ShallowEntityFillerFactory;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private static final String COMMA_AND_WHITESPACE_REGEX = "[ ]+,[ ]+";

    private final DaoHelperFactory daoHelperFactory;
    private final ShallowEntityFillerFactory fillerFactory;

    public BookServiceImpl(DaoHelperFactory daoHelperFactory, ShallowEntityFillerFactory fillerFactory) {
        this.daoHelperFactory = daoHelperFactory;
        this.fillerFactory = fillerFactory;
    }

    @Override
    public List<Book> getBooks() throws ServiceException {
        try (DaoHelper helper = daoHelperFactory.createHelper()) {
            helper.startTransaction();

            ShallowEntityFiller<Book> bookFiller = createBookFiller(helper);

            List<Book> bookList = new ArrayList<>();
            BookDao bookDao = helper.createBookDao();
            for (Book book : bookDao.getAllNotDeleted()) {
                bookList.add(bookFiller.fillShallowEntity(book));
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

            ShallowEntityFiller<Book> bookFiller = createBookFiller(helper);

            Book book = bookFiller.fillShallowEntity(shallowBook.get());

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

            GenreDao genreDao = helper.createGenreDao();
            Genre idLessGenre = Genre.ofName(genre);
            Long savedGenreId = genreDao.getIdOfNewOrExistingObject(idLessGenre);

            PublisherDao publisherDao = helper.createPublisherDao();
            Publisher idLessPublisher = Publisher.ofName(publisher);
            Long savedPublisherId = publisherDao.getIdOfNewOrExistingObject(idLessPublisher);

            BookDao bookDao = helper.createBookDao();
            Book book = new Book(id, title, null, Genre.ofId(savedGenreId), Publisher.ofId(savedPublisherId), publishmentYear, amount);
            Long savedBookId = bookDao.getIdOfNewOrExistingObject(book);

            AuthorDao authorDao = helper.createAuthorDao();
            saveAuthorsAndMapThemToBook(authorDao, authors, savedBookId);

            deleteUnreferenced(authorDao, genreDao, publisherDao);

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

    private void saveAuthorsAndMapThemToBook(AuthorDao authorDao, String authors, Long bookId) throws ServiceException, DaoException {
        authorDao.deleteBookMappingsFromRelationTable(bookId);
        String[] authorNames = authors.split(COMMA_AND_WHITESPACE_REGEX);
        for (String authorName : authorNames) {
            if (authorName.length() == 0) {
                throw new ServiceException("Author name cannot be blank");
            }

            Long authorId = authorDao.getIdOfNewOrExistingObject(Author.ofName(authorName));
            if (!authorDao.isAuthorMappedToBookInRelationTable(authorId, bookId)) {
                authorDao.mapAuthorToBookInRelationTable(authorId, bookId);
            }
        }
    }

    private void deleteUnreferenced(AuthorDao authorDao, GenreDao genreDao, PublisherDao publisherDao) throws DaoException {
        authorDao.deleteUnreferenced(Book.TABLE_NAME, Book.ID_COLUMN);
        genreDao.deleteUnreferenced(Book.TABLE_NAME, Book.GENRE_ID_COLUMN);
        publisherDao.deleteUnreferenced(Book.TABLE_NAME, Book.PUBLISHER_ID_COLUMN);
    }

    private ShallowEntityFiller<Book> createBookFiller(DaoHelper helper) {
        AuthorDao authorDao = helper.createAuthorDao();
        GenreDao genreDao = helper.createGenreDao();
        PublisherDao publisherDao = helper.createPublisherDao();

        return fillerFactory.createBookFiller(authorDao, genreDao, publisherDao);
    }
}
